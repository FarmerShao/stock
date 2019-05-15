package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.EntrustOrder;

/**
 * EntrustOrderMapper
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
public interface EntrustOrderMapper {

    int insertSelective(EntrustOrder record);

    EntrustOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EntrustOrder record);
}