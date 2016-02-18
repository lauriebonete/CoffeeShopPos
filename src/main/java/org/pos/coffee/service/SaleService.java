package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.bean.helper.OrderExpenseHelper;
import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 12/14/2015.
 */
public interface SaleService extends BaseCrudService<Sale> {
    public Double countExpensePerOrder(List<OrderExpenseHelper> orderExpenseHelperList);
    public void createSaleAndOrders(Sale sale);
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry);
    public Sale confirmSaleTransaction(Sale sale) throws Exception;
    public Double getTotalSaleForDate(Date startDate, Date endDate);
    public List<Double> getSalesPerCategory(Date startDate, Date endDate);
    public List<Map<String,Double>> getDisSurTax(Date startDate, Date endDate);
    public List<ProductSaleHelper> getProductSalePerDate(Date startDate, Date endDate);
    public List<Double> getSalePerMonth(Date startDate, Date endDate);
    public List<Double> getSalePerWeek(Date startDate, Date endDate);
    public List<Double> getSalePerDay(Date startDate, Date endDate);
    public List<Double> getSaleCountPerDay(Date startDate, Date endDate);
    public List<CategoryHelper> getCategoryPercentage(Date startDate, Date endDate);
}
