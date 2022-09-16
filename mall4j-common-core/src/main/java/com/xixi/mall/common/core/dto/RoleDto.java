package com.xixi.mall.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色DTO
 */
@Getter
@Setter
public class RoleDto {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("菜单id列表")
    private List<Long> menuIds;

    @ApiModelProperty("菜单资源id列表")
    private List<Long> menuPermissionIds;

}
