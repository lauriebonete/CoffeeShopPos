package org.pos.coffee.bean.poi.format;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
        cell.setCellValue("Quantity Received");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(3);
        cell.setCellValue("Price");
        cell.setCellStyle(alignStyle);

    }

    @Override
    protected void publishData() {

        int indexRow = 1;
        for(int i=0;i<purchaseReportHelperList.size();i++){
            PurchaseReportHelper purchaseReportHelper = purchaseReportHelperList.get(i);

            XSSFRow row = sheet.createRow(indexRow);
            XSSFCell cell = null;

            cell = row.createCell(0);
            cell.setCellValue(purchaseReportHelper.getItemCode());

            cell = row.createCell(1);
            cell.setCellValue(purchaseReportHelper.getItemName());

            cell = row.createCell(2);
            cell.setCellValue(purchaseReportHelper.getQtyReceived()+" "+ purchaseReportHelper.getUom());

            cell = row.createCell(3);
            cell.setCellValue(purchaseReportHelper.getPrice());

            indexRow++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        XSSFRow row = sheet.createRow(indexRow+2);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("TOTAL");

        StringBuilder formulaBuilder = new StringBuilder();
        formulaBuilder.append("SUM(D")
                .append("2:D")
                .append(indexRow+")");
        cell = row.createCell(1);
        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula(formulaBuilder.toString());

    }
}
