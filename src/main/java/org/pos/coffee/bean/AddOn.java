package org.pos.coffee.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.evey.bean.BaseEntity;

import javax.persistence.*;

/**
 * Created by Laurie on 1/20/2016.
 */
@Entity
@Table(name = "ADD_ON")
public class AddOn extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Double quantity;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "COST")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Order order;

    @Column(name = "ORDER_ID", insertable = false, updatable = false)
    private Long orderId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
