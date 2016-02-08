package org.pos.coffee.dao;

import org.pos.coffee.bean.helper.report.PurchaseReportHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 2/8/2016.
 */
public interface PurchaseDaoJdbc {
    public List<PurchaseReportHelper> getPurchaseUsingDateRange(Date startDate, Date endDate);
}
