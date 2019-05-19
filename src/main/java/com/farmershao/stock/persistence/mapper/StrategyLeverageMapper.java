package com.farmershao.stock.persistence.mapper;

import com.farmershao.stock.persistence.model.StrategyLeverage;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * StrategyLeverageMapper
 *
 * @author Shao Yu
 * @since 2019/5/19 21:03
 **/
public interface StrategyLeverageMapper {

    /**
     * 添加默认杠杆配置
     * @param entity
     */
    void insert(StrategyLeverage entity);

    /**
     * 修改默认杠杆配置
     * @param entity
     * @return
     */
    int update(StrategyLeverage entity);

    /**
     * 删除默认杠杆配置
     * @param id
     * @return
     */
    int delete(@Param("id") long id);

    /**
     * 查询所有的默认杠杆配置
     * @return
     */
    List<StrategyLeverage> findAll();

    int findByLever(StrategyLeverage strategyLeverage);

    StrategyLeverage findLeverageByLever(@Param("defaultLeverage") BigDecimal lever);

}
