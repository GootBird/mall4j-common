package com.xixi.mall.common.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InputUserNameEnum {

    /**
     * 用户名
     */
    USERNAME(1),

    /**
     * 手机号
     */
    PHONE(2),

    /**
     * 邮箱
     */
    EMAIL(3),
    ;

    private final Integer value;
}
