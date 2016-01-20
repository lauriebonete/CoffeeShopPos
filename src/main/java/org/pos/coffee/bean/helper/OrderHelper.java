package org.pos.coffee.bean.helper;

import org.pos.coffee.bean.AddOn;
import org.pos.coffee.bean.Product;

import java.util.List;

/**
 * Created by Laurie on 12/7/2015.
 */
public class OrderHelper {

    private Long productId;
    private Long quantity;
    private Long listId;
    private Double price;
    private Product product;
    private List<AddOn> addOn;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<AddOn> getAddOn() {
        return addOn;
    }

    public void setAddOn(List<AddOn> addOn) {
        this.addOn = addOn;
    }
}
