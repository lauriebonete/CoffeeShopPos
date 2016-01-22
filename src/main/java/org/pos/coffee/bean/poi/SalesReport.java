package org.pos.coffee.bean.poi;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Laurie on 1/21/2016.
 */
public class SalesReport extends Report {

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

    @Override
    protected void publishData() {

    }
}
