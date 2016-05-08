package org.pos.coffee.dao;

import org.pos.coffee.bean.helper.TrendingProductDTO;
import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.LineChartDTO;
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
    public List<ProductSaleHelper> getProductExpensePerDate(Date startDate, Date endDate);
    public Map getProductSaleSummaryPerDate(Date startDate, Date endDate);
    public List<LineChartDTO> getSalePerMonth(Date startDate, Date endDate);
    public Map getSalePerWeek(Date startDate, Date endDate);
    public Double getSalePerDay(Date startDate, Date endDate);
    public Double getSaleCountPerDay(Date startDate, Date endDate);
    public List<CategoryHelper> getCategoryPercentage(Date startDate, Date endDate);
    public Map getAllSalesByProductPerDate(Date startDate, Date endDate);
    public List<TrendingProductDTO> getTrendingProduct(Date startDate, Date endDate);
}
