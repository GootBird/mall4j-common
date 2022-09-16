package com.xixi.mall.common.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import com.xixi.mall.api.auth.constant.SysTypeEnum;
import com.xixi.mall.api.auth.feign.TokenFeignClient;
import com.xixi.mall.api.rabc.constant.HttpMethodEnum;
import com.xixi.mall.api.rabc.feign.PermissionFeignClient;
import com.xixi.mall.common.core.constant.Auth;
import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.feign.FeignInsideAuthConfig;
import com.xixi.mall.common.core.handle.HttpHandler;
import com.xixi.mall.common.core.utils.IpHelper;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import com.xixi.mall.common.security.adapter.AuthConfigAdapter;
import com.xixi.mall.common.security.context.AuthUserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可：
 */
@Slf4j
@Component
public class AuthFilter implements Filter {

    @Resource
    private AuthConfigAdapter authConfigAdapter;

    @Resource
    private TokenFeignClient tokenFeignClient;

    @Resource
    private PermissionFeignClient permissionFeignClient;

    @Resource
    private FeignInsideAuthConfig feignInsideAuthConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String reqUri = req.getRequestURI();

        if (reqUri.startsWith(FeignInsideAuthConfig.FEIGN_URL)
                && !feignRequestCheck(req) //校验是否是合法的feign调用
        ) {
            HttpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        if (Auth.CHECK_TOKEN_URI.equals(reqUri)) {
            chain.doFilter(req, resp);
            return;
        }

        AntPathMatcher pathMatcher = new AntPathMatcher();

        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        if (
                ObjectUtil.defaultIfNull(authConfigAdapter.excludePathPatterns(), Collections.emptyList())
                        .stream().anyMatch(excludePath -> pathMatcher.match((String) excludePath, reqUri))
        ) {
            chain.doFilter(req, resp);
            return;
        }

        String accessToken = req.getHeader("Authorization");

        if (StrUtil.isBlank(accessToken)) {
            HttpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        // 校验token，并返回用户信息
        ServerResponse<UserInfoInTokenBo> userInfoInTokenVoServerResponse = tokenFeignClient
                .checkToken(accessToken);

        if (!userInfoInTokenVoServerResponse.isSuccess()) {
            HttpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        UserInfoInTokenBo userInfoInToken = userInfoInTokenVoServerResponse.getData();

        // 需要用户角色权限，就去根据用户角色权限判断是否
        if (!checkRbac(userInfoInToken, reqUri, req.getMethod())) {
            HttpHandler.printServerResponseToWeb(ServerResponse.fail(ResponseEnum.UNAUTHORIZED));
            return;
        }

        try {
            // 保存上下文
            AuthUserContext.set(userInfoInToken);

            chain.doFilter(req, resp);
        } finally {
            AuthUserContext.clean();
        }

    }

    private boolean feignRequestCheck(HttpServletRequest req) {

        String feignInsideSecret = req.getHeader(feignInsideAuthConfig.getKey());

        // 校验feign 请求携带的key 和 value是否正确
        if (StrUtil.isBlank(feignInsideSecret)
                || ObjectUtil.notEqual(feignInsideSecret, feignInsideAuthConfig.getSecret())) {
            return false;
        }

        List<String> ips = feignInsideAuthConfig.getIps(); // ip白名单
        ips.removeIf(StrUtil::isBlank); // 移除无用的空ip

        // 有ip白名单，且ip不在白名单内，校验失败
        if (CollectionUtil.isNotEmpty(ips) && !ips.contains(IpHelper.getIpAddr())) {
            log.error("ip not in ip White list: {}, ip, {}", ips, IpHelper.getIpAddr());
            return false;
        }

        return true;
    }

    /**
     * 用户角色权限校验
     *
     * @param uri uri
     * @return 是否校验成功
     */
    public boolean checkRbac(UserInfoInTokenBo userInfoInToken, String uri, String method) {

        if (ObjectUtil.notEqual(SysTypeEnum.PLATFORM.getValue(), userInfoInToken.getSysType())
                && ObjectUtil.notEqual(SysTypeEnum.MULTISHOP.getValue(), userInfoInToken.getSysType())) {

            return true;
        }

        ServerResponse<Boolean> booleanServerResponse = permissionFeignClient.checkPermission(
                userInfoInToken.getUserId(),
                userInfoInToken.getSysType(),
                uri,
                userInfoInToken.getIsAdmin(),
                HttpMethodEnum.valueOf(method.toUpperCase()).getValue()
        );

        if (booleanServerResponse.unSuccess())
            return false;

        return booleanServerResponse.getData();
    }

}
