package org.pos.coffee.bean.helper;

/**
 * Created by Laurie on 1/11/2016.
 */
public class OrderExpenseHelper {
    private Long itemId;
    private Double expense;
    private Long orderId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
