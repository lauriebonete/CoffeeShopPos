package org.pos.coffee.service;

import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.SaleOrderHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/21/2016.
 */
public interface ReportService {

    public List<SaleOrderHelper> prepareSalesData(Date startDate, Date endDate);
    public void buildReportData(List<SaleOrderHelper> saleOrderHelpers, List<ReferenceLookUp> categoryList, List<ProductGroup> productGroups);
}
