package org.pos.coffee.bean.helper;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 1/11/2016.
 */
public class ItemUsedHelper {
    private Long orderId;
    private Map<Long,Double> itemUsedAndQuantity;
    private List<AddOnUsedHelper> addOnUsedHelperList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Map<Long, Double> getItemUsedAndQuantity() {
        return itemUsedAndQuantity;
    }

    public void setItemUsedAndQuantity(Map<Long, Double> itemUsedAndQuantity) {
        this.itemUsedAndQuantity = itemUsedAndQuantity;
    }

    public List<AddOnUsedHelper> getAddOnUsedHelperList() {
        return addOnUsedHelperList;
    }

    public void setAddOnUsedHelperList(List<AddOnUsedHelper> addOnUsedHelperList) {
        this.addOnUsedHelperList = addOnUsedHelperList;
    }
}
