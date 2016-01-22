package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.helper.report.SaleOrderHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface SaleDaoJdbc {
    public List<SaleOrderHelper> getAllSales(Date startDate, Date endDate);
}
