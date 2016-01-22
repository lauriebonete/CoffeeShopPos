package org.pos.coffee.bean.poi.format;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.poi.Report;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Laurie on 1/21/2016.
 */
public class SalesReport extends Report {

    private List<ProductGroup> productGroups;

    @Override
    protected void publishHeader() {

        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());


        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Date: "+month);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        cell = row.createCell(5);
        cell.setCellValue("TOTAL SALES");
        sheet.addMergedRegion(new CellRangeAddress(0,0,5,8));

    }

    public SalesReport(List<ProductGroup> productGroupList){
        super("Sales Report");
        this.productGroups = productGroupList;

    }

    @Override
    protected void publishData() {
        publishData(this.productGroups);
    }

    protected void publishData(List<ProductGroup> productGroupList){
        for(int i=0; i<=productGroupList.size()-1;i++){
            XSSFRow row = sheet.createRow(i+1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(productGroupList.get(i).getProductGroupName());
        }
    }

    private void printCategoryHeader(List<ReferenceLookUp> category){

    }
}
