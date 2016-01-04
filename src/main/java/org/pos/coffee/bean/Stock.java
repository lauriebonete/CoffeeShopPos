package org.pos.coffee.bean;

import org.evey.bean.BaseEntity;

import javax.persistence.*;

/**
 * Created by Laurie on 1/4/2016.
 */
@Entity
@Table(name = "STOCK")
public class Stock extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID")
    private Item item;

    @Column(name = "QUANTITY")
    private Double quantity;

    private transient String status;

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

    public String getStatus() {
        if(this.item != null){
            if(this.quantity > this.item.getCriticalLevel()){
                return "GREEN";
            }
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
