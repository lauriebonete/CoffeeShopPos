package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Laurie on 1/4/2016.
 */
@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Purchase purchase;

    @Column(name = "PURCHASE_ID", insertable = false, updatable = false)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID")
    private Item orderedItem;

    @Column(name = "QTY_ORDER")
    private Double orderedQuantity;

    @Column(name = "QTY_RECEIVE")
    private Double receivedQuantity;

    @Column(name = "PRICE")
    private Double price;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Item getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(Item orderedItem) {
        this.orderedItem = orderedItem;
    }

    public Double getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Double orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Double getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Double receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }
}
