package com.xixi.mall.common.cache.constant;

public interface UserCacheNames {

    /**
     * 前缀
     */
    String USER_PREFIX = "mall4j_user:";

    /**
     * 用户信息缓存key
     */
    String USER_INFO = USER_PREFIX + "info:";

    /**
     * 用户默认地址缓存key
     */
    String USER_DEFAULT_ADDR = USER_PREFIX + "user_addr:user_id:";

    /**
     * 店铺分类列表缓存key
     */
    String AREA_INFO_KEY = USER_PREFIX + "area_info";

    /**
     * 店铺分类列表缓存key
     */
    String AREA_KEY = USER_PREFIX + "area";

}
