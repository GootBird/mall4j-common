package order.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 订单支付成功通知
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class PayNotifyBo {

    private List<Long> orderIds;

}
