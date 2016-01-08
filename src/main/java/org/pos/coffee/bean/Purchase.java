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
        IN_PROGRESS("In Progress"),
        RECEIVED("Received"),
        CANCELLED("Cancelled");

        private String status;

        private Status(String status){
            this.status = status;
        }

        public String getValue(){
            return this.status;
        }

        public static Status findByString(String status){
            for(Status stat : values()){
                if( stat.getValue().equals(status)){
                    return stat;
                }
            }
            return null;
        }
    }

    @Column(name = "PURCHASE_CODE", nullable = false, unique = true)
    @UniqueField
    private String purchaseCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
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

    private transient Boolean isForReceival;

    public Boolean getIsForReceival() {
        if(Status.IN_TRANSIT.equals(Status.findByString(this.status))
                || Status.IN_PROGRESS.equals(Status.findByString(this.status))
                || Status.RECEIVED.equals(Status.findByString(this.status))) {
            return true;
        }
        return false;
    }

    public void setIsForReceival(Boolean isForReceival) {
        this.isForReceival = isForReceival;
    }

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
