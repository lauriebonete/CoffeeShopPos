package org.pos.coffee.bean.poi.format;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelperHolder;
import org.pos.coffee.bean.poi.Report;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Laurie on 2/11/2016.
 */
public class ExpenseReport extends Report {

    List<ProductSaleHelperHolder> productSaleHelperHolderList;

    public ExpenseReport(List<ProductSaleHelperHolder> productSaleHelperHolderList){
        this.productSaleHelperHolderList = productSaleHelperHolderList;
    }

    @Override
    protected void publishHeader() {

    }

    @Override
    protected void publishData() {
        for(ProductSaleHelperHolder productSaleHelperHolder: productSaleHelperHolderList){
            this.sheet = workbook.createSheet(new SimpleDateFormat("MM-dd-yyyy").format(productSaleHelperHolder.getDate()));
            createProductSaleReportForDateHeader();
            createProductSaleReportForDate(productSaleHelperHolder);

        }
    }

    private void createProductSaleReportForDate(ProductSaleHelperHolder productSaleHelperHolder){
        int indexRow = 1;
        Long categoryId = null;
        Long groupId = null;
        Long parentId = null;
        int categoryRow = 1;
        int groupRow = 1;
        int parentRow = 1;

        XSSFCellStyle alignStyle = workbook.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

        XSSFCellStyle boldStyle = workbook.createCellStyle();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        XSSFCellStyle productGroupStyle = workbook.createCellStyle();
        XSSFFont productGroupFont = workbook.createFont();
        productGroupFont.setBold(true);
        productGroupFont.setItalic(true);
        productGroupStyle.setFont(productGroupFont);
        productGroupStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        for(ProductSaleHelper productSaleHelper: productSaleHelperHolder.getProductSaleHelperList()){
            XSSFRow row = sheet.createRow(indexRow);
            int indexCell = 0;
            XSSFCell cell = row.createCell(indexCell);
            sheet.setRowSumsBelow(false);


            if(productSaleHelper.getCategoryId()!=categoryId) {
                sheet.groupRow(categoryRow+1,indexRow-1);
                sheet.setRowGroupCollapsed(categoryRow+1,true);
                sheet.groupRow(groupRow+1,indexRow-1);
                sheet.groupRow(parentRow+1,indexRow-1);
                categoryRow = indexRow;
                cell.setCellValue(productSaleHelper.getCategoryName());
                categoryId = productSaleHelper.getCategoryId();

                row = sheet.createRow(++indexRow);
                groupRow = indexRow;
                indexCell = 0;
                cell = row.createCell(indexCell);
                cell.setCellValue(productSaleHelper.getGroupName());
                cell.setCellStyle(productGroupStyle);
                groupId = productSaleHelper.getGroupId();

                row = sheet.createRow(++indexRow);
                parentRow = indexRow;
                indexCell = 0;
                cell = row.createCell(indexCell);
                cell.setCellValue(productSaleHelper.getParentName());
                cell.setCellStyle(boldStyle);
                parentId = productSaleHelper.getParentId();

                if(productSaleHelper.getProductName()==null ||
                        productSaleHelper.getProductName()==""){
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getQuantity());
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getLinePrice());
                } else {
                    row = sheet.createRow(++indexRow);
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getProductName());
                    cell.setCellStyle(alignStyle);

                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getQuantity());
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getLinePrice());
                }

            } else {
                if(productSaleHelper.getGroupId()!=groupId){
                    sheet.groupRow(groupRow+1,indexRow-1);
                    groupRow = indexRow;

                    if(parentRow+1<indexRow-1){
                        sheet.groupRow(parentRow+1,indexRow-1);
                    }

                    row = sheet.createRow(indexRow);
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getGroupName());
                    cell.setCellStyle(productGroupStyle);
                    groupId = productSaleHelper.getGroupId();

                    row = sheet.createRow(++indexRow);
                    parentRow = indexRow;
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getParentName());
                    cell.setCellStyle(boldStyle);
                    parentId = productSaleHelper.getParentId();

                    if(productSaleHelper.getProductName()==null ||
                            productSaleHelper.getProductName()==""){
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getLinePrice());
                    } else {
                        row = sheet.createRow(++indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());
                        cell.setCellStyle(alignStyle);

                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getLinePrice());
                    }
                } else {
                    if(productSaleHelper.getParentId()!=parentId){
                        row = sheet.createRow(indexRow);
                        sheet.groupRow(parentRow+1,indexRow-1);
                        parentRow = indexRow;
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getParentName());
                        cell.setCellStyle(boldStyle);
                        parentId = productSaleHelper.getParentId();

                        if(productSaleHelper.getProductName()==null
                                || productSaleHelper.getProductName() == ""){
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getQuantity());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getLinePrice());
                        } else {
                            row = sheet.createRow(++indexRow);
                            indexCell = 0;
                            cell = row.createCell(indexCell);
                            cell.setCellValue(productSaleHelper.getProductName());
                            cell.setCellStyle(alignStyle);

                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getQuantity());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getLinePrice());
                        }
                    } else {
                        row = sheet.createRow(indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());
                        cell.setCellStyle(alignStyle);

                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getLinePrice());
                    }
                }
            }
            indexRow++;
        }
        sheet.groupRow(categoryRow+1,indexRow-1);
        sheet.groupRow(groupRow+1,indexRow-1);
        if(parentRow+1<indexRow-1){
            sheet.groupRow(parentRow+1,indexRow-1);
        }
        sheet.setRowGroupCollapsed(categoryRow+1,true);
        sheet.autoSizeColumn(0);

    }

    private void createProductSaleReportForDateHeader(){

        XSSFCellStyle alignStyle = workbook.createCellStyle();
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        XSSFRow row = sheet.createRow(0);
        int indexCell = 0;
        XSSFCell cell = row.createCell(indexCell);
        cell.setCellValue("Category");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Quantity");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Total");
        cell.setCellStyle(alignStyle);
    }
}
