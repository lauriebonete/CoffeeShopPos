package org.pos.coffee.bean.helper.report;

import org.pos.coffee.bean.ReferenceLookUp;

import java.util.List;
import java.util.Set;

/**
 * Created by Laurie on 1/22/2016.
 */
public class CategorySaleHelper {
    private ReferenceLookUp category;
    private List<ProductGroupSaleHelper> productGroupSaleHelperSetList;

    public ReferenceLookUp getCategory() {
        return category;
    }

    public void setCategory(ReferenceLookUp category) {
        this.category = category;
    }

    public List<ProductGroupSaleHelper> getProductGroupSaleHelperSetList() {
        return productGroupSaleHelperSetList;
    }

    public void setProductGroupSaleHelperSetList(List<ProductGroupSaleHelper> productGroupSaleHelperSetList) {
        this.productGroupSaleHelperSetList = productGroupSaleHelperSetList;
    }
}
