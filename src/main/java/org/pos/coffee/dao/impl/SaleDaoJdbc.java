package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.SaleOrderHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface SaleDaoJdbc {
    public List<SaleOrderHelper> getAllSales(Date startDate, Date endDate);
    public Double getTotalSaleForDate(Date startDate, Date endDate);
    public List<Double> getSalesPerCategory(Date startDate, Date endDate);
    public List<Map<String,Double>> getDisSurTax(Date startDate, Date endDate);
}
