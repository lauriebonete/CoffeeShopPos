package org.pos.coffee.bean.helper.report;

import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;

import java.util.List;
import java.util.Set;

/**
 * Created by Laurie on 1/22/2016.
 */
public class ProductGroupSaleHelper {
    private ProductGroup productGroup;
    private Set<ReferenceLookUp> sizes;
    private Set<Product> parent;
    private ReferenceLookUp category;
    private List<Product> productList;

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public Set<ReferenceLookUp> getSizes() {
        return sizes;
    }

    public void setSizes(Set<ReferenceLookUp> sizes) {
        this.sizes = sizes;
    }

    public Set<Product> getParent() {
        return parent;
    }

    public void setParent(Set<Product> parent) {
        this.parent = parent;
    }

    public ReferenceLookUp getCategory() {
        return category;
    }

    public void setCategory(ReferenceLookUp category) {
        this.category = category;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
