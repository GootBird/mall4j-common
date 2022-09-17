package com.xixi.mall.common.core.webbase.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class PageVo {

    /**
     * 当前页
     */
    @NotNull(message = "pageNum 不能为空")
    @ApiModelProperty(value = "当前页", required = true)
    private Long pageNum;

    @NotNull(message = "pageSize 不能为空")
    @ApiModelProperty(value = "每页大小", required = true)
    private Long pageSize;

    @ApiModelProperty(value = "总条数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Long pages;

    @ApiModelProperty(value = "排序字段数组，用逗号分割")
    private String[] columns;

    @ApiModelProperty(value = "排序字段方式，用逗号分割，ASC正序，DESC倒序")
    private String[] orders;

}
