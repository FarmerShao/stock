package com.farmershao.stock.constant;

/**
 * CacheKeyEnum
 *
 * @author Shao Yu
 * @date 2019/5/5 19:25
 * @since
 **/
public enum  CacheKeyEnum {

    //======= BackPermission ======
    BACK_PERMISSION_ARRAY("back.permission.array.", 30 * 60L),

    ;



    /**
     * 缓存的key
     */
    private String key;
    /**
     * 过期时间，单位 秒
     */
    private Long expire;

    CacheKeyEnum(String key, Long expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public Long getExpire() {
        return expire;
    }
}
