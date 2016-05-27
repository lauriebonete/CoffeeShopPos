package org.pos.coffee.bean.helper;

/**
 * Created by Laurie on 5/27/2016.
 */
public class PendingPurchaseDTO {
    private String purchaseCode;
    private String status;

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
