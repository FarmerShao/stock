package com.farmershao.stock.service.system;

import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.mapper.SysRoleMapper;
import com.farmershao.stock.persistence.model.SysRole;
import com.farmershao.stock.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 系统用户角色 Service
 *
 * @author : Zhao Da
 * @since : 2019/4/28 17:17
 */
@Service
@Slf4j
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    public boolean containsPermission(String id, String permission) {

        String cacheKey = CacheKeyEnum.BACK_PERMISSION_ARRAY.getKey() + id;
        String permissions = redisUtil.getValue(cacheKey, String.class);
        if (StringUtils.isEmpty(permissions)) {
            SysRole sysRole = findById(Long.valueOf(id.trim()));
            permissions = sysRole.getPermissions();
            redisUtil.setValue(cacheKey, permissions, CacheKeyEnum.BACK_PERMISSION_ARRAY.getExpire());
        }
        if (StringUtils.isEmpty(permissions)) {
            log.warn("没有找到该角色的权限配置,role:" + id);
            return false;
        }
        List<String> permissionList = Arrays.asList(permissions.split(","));
        return permissionList.contains(permission);
    }

    public PageInfo<SysRole> selectList(int pageNum, int pageSize, SysRoleDto sysRoleDto) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {sysRoleMapper.selectList(sysRoleDto);});
    }

    public Integer update(SysRole sysRole) {
        try {
            return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        } catch (SqlSessionException e) {
            log.error("角色信息更新异常 SysRoleService.update()", e);
        }
        return 0;
    }
}
