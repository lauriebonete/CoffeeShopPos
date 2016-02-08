package org.pos.coffee.dao;

import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface SaleDaoJdbc {
    public Double getTotalSaleForDate(Date startDate, Date endDate);
    public List<Double> getSalesPerCategory(Date startDate, Date endDate);
    public List<Map<String,Double>> getDisSurTax(Date startDate, Date endDate);
    public List<ProductSaleHelper> getProductSalePerDate(Date startDate, Date endDate);
    public List<Double> getSalePerMonth(Date startDate, Date endDate);
    public List<CategoryHelper> getCategoryPercentage(Date startDate, Date endDate);
}
