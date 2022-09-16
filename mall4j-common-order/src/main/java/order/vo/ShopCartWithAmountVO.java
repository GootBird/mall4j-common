package order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@ToString
@Accessors(chain = true)
public class ShopCartWithAmountVO {

    @ApiModelProperty("总额")
    private Long totalMoney;

    @ApiModelProperty("总计")
    private Long finalMoney;

    @ApiModelProperty("商品数量")
    private Integer count;

    @ApiModelProperty("多个店铺的购物车信息")
    private List<ShopCartVO> shopCarts;

}
