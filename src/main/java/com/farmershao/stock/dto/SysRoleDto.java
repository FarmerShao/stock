package com.farmershao.stock.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回 web 角色实体
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 */
@ApiModel(description = "角色列表")
@Getter
@Setter
public class SysRoleDto {

    @ApiModelProperty(value="角色id",name="id")
    private Long id;

    @ApiModelProperty(value="角色名",name="roleName")
    private String roleName;
}
