package com.xixi.mall.common.database.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class PageBo {

    /**
     * 当前页
     */
    @NotNull(message = "pageNum 不能为空")
    @ApiModelProperty(value = "当前页", required = true)
    private Integer pageNum;

    @NotNull(message = "pageSize 不能为空")
    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "排序字段数组，用逗号分割")
    private String[] columns;

    @ApiModelProperty(value = "排序字段方式，用逗号分割，ASC正序，DESC倒序")
    private String[] orders;

}
