package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.HoldingOrder;

/**
 * Created by ShaoYu on 2019/5/16.
 */

public interface HoldingOrderMapper {
    int insertSelective(HoldingOrder record);

    HoldingOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HoldingOrder record);
}