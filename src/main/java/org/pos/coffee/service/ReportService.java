package org.pos.coffee.service;

import org.pos.coffee.bean.helper.report.MonthlySaleCategoryHelper;

import java.util.Date;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface ReportService {

    public MonthlySaleCategoryHelper createSaleReportSummary(Date startDate, Date endDate);
}
