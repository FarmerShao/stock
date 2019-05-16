package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.EntrustOrder;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface EntrustOrderMapper {
    int insertSelective(EntrustOrder record);

    EntrustOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EntrustOrder record);
}