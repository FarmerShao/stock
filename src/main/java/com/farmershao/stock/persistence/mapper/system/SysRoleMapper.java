package com.farmershao.stock.persistence.mapper.system;

import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.model.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SysRoleMapper
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
public interface SysRoleMapper {
    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    /**
     * 查询角色列表
     * @param sysRoleDto
     * @return
     */
    List<SysRole> selectList(@Param("search") SysRoleDto sysRoleDto);
}