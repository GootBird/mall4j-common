package com.xixi.mall.common.security.bo;

import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * token信息，该信息存在redis中
 */
@Getter
@Setter
@ToString
public class TokenInfoBo {

    /**
     * 保存在token信息里面的用户信息
     */
    private UserInfoInTokenBo userInfoInToken;

    private String accessToken;

    private String refreshToken;

    /**
     * 在多少秒后过期
     */
    private Integer expiresIn;

}
