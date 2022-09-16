package com.xixi.mall.common.database.interceptor;


import cn.hutool.core.util.StrUtil;
import com.xixi.mall.common.core.constant.Auth;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnClass({RequestInterceptor.class, GlobalTransactional.class})
public class SeataRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        String currentXid = RootContext.getXID(),
                reqUrl = template.url();

        if (StrUtil.isNotBlank(currentXid)
                && !reqUrl.startsWith(Auth.CHECK_TOKEN_URI)
                && !reqUrl.startsWith(Auth.CHECK_RBAC_URI)
        ) {
            template.header(RootContext.KEY_XID, currentXid);
        }

    }
}
