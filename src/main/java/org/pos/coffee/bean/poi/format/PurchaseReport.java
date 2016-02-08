package org.pos.coffee.bean.poi.format;

import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.bean.helper.report.PurchaseReportHelper;
import org.pos.coffee.bean.poi.Report;

import java.util.List;

/**
 * Created by Laurie on 2/8/2016.
 */
public class PurchaseReport extends Report {

    private List<PurchaseReportHelper> purchaseReportHelperList;

    public PurchaseReport(List<PurchaseReportHelper> purchaseReportHelperList){
        this.purchaseReportHelperList = purchaseReportHelperList;
    }

    @Override
    protected void publishHeader() {

    }

    @Override
    protected void publishData() {

    }
}
