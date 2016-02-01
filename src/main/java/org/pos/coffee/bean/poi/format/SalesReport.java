package org.pos.coffee.bean.poi.format;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.CategorySaleHelper;
import org.pos.coffee.bean.helper.report.ProductGroupSaleHelper;
import org.pos.coffee.bean.helper.report.SaleOrderHelper;
import org.pos.coffee.bean.poi.Report;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Laurie on 1/21/2016.
 */
public class SalesReport extends Report {

    private List<CategorySaleHelper> categorySaleHelperList;
    private List<SaleOrderHelper> saleOrderHelperList;

    @Autowired
    private SaleService saleService;

    @Override
    protected void publishHeader() {

    }

    public SalesReport(List<CategorySaleHelper> categorySaleHelperList, List<SaleOrderHelper> saleOrderHelperList){

    }

    @Override
    protected void publishData() {
        publishData(this.categorySaleHelperList, this.saleOrderHelperList);
    }

    protected void publishData(List<CategorySaleHelper> categorySaleHelperList, List<SaleOrderHelper> saleOrderHelperList){

    }

    private int getHighestNumberOfQTY(List<CategorySaleHelper> categorySaleHelperList){
        int maxSize = 0;
        for(CategorySaleHelper categorySaleHelper: categorySaleHelperList){
            for(ProductGroupSaleHelper productGroupSaleHelper : categorySaleHelper.getProductGroupSaleHelperSetList()){
                if(productGroupSaleHelper.getSizes().size()>maxSize){
                    maxSize = productGroupSaleHelper.getSizes().size();
                }
            }
        }

        return maxSize;
    }

    private void createSaleReportSummary(){
        Double totalSaleForDate =  saleService.getTotalSaleForDate(new Date(), new Date());
    }

}
