package order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 多个店铺订单合并在一起的合并类
 * "/confirm" 使用
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ShopCartOrderMergerVo {

    @ApiModelProperty(value = "商品总值", required = true)
    private Long total;

    @ApiModelProperty(value = "商品总数", required = true)
    private Integer totalCount;

    @ApiModelProperty(value = "配送类型 ：无需快递")
    private Integer dvyType;

    @ApiModelProperty(value = "过滤掉的商品项", required = true)
    private List<ShopCartItemVo> filterShopItems;

    @ApiModelProperty(value = "每个店铺的订单信息", required = true)
    List<ShopCartOrderVo> shopCartOrders;

    @ApiModelProperty(value = "用户地址")
    private UserAddrVo userAddr;

}
