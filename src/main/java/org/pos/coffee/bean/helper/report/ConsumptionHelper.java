package org.pos.coffee.bean.helper.report;

/**
 * Created by Laurie on 3/10/2016.
 */
public class ConsumptionHelper {
    private String itemCode;
    private String itemName;
    private String consumed;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getConsumed() {
        return consumed;
    }

    public void setConsumed(String consumed) {
        this.consumed = consumed;
    }
}
