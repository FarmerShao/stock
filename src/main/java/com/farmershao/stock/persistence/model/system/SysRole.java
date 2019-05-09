package com.farmershao.stock.persistence.model.system;

import lombok.Getter;
import lombok.Setter;

/**
 *  角色 entity
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
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