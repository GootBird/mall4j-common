package com.xixi.mall.common.security.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import com.xixi.mall.api.auth.constant.SysTypeEnum;
import com.xixi.mall.api.auth.feign.TokenFeignClient;
import com.xixi.mall.api.rabc.constant.HttpMethodEnum;
import com.xixi.mall.api.rabc.feign.PermissionFeignClient;
import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.feign.FeignInsideAuthConfig;
import com.xixi.mall.common.core.utils.IpHelper;
import com.xixi.mall.common.core.utils.ThrowUtils;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import com.xixi.mall.common.security.annotations.FeignAuthenticate;
import com.xixi.mall.common.security.annotations.SkipAuthenticate;
import com.xixi.mall.common.security.context.AuthUserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private PermissionFeignClient permissionFeignClient;

    @Resource
    private FeignInsideAuthConfig feignInsideAuthConfig;

    @Resource
    private TokenFeignClient tokenFeignClient;

    /**
     * 外部直接调用接口，无需登录权限 unwanted auth
     */
    private static final String EXTERNAL_URI = "/**/ua/**";

    /**
     * swagger
     */
    private static final String DOC_URI = "/v2/api-docs";

    private static final List<String> excludePathPatterns = Arrays.asList(EXTERNAL_URI, DOC_URI);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {

        String reqUri = req.getRequestURI();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        if (
                excludePathPatterns
                        .stream().anyMatch(excludePath -> pathMatcher.match(excludePath, reqUri))
        ) {
            return true;
        }

        if (isSkipAuth(handle)) return true; //是否跳过鉴权

        if (isFeignInterface(handle) && !feignRequestCheck(req)) {
            ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
        }

        String accessToken = req.getHeader("Authorization");

        if (StringUtils.isBlank(accessToken)) {
            ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
        }

        // 校验token，并返回用户信息
        ServerResponse<UserInfoInTokenBo> checkResponse = tokenFeignClient
                .checkToken(accessToken);

        ThrowUtils.throwErr(checkResponse);

        UserInfoInTokenBo userInfoInToken = checkResponse.getData();

        // 需要用户角色权限，就去根据用户角色权限判断是否
        if (!checkRbac(userInfoInToken, reqUri, req.getMethod())) {
            ThrowUtils.throwErr(ResponseEnum.UNAUTHORIZED);
        }

        // 保存上下文
        AuthUserContext.set(userInfoInToken);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthUserContext.clean();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
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

        ServerResponse<Boolean> checkResponse = permissionFeignClient.checkPermission(
                userInfoInToken.getUserId(),
                userInfoInToken.getSysType(),
                uri,
                userInfoInToken.getIsAdmin(),
                HttpMethodEnum.valueOf(method.toUpperCase()).getValue()
        );

        ThrowUtils.throwErr(checkResponse);

        return checkResponse.getData();
    }

    /**
     * feign请求校验
     *
     * @param req req
     * @return 是否是合法的feign
     */
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
     * 是否跳过鉴权
     *
     * @param handle handle
     */
    private boolean isSkipAuth(Object handle) {

        if (!(handle instanceof HandlerMethod))
            return false;

        return assertHdlHasAnnotation((HandlerMethod) handle, SkipAuthenticate.class);
    }

    /**
     * 是否feign接口
     */
    private boolean isFeignInterface(Object handle) {

        if (!(handle instanceof HandlerMethod))
            return false;

        return assertHdlHasAnnotation((HandlerMethod) handle, FeignAuthenticate.class);
    }

    /**
     * 断言代理方法是否有某个注解
     *
     * @param handlerMethod  代理方法
     * @param annotationType 注解类型
     * @return tf
     */
    private boolean assertHdlHasAnnotation(HandlerMethod handlerMethod, Class<? extends Annotation> annotationType) {
        Class<?> clz = handlerMethod.getBeanType();

        if (Objects.nonNull(clz.getAnnotation(annotationType)))
            return true;

        Method method = handlerMethod.getMethod();

        return Objects.nonNull(method.getAnnotation(annotationType));
    }

}
