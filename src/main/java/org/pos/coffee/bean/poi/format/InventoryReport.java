package org.pos.coffee.bean.poi.format;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.bean.poi.Report;

import java.util.List;

/**
 * Created by Laurie on 2/4/2016.
 */
public class InventoryReport extends Report {

    List<StockHelper> stockHelperList;

    public InventoryReport(List<StockHelper> stockHelperList){
        this.stockHelperList = stockHelperList;
    }

    @Override
    protected void publishHeader() {
        XSSFCellStyle alignStyle = workbook.createCellStyle();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        alignStyle.setFont(boldFont);

        sheet = workbook.createSheet("Inventory");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;

        cell = row.createCell(0);
        cell.setCellValue("Item Code");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(1);
        cell.setCellValue("Item Name");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(2);
        cell.setCellValue("Description");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(3);
        cell.setCellValue("Current Stock");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(4);
        cell.setCellValue("Critical Level");
        cell.setCellStyle(alignStyle);
    }

    @Override
    protected void publishData() {
        XSSFCellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.RED));
        redStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        XSSFCellStyle orangeStyle = workbook.createCellStyle();
        orangeStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.ORANGE));
        orangeStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        XSSFCellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.GREEN));
        greenStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        XSSFCellStyle grayStyle = workbook.createCellStyle();
        grayStyle.setFillForegroundColor(new XSSFColor(java.awt.Color.LIGHT_GRAY));
        grayStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

        XSSFCellStyle cellStyle = null;
        for(int i=0; i<stockHelperList.size();i++){
            StockHelper stockHelper = stockHelperList.get(i);
            XSSFRow row = sheet.createRow(i+1);
            XSSFCell cell = null;

            switch (stockHelper.getStatus()){
                case "critical": cellStyle = redStyle;
                                break;
                case "low": cellStyle = orangeStyle;
                            break;
                case "good":    cellStyle = greenStyle;
                            break;
                default: cellStyle = grayStyle;
                    break;
            }

            cell = row.createCell(0);
            cell.setCellValue(stockHelper.getItem().getItemCode());

            cell = row.createCell(1);
            cell.setCellValue(stockHelper.getItem().getItemName());

            cell = row.createCell(2);
            cell.setCellValue(stockHelper.getItem().getDescription());

            cell = row.createCell(3);
            cell.setCellValue(stockHelper.getQuantity()+" "+stockHelper.getItem().getUom().getValue());

            cell = row.createCell(4);
            cell.setCellValue(stockHelper.getItem().getCriticalLevel()+" "+stockHelper.getItem().getUom().getValue());

            cell = row.createCell(5);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("");
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
    }
}
