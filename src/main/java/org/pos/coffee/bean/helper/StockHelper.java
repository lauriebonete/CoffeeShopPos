package org.pos.coffee.bean.helper;

import org.pos.coffee.bean.Item;

/**
 * Created by Laurie on 1/5/2016.
 */
public class StockHelper {
    private Item item;
    private Double quantity;
    private String status;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public StockHelper(Item item, Double quantity) {
        this.item = item;

        setStatus(item,quantity);

        if(quantity == null){
            quantity = 0D;
        }
        this.quantity = quantity;
    }

    public StockHelper(Item item,Double quantity, Double criticalLevel, Double demarcation){
        this(item,quantity);
    }

    public StockHelper() {
    }

    private void setStatus(Item item, Double quantity){
        item.getUom().getValue();
        if(quantity==null){
            this.status = "empty";

            //just load the items
        } else {
            Double criticalLevel = item.getCriticalLevel();
            Double demarcation = item.getDemarcation();

            if(criticalLevel==null){
                criticalLevel = 0D;
            }

            if(demarcation==null){
                demarcation = 0D;
            }

            if(quantity>criticalLevel+demarcation){
                this.status = "good";
            } else if(quantity<=criticalLevel+demarcation
                    && quantity>criticalLevel){
                this.status = "low";
            } else if (quantity<=criticalLevel) {
                this.status = "critical";
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
