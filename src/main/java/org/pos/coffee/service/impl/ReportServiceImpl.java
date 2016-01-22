package org.pos.coffee.service.impl;

import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.CategorySaleHelper;
import org.pos.coffee.bean.helper.report.ProductGroupSaleHelper;
import org.pos.coffee.bean.helper.report.SaleOrderHelper;
import org.pos.coffee.dao.impl.SaleDaoJdbc;
import org.pos.coffee.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Laurie on 1/21/2016.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    @Qualifier("saleDaoJdbc")
    private SaleDaoJdbc saleDaoJdbc;

    @Override
    public List<SaleOrderHelper> prepareSalesData(Date startDate, Date endDate) {
        return saleDaoJdbc.getAllSales(startDate, endDate);
    }

    @Override
    public List<CategorySaleHelper> buildReportData(List<Product> productList, List<ReferenceLookUp> categoryList, List<ProductGroup> productGroups) {
        Set<ProductGroupSaleHelper> productGroupSaleHelperSet = new HashSet<>();
        for(ProductGroup productGroup: productGroups){
            Set<Product> parent = new HashSet<>();
            Set<ReferenceLookUp> size = new HashSet<>();
            Set<Long> categoryIds = new HashSet<>();

            List<Product> products = new ArrayList<>();
            for(Product product: productList){
                if(productGroup.getId()==product.getProductGroup().getId()){
                    products.add(product);

                    if(product.getParentProduct()!=null){
                        Product parentProduct = product.getParentProduct();
                        parent.add(parentProduct);
                    } else {
                        parent.add(product);
                    }

                    if(product.getSize()!=null){
                        ReferenceLookUp sizeReference = product.getSize();
                        size.add(sizeReference);
                    }

                    categoryIds.add(product.getCategoryId());
                }
            }
            ProductGroupSaleHelper productGroupSaleHelper = new ProductGroupSaleHelper();
            productGroupSaleHelper.setProductGroup(productGroup);
            productGroupSaleHelper.setParent(parent);
            productGroupSaleHelper.setSizes(size);
            productGroupSaleHelper.setCategorys(categoryIds);
            productGroupSaleHelper.setProductList(products);
            productGroupSaleHelperSet.add(productGroupSaleHelper);
        }

        List<CategorySaleHelper> categorySaleHelperList = new ArrayList<>();
        for(ReferenceLookUp category: categoryList){
            CategorySaleHelper categorySaleHelper = new CategorySaleHelper();
            categorySaleHelper.setCategory(category);

            List<ProductGroupSaleHelper> listProductGroupSaleHelper = new ArrayList<>();
            for(ProductGroupSaleHelper productGroupSaleHelper: productGroupSaleHelperSet){
                for(Long categoryId: productGroupSaleHelper.getCategorys()){
                    if(category.getId().equals(categoryId)){
                        listProductGroupSaleHelper.add(productGroupSaleHelper);
                        break;
                    }
                }
            }
            categorySaleHelper.setProductGroupSaleHelperSetList(listProductGroupSaleHelper);
            categorySaleHelperList.add(categorySaleHelper);
        }

        return categorySaleHelperList;
    }
}
