package com.xixi.mall.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 菜单id和权限id列表
 */
@Setter
@Getter
@ToString
public class MenuWithPermissionIdDto {

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单下的权限id列表")
    private List<Long> permissionIds;

}
