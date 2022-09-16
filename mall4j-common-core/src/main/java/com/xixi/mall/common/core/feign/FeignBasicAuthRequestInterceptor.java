package com.xixi.mall.common.core.feign;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@ConditionalOnClass({RequestInterceptor.class})
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Resource
    private FeignInsideAuthConfig feignInsideAuthConfig;

    @Override
    public void apply(RequestTemplate template) {
        // feign的内部请求，往请求头放入key 和 secret进行校验
        template.header(feignInsideAuthConfig.getKey(), feignInsideAuthConfig.getSecret());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String authorization = request.getHeader("Authorization");

        if (StrUtil.isNotBlank(authorization)) {
            template.header("Authorization", authorization);
        }
    }
}
