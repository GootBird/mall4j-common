package com.xixi.mall.common.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单资源DTO
 */
@Getter
@Setter
@ToString
public class MenuPermissionDto {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单资源用户id")
    private Long menuPermissionId;

    @NotNull(message = "menuId NotNull")
    @ApiModelProperty("资源关联菜单")
    private Long menuId;

    @NotBlank(message = "permission NotBlank")
    @ApiModelProperty("权限对应的编码")
    private String permission;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("资源对应服务器路径")
    @NotBlank(message = "uri NotBlank")
    private String uri;

    @NotNull(message = "method NotNull")
    @ApiModelProperty("请求方法 1.GET 2.POST 3.PUT 4.DELETE")
    private Integer method;

}
