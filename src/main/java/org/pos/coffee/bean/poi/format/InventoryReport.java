package org.pos.coffee.bean.poi.format;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
        sheet = workbook.createSheet("Inventory");
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = null;

        cell = row.createCell(0);
        cell.setCellValue("Item Code");

        cell = row.createCell(1);
        cell.setCellValue("Item Name");

        cell = row.createCell(2);
        cell.setCellValue("Description");

        cell = row.createCell(3);
        cell.setCellValue("Current Stock");

        cell = row.createCell(4);
        cell.setCellValue("Critical Level");
    }

    @Override
    protected void publishData() {
        for(int i=0; i<stockHelperList.size();i++){
            StockHelper stockHelper = stockHelperList.get(i);
            XSSFRow row = sheet.createRow(i+1);
            XSSFCell cell = null;

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
        }
    }
}
