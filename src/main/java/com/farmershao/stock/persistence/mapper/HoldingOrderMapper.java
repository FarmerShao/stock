package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.HoldingOrder;

/**
 * HoldingOrderMapper
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
public interface HoldingOrderMapper {
    int insertSelective(HoldingOrder record);

    HoldingOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HoldingOrder record);
}