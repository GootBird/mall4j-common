package order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xixi.mall.common.core.handle.ImgJsonSerializerHandle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ShopCartItemVO implements Serializable {

    @ApiModelProperty(value = "加入购物车时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "购物车ID", required = true)
    private Long cartItemId;

    @ApiModelProperty("店铺ID")
    private Long shopId;

    @ApiModelProperty("产品ID")
    private Long spuId;

    @ApiModelProperty("SkuID")
    private Long skuId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("购物车产品个数")
    private Integer count;

    @ApiModelProperty("是否已经勾选")
    private Integer isChecked;

    @ApiModelProperty("售价，加入购物车时的商品价格")
    private Long priceFee;

    @ApiModelProperty("当前商品价格")
    private Long skuPriceFee;

    @ApiModelProperty("当前总价格(商品价格 * 数量)")
    private Long totalPriceFee;

    @ApiModelProperty("当前总价格(商品价格 * 数量)(带小数)")
    private Long totalPrice;

    @ApiModelProperty("商品重量")
    private BigDecimal weight;

    @ApiModelProperty("商品体积")
    private BigDecimal volume;

    @ApiModelProperty("商品图片")
    @JsonSerialize(using = ImgJsonSerializerHandle.class)
    private String imgUrl;

    @ApiModelProperty(value = "总金额", required = true)
    private Long totalAmount;

    @ApiModelProperty("sku规格信息")
    private String skuName;

    @ApiModelProperty("spu名称")
    private String spuName;

}
