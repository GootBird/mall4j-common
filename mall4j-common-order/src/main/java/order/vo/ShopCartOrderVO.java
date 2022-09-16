package order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 单个店铺的订单信息
 */
@Setter
@Getter
@ToString
public class ShopCartOrderVO implements Serializable {

    @ApiModelProperty(value = "店铺id", required = true)
    private Long shopId;

    @ApiModelProperty(value = "店铺名称", required = true)
    private String shopName;

    @ApiModelProperty(value = "商品总值", required = true)
    private Long total;

    @ApiModelProperty(value = "购物车商品", required = true)
    private List<ShopCartItemVO> shopCartItemVO;

    @ApiModelProperty(value = "商品总数", required = true)
    private Integer totalCount;

}
