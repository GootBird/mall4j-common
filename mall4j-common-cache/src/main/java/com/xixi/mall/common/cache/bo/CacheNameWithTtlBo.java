package com.xixi.mall.common.cache.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通过 cacheName 配置 和 时间告诉缓存多久清楚一遍
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class CacheNameWithTtlBo {

    /**
     * 缓存key
     */
    private String cacheName;

    /**
     * 过期时间
     */
    private Integer ttl;

}
