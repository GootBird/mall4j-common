package com.xixi.mall.common.security.bo;

import com.xixi.mall.api.auth.bo.UserInfoInTokenBo;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于校验的用户信息
 */
@Getter
@Setter
public class AuthAccountInVerifyBo extends UserInfoInTokenBo {

	private String password;

	private Integer status;

}
