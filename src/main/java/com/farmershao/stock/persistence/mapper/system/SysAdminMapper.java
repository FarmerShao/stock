package com.farmershao.stock.persistence.mapper.system;

import com.farmershao.stock.persistence.model.system.SysAdmin;

/**
 * SysAdminMapper
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 */
public interface SysAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAdmin record);
}