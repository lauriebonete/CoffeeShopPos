package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.evey.annotation.JoinList;
import org.evey.annotation.UniqueField;
import org.evey.bean.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Entity
@Table(name = "PURCHASE")
public class Purchase extends BaseEntity {

    public static enum Status {
        FOR_APPROVAL("For Approval"),
        IN_TRANSIT("In Transit"),
        RECEIVED("Received");

        private String status;

        private Status(String status){
            this.status = status;
        }

        public String getValue(){
            return this.status;
        }
    }

    @Column(name = "PURCHASE_CODE", nullable = false, unique = true)
    @UniqueField
    private String purchaseCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseOrder> purchaseOrders;

    @Column(name="PURCHASE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Column(name = "SUPPLIER_DETAILS")
    private String supplier;

    @Column(name = "TOTAL_EXPENSE")
    private Double totalExpense;

    @Column(name = "STATUS")
    private String status;

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
