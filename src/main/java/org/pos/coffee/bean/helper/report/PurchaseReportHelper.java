package org.pos.coffee.bean.helper.report;

/**
 * Created by Laurie on 2/8/2016.
 */
public class PurchaseReportHelper {

    private Double qtyReceived;
    private Double price;
    private String itemCode;
    private String itemName;
    private String uom;

    public Double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(Double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

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

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
