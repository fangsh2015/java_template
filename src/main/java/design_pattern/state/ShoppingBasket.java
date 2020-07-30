package design_pattern.state;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物篮，在没有提交订单时，可以添加商品；提交订单后仅可以读取商品,无法添加。
 * Created by Niki on 2019/2/26 18:14
 */
public class ShoppingBasket {
    private String orderNo;
    private List<String> articleNumbers = new ArrayList<>();
    private UpdateState state = UpdateState.UPDATEABLE;

    public void add(String articleNumber) {
        this.articleNumbers.add(state.set(articleNumber));
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = state.set(orderNo);
    }

    /**
     * 下单逻辑，判断当前订单的状态
     */
    public void order() {
        state = UpdateState.READONLY;
    }

}
