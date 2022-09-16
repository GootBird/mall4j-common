package order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 购物车VO
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ShopCartVO {

    @ApiModelProperty(value = "店铺ID", required = true)
    private Long shopId;

    @ApiModelProperty(value = "店铺名称", required = true)
    private String shopName;

    @ApiModelProperty("店铺类型1自营店 2普通店")
    private Integer shopType;

    @ApiModelProperty("购物车商品信息")
    private List<ShopCartItemVO> shopCartItem;

    @ApiModelProperty(value = "商品总值", required = true)
    private Long total;

    @ApiModelProperty(value = "数量", required = true)
    private Integer totalCount;

}
