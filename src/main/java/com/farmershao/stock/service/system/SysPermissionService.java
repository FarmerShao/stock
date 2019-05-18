package com.farmershao.stock.service.system;

import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.persistence.mapper.system.SysPermissionMapper;
import com.farmershao.stock.persistence.model.system.SysPermission;
import com.farmershao.stock.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能权限 Service
 *
 * @author : Zhao Da
 * @since : 2019/4/28 17:17
 */
@Service
@Slf4j
public class SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private RedisUtil redisUtil;

    public Map<String, List<SysPermission>> findPermissionMap() {
        String cacheKey = CacheKeyEnum.BACK_PERMISSION_ARRAY.getKey();
        LinkedHashMap<String, List<SysPermission>> map = redisUtil.getValue(cacheKey, LinkedHashMap.class);
        if (CollectionUtils.isEmpty(map)) {
            map = new LinkedHashMap<>();
            List<String> tagList = sysPermissionMapper.findTagList();
            for (String tag : tagList) {
                List<SysPermission> permissionList = sysPermissionMapper.findByTagName(tag);
                map.put(tag, permissionList);
            }
            redisUtil.setValue(cacheKey, map, CacheKeyEnum.BACK_PERMISSION_ARRAY.getExpire());
        }
        return map;
    }

    /**
     * 清除条件缓存
     * @return Integer
     */
    public Integer clear() {
        try {
            redisUtil.deleteKey(CacheKeyEnum.BACK_PERMISSION_ARRAY.getKey());
        } catch (Exception e) {
            log.error("SysPermissionService.clear : 清除缓存异常", e);
            return 0;
        }
        return 1;
    }
}
