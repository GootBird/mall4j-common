package com.xixi.mall.common.cache.adapter;

import com.xixi.mall.common.cache.bo.CacheNameWithTtlBo;

import java.util.List;

/**
 * 实现该接口之后，根据缓存的cacheName和ttl将缓存进行过期
 */
public interface CacheTtlAdapter {

    /**
     * 根据缓存的cacheName和ttl将缓存进行过期
     *
     * @return 需要独立设置过期时间的缓存列表
     */
    List<CacheNameWithTtlBo> listCacheNameWithTtl();

}
