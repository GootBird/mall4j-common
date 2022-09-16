package com.xixi.mall.common.security.context;

import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;

public class AuthUserContext {

    /**
     * The request holder.
     */
    private static final ThreadLocal<UserInfoInTokenBo> USER_INFO_IN_TOKEN_HOLDER = new ThreadLocal<>();

    public static UserInfoInTokenBo get() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }


    public static UserInfoInTokenBo forceGet() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(UserInfoInTokenBo userInfoInTokenBo) {
        USER_INFO_IN_TOKEN_HOLDER.set(userInfoInTokenBo);
    }

    public static void clean() {
        if (USER_INFO_IN_TOKEN_HOLDER.get() != null) {
            USER_INFO_IN_TOKEN_HOLDER.remove();
        }
    }

}
