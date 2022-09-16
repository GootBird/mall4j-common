package com.xixi.mall.common.core.webbase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Setter
@Getter
@ToString
public class OrderSearchDTO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 店铺id
     */
    private Long shopId;

    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("是否已经支付，1：已经支付过，0：没有支付过")
    private Integer isPayed;

    /**
     * 订购流水号
     */
    @ApiModelProperty("订单号")
    private Long orderId;

    @ApiModelProperty("下单的时间范围:开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("下单的时间范围:结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("收货人姓名")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty("物流类型 3：无需快递")
    private Integer deliveryType;

    @ApiModelProperty("开始页")
    private Integer pageNum;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

}
