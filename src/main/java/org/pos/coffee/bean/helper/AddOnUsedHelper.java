package org.pos.coffee.bean.helper;

import java.util.Map;

/**
 * Created by Laurie on 6/2/2016.
 */
public class AddOnUsedHelper {
    private Long addOnId;
    private Map<Long,Double> itemUsed;

    public Long getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(Long addOnId) {
        this.addOnId = addOnId;
    }

    public Map<Long, Double> getItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(Map<Long, Double> itemUsed) {
        this.itemUsed = itemUsed;
    }
}
