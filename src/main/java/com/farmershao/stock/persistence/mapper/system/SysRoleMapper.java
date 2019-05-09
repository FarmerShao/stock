package com.farmershao.stock.persistence.mapper.system;

import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.model.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 */
public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 查询角色列表
     * @param sysRoleDto
     * @return
     */
    List<SysRole> selectList(@Param("search") SysRoleDto sysRoleDto);
}