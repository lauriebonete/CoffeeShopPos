package org.pos.coffee.bean.poi.format;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.pos.coffee.bean.helper.report.ConsumptionHelper;
import org.pos.coffee.bean.poi.Report;

import java.util.List;

/**
 * Created by Laurie on 3/10/2016.
 */
public class ConsumptionReport extends Report {

    private List<ConsumptionHelper> consumptionHelperList;

    public ConsumptionReport(List<ConsumptionHelper> consumptionHelperList){
        this.consumptionHelperList = consumptionHelperList;
    }

    @Override
    protected void publishHeader() {
        XSSFCellStyle alignStyle = workbook.createCellStyle();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        alignStyle.setFont(boldFont);

        sheet = workbook.createSheet("Consumption");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;

        cell = row.createCell(0);
        cell.setCellValue("Item Code");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(1);
        cell.setCellValue("Item Name");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(2);
        cell.setCellValue("Consumed");
        cell.setCellStyle(alignStyle);
    }

    @Override
    protected void publishData() {
        XSSFCellStyle alignStyle = workbook.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        int indexRow=1;
        for(ConsumptionHelper consumptionHelper: consumptionHelperList){
            XSSFRow row = sheet.createRow(indexRow);
            XSSFCell cell = null;

            cell = row.createCell(0);
            cell.setCellValue(consumptionHelper.getItemCode());
            cell.setCellStyle(alignStyle);

            cell = row.createCell(1);
            cell.setCellValue(consumptionHelper.getItemName());
            cell.setCellStyle(alignStyle);

            cell = row.createCell(2);
            cell.setCellValue(consumptionHelper.getConsumed());
            cell.setCellStyle(alignStyle);
            indexRow++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
    }
}
