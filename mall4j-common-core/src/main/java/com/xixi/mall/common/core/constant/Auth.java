package com.xixi.mall.common.core.constant;


import com.xixi.mall.common.core.feign.FeignInsideAuthConfig;

public interface Auth {

    String CHECK_TOKEN_URI = FeignInsideAuthConfig.FEIGN_URL + "/token/checkToken";

    String CHECK_RBAC_URI = FeignInsideAuthConfig.FEIGN_URL + "/insider/permission/checkPermission";

    String CHECK_PERMISSION_URI = FeignInsideAuthConfig.FEIGN_URL + "/checkPermission";

}
