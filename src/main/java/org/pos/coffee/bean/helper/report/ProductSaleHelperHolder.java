package org.pos.coffee.bean.helper.report;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 2/2/2016.
 */
public class ProductSaleHelperHolder {
    private Date date;
    private List<ProductSaleHelper> productSaleHelperList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductSaleHelper> getProductSaleHelperList() {
        return productSaleHelperList;
    }

    public void setProductSaleHelperList(List<ProductSaleHelper> productSaleHelperList) {
        this.productSaleHelperList = productSaleHelperList;
    }
}
