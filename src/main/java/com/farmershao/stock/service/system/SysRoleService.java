package com.farmershao.stock.service.system;

import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.mapper.system.SysRoleMapper;
import com.farmershao.stock.persistence.model.system.SysRole;
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
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
@Service
@Slf4j
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据角色ID 查找角色信息
     * @param id    角色ID
     * @return
     */
    public SysRole findById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 判断用户是否有对应的权限
     *
     * @param roleId     角色Id
     * @param permission 权限
     * @return
     */
    public boolean containsPermission(String roleId, String permission) {

        String cacheKey = CacheKeyEnum.BACK_PERMISSION_ARRAY.getKey() + roleId;
        String permissions = redisUtil.getValue(cacheKey, String.class);
        if (StringUtils.isEmpty(permissions)) {
            SysRole sysRole = findById(Integer.valueOf(roleId.trim()));
            permissions = sysRole.getPermissions();
            redisUtil.setValue(cacheKey, permissions, CacheKeyEnum.BACK_PERMISSION_ARRAY.getExpire());
        }
        if (StringUtils.isEmpty(permissions)) {
            log.warn("没有找到该角色的权限配置,role:" + roleId);
            return false;
        }
        List<String> permissionList = Arrays.asList(permissions.split(","));
        return permissionList.contains(permission);
    }

    /**
     * 分页查询权限列表
     *
     * @param pageNum    页码
     * @param pageSize   分页大小
     * @param sysRoleDto 查询条件
     * @return PageInfo<SysRole>
     */
    public PageInfo<SysRole> selectList(int pageNum, int pageSize, SysRoleDto sysRoleDto) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            sysRoleMapper.selectList(sysRoleDto);
        });
    }

    /**
     * 更新系统权限
     * @param sysRole   系统权限
     * @return
     */
    public Integer update(SysRole sysRole) {
        try {
            return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        } catch (SqlSessionException e) {
            log.error("角色信息更新异常 SysRoleService.update()", e);
        }
        return 0;
    }
}
