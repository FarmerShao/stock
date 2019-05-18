package com.farmershao.stock.persistence.mapper.system;

import com.farmershao.stock.persistence.model.system.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author : Zhao Da
 * @since : 2019/4/29 11:33
 */
public interface SysPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<String> findTagList();

    /**
     * 根据标签名查询对应权限列表
     * @param tagName
     * @return
     */
    List<SysPermission> findByTagName(@Param("tagName") String tagName);
}