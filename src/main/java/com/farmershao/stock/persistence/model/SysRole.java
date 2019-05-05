package com.farmershao.stock.persistence.model;

import lombok.Getter;
import lombok.Setter;

/**
 *  角色 entity
 *
 * @author : Zhao Da
 * @since : 2019/4/28 17:17
 */
@Getter
@Setter
public class SysRole {
    /**
    * 角色id
    */
    private Long id;

    /**
    * 角色名
    */
    private String roleName;

    /**
    * 角色描述
    */
    private String remark;

    /**
    * 权限列表(英文逗号分隔）
    */
    private String permissions;
}