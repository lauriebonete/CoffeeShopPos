package org.pos.coffee.service.impl;

import org.pos.coffee.bean.helper.report.MonthlySaleCategoryHelper;
import org.pos.coffee.service.ReportService;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Laurie on 1/21/2016.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    private SaleService saleService;

    @Override
    public MonthlySaleCategoryHelper createSaleReportSummary(Date startDate, Date endDate) {
        MonthlySaleCategoryHelper monthlySaleCategoryHelper = new MonthlySaleCategoryHelper();
        List<Double> categorySaleList = saleService.getSalesPerCategory(startDate,endDate);
        Double totalSaleForDate =  saleService.getTotalSaleForDate(startDate, endDate);
        List<Map<String,Double>> saleDiscountSurcharge = saleService.getDisSurTax(startDate,endDate);

        List<Double> saleBalanceList = new ArrayList<>();
        for(Double categorySale: categorySaleList){
            if(totalSaleForDate>0){
                saleBalanceList.add((categorySale/totalSaleForDate)*100);
            } else {
                saleBalanceList.add(0D);
            }

        }

        for(Map<String,Double> saleDisSur:saleDiscountSurcharge){
            monthlySaleCategoryHelper.setDiscount(saleDisSur.get("discount"));
            monthlySaleCategoryHelper.setSurcharge(saleDisSur.get("surcharge"));
            monthlySaleCategoryHelper.setTax(saleDisSur.get("tax"));
        }

        monthlySaleCategoryHelper.setCategorySale(categorySaleList);
        monthlySaleCategoryHelper.setTotalSales(totalSaleForDate);
        monthlySaleCategoryHelper.setSaleBalance(saleBalanceList);
        monthlySaleCategoryHelper.setDate(startDate);

        return monthlySaleCategoryHelper;
    }
}
