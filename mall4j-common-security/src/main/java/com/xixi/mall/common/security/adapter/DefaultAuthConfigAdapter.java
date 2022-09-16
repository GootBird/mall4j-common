package com.xixi.mall.common.security.adapter;

import com.xixi.mall.common.core.feign.FeignInsideAuthConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultAuthConfigAdapter implements AuthConfigAdapter {

    /**
     * 内部直接调用接口，无需登录权限
     */
    private static final String FEIGN_INSIDER_URI = FeignInsideAuthConfig.FEIGN_URL
            + "/**"
            + FeignInsideAuthConfig.INSIDER_URL
            + "/**";

    /**
     * 外部直接调用接口，无需登录权限 unwanted auth
     */
    private static final String EXTERNAL_URI = "/**/ua/**";

    /**
     * swagger
     */
    private static final String DOC_URI = "/v2/api-docs";

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/*");
    }

    @Override
    public List<String> excludePathPatterns(String... paths) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(DOC_URI);
        arrayList.add(FEIGN_INSIDER_URI);
        arrayList.add(EXTERNAL_URI);
        arrayList.addAll(Arrays.asList(paths));
        return arrayList;
    }

}
