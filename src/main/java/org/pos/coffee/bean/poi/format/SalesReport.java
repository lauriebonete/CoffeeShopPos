package org.pos.coffee.bean.poi.format;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.*;
import org.pos.coffee.bean.poi.Report;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Laurie on 1/21/2016.
 */
public class SalesReport extends Report {


    private MonthlySaleHelper monthlySaleHelper;

    @Autowired
    private SaleService saleService;

    @Override
    protected void publishHeader() {

    }

    public SalesReport(MonthlySaleHelper monthlySaleHelper){
        this.monthlySaleHelper = monthlySaleHelper;
    }

    @Override
    protected void publishData() {
        publishData(this.monthlySaleHelper);
    }

    protected void publishData(MonthlySaleHelper monthlySaleHelper){
        this.sheet = workbook.createSheet("Monthly Sale");
        createSaleReportSummaryHeader(monthlySaleHelper.getHeaders());
        createSaleReportSummary(monthlySaleHelper.getMonthlySaleCategoryHelperList());
    }

    private void createSaleReportSummaryHeader(List<String> categoryHeaders){
        XSSFRow row = sheet.createRow(0);
        int indexCell = 0;
        XSSFCell cell = row.createCell(indexCell);

        cell.setCellValue("Date");
        for(String category: categoryHeaders){
            indexCell++;
            cell = row.createCell(indexCell);
            cell.setCellValue(category);
        }

        cell = row.createCell(++indexCell);
        cell.setCellValue("Discount");

        cell = row.createCell(++indexCell);
        cell.setCellValue("Surcharge");

        cell = row.createCell(++indexCell);
        cell.setCellValue("Tax");

        cell = row.createCell(++indexCell);
        cell.setCellValue("Total Sales");

        for(String category: categoryHeaders){
            indexCell++;
            cell = row.createCell(indexCell);
            cell.setCellValue(category);
        }
    }

    private void createSaleReportSummary(List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList){
        int indexRow = 1;
        for(MonthlySaleCategoryHelper monthlySaleCategoryHelper: monthlySaleCategoryHelperList){

            int indexCell = 0;
            XSSFRow row = sheet.createRow(indexRow);
            XSSFCell cell = row.createCell(indexCell);

            cell.setCellValue(new SimpleDateFormat("MM-dd-yyyy").format(monthlySaleCategoryHelper.getDate()));
            for(Double categorySale: monthlySaleCategoryHelper.getCategorySale()){
                indexCell++;
                cell = row.createCell(indexCell);
                cell.setCellValue(categorySale);
            }

            cell = row.createCell(++indexCell);
            cell.setCellValue(monthlySaleCategoryHelper.getDiscount());

            cell = row.createCell(++indexCell);
            cell.setCellValue(monthlySaleCategoryHelper.getSurcharge());

            cell = row.createCell(++indexCell);
            cell.setCellValue(monthlySaleCategoryHelper.getTax());

            cell = row.createCell(++indexCell);
            cell.setCellValue(monthlySaleCategoryHelper.getTotalSales());

            for(Double balance: monthlySaleCategoryHelper.getSaleBalance()){
                indexCell++;
                cell = row.createCell(indexCell);
                cell.setCellValue(balance);
            }

            indexRow++;
        }
    }

}
