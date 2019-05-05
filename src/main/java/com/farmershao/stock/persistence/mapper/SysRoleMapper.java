package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.dto.SysRoleDto;
import com.farmershao.stock.persistence.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author : Zhao Da
 * @since : 2019/4/28 17:17
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