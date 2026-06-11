package com.cskaoyan.duolai.clean.redis.constants;

/**
 * redis相关常量
 **/
public class RedisConstants {

    public static final class CacheName {

        /**
         *  区域列表缓存
         */

        /**
         * 家政服务缓存
         */
        public static final String REGION_CACHE = "REGION_CACHE:ACTIVE_REGIONS";

        /**
         * 用户端首页服务图标
         */
        public static final String FIRST_PAGE_PARTIAL_SERVE_CACHE = "FIRST_PAGE:PARTIAL_SERVE";

        /**
         * 用户端首页热门服务
         */
        public static final String FIRST_PAGE_HOT_SERVE = "FIRST_PAGE:HOT_SERVE";

        /**
         * 用户端已开通服务分类
         */
        public static final String REGION_SERVE_TYPE = "REGION:SERVE_TYPE";

        /**
         * 服务项
         */
        public static final String REGION_SERVE_DETAIL = "REGION:SERVE_DETAIL";
    }

    public static final class CacheManager {
        /**
         * 缓存时间永久
         */
        public static final String FOREVER = "cacheManagerForever";

        /**
         * 缓存时间永久
         */
        public static final String THIRTY_MINUTES = "cacheManager30Minutes";


    }

}
