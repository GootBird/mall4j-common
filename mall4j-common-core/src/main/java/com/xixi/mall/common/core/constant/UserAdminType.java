package com.xixi.mall.common.core.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserAdminType {
    /**
     * 管理员
     */
    ADMIN(1),
    /**
     * 用户
     */
    USER(0),
    ;

    private final Integer value;

    public Integer value() {
        return value;
    }

    public static UserAdminType instance(Integer value) {
        UserAdminType[] enums = values();
        for (UserAdminType statusEnum : enums) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }
}
