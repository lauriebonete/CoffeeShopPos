package org.pos.coffee.service;

import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.CategorySaleHelper;
import org.pos.coffee.bean.helper.report.MonthlySaleCategoryHelper;
import org.pos.coffee.bean.helper.report.SaleOrderHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface ReportService {

    public List<SaleOrderHelper> prepareSalesData(Date startDate, Date endDate);
    public List<CategorySaleHelper> buildReportData(List<Product> productList, List<ReferenceLookUp> categoryList, List<ProductGroup> productGroups);
    public MonthlySaleCategoryHelper createSaleReportSummary(Date startDate, Date endDate);
}
