package com.farmershao.stock.trade.model.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author 罗成
 **/
public class StaticParameter {
    // 存储账户信息
    public static final Map<Long, AccountDO> ACCOUNTDO_MAP = new HashMap<>();
    static {
        // 这里可以做成数据库加载 然后通过传入不同的 accountDO.id 可以取消不通的账号
        AccountDO accountDO = new AccountDO();
        accountDO.setId(1L);
        accountDO.setServers("115.238.108.58:7708,60.12.142.37:7708,60.191.116.36");
        accountDO.setSalesDepartmentId("1");
        // 这里填写 券商账号
        accountDO.setAccountNo("");
        // 这里填写 券商交易账号
        accountDO.setTradeAccountNo("");
        // 这里填写 券商交易密码
        accountDO.setTradePassword("");
        // 这里填写 券商通讯密码 一般为空
        accountDO.setCommunicationPassword(null);
        accountDO.setBrokerCode("98");
        accountDO.setAccountType("8");
        ACCOUNTDO_MAP.put(1L, accountDO);
    }
}
