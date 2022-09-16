package order.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单关闭
 */
@Getter
@AllArgsConstructor
public enum OrderCloseType {

    /**
     * 超时未支付
     */
    OVERTIME(1),

    /**
     * 买家取消
     */
    BUYER(4),

    /**
     * 已通过货到付款交易
     */
    DELIVERY(15),
    ;

    private final Integer value;

}
