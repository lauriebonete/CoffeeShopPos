package org.pos.coffee.bean.helper;

/**
 * Created by Laurie on 5/8/2016.
 */
public class TrendingProductDTO {
    private String productName;
    private Long count;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
