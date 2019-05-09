package com.farmershao.stock.persistence.model.system;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SysAdmin
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Getter
@Setter
@ToString
public class SysAdmin {
    private Integer id;

    /**
    * 用户创建时间
    */
    private Date createdAt;

    /**
    * 用户修改时间
    */
    private Date updatedAt;

    /**
    * 角色ID
    */
    private Integer roleId;

    /**
    * 用户名称
    */
    private String userName;

    /**
    * 用户密码
    */
    private String userPwd;

    /**
    * 最后登录IP
    */
    private String lastLoginIp;

    private Date lastLoginTime;

    /**
    * 角色类型 ：1.系统管理员
    */
    private Byte roleType;

    /**
    * 1.删除 2.未删除
    */
    private Byte disableFlag;

    private String loginKey;

    /**
    * 备注
    */
    private String remark;
}