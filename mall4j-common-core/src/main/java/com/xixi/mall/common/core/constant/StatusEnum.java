package com.xixi.mall.common.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 等级
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 删除 (逻辑删除)
     */
    DELETE(-1),

    /**
     * 禁用/过期/下架
     */
    DISABLE(0),

    /**
     * 启用/未过期/上架
     */
    ENABLE(1),

    /**
     * 违规下架
     */
    OFFLINE(2),

    /**
     * 等待审核
     */
    WAIT_AUDIT(3);

    private final Integer value;

    public Integer value() {
        return value;
    }

    public static Boolean offlineStatus(Integer value) {
        StatusEnum[] enums = values();
        for (StatusEnum statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
