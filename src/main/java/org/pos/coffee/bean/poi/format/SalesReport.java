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
import java.util.*;

/**
 * Created by Laurie on 1/21/2016.
 */
public class SalesReport extends Report {


    private MonthlySaleHelper monthlySaleHelper;
    private List<ProductSaleHelperHolder> productSaleHelperHolderList;

    @Autowired
    private SaleService saleService;

    @Override
    protected void publishHeader() {

    }

    public SalesReport(MonthlySaleHelper monthlySaleHelper, List<ProductSaleHelperHolder> productSaleHelperHolderList){
        this.monthlySaleHelper = monthlySaleHelper;
        this.productSaleHelperHolderList = productSaleHelperHolderList;
    }

    @Override
    protected void publishData() {
        publishData(this.monthlySaleHelper, this.productSaleHelperHolderList);
    }

    protected void publishData(MonthlySaleHelper monthlySaleHelper, List<ProductSaleHelperHolder> productSaleHelperHolderList){
        this.sheet = workbook.createSheet("Monthly Sale");
        createSaleReportSummaryHeader(monthlySaleHelper.getHeaders());
        createSaleReportSummary(monthlySaleHelper.getMonthlySaleCategoryHelperList());

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
                groupId = productSaleHelper.getGroupId();

                row = sheet.createRow(++indexRow);
                parentRow = indexRow;
                indexCell = 0;
                cell = row.createCell(indexCell);
                cell.setCellValue(productSaleHelper.getParentName());
                parentId = productSaleHelper.getParentId();

                if(productSaleHelper.getProductName()==null ||
                        productSaleHelper.getProductName()==""){
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getQuantity());
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getPrice());
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getLinePrice());
                } else {
                    row = sheet.createRow(++indexRow);
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getProductName());

                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getQuantity());
                    cell = row.createCell(++indexCell);
                    cell.setCellValue(productSaleHelper.getPrice());
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
                    groupId = productSaleHelper.getGroupId();

                    row = sheet.createRow(++indexRow);
                    parentRow = indexRow;
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getParentName());
                    parentId = productSaleHelper.getParentId();

                    if(productSaleHelper.getProductName()==null ||
                            productSaleHelper.getProductName()==""){
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getPrice());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getLinePrice());
                    } else {
                        row = sheet.createRow(++indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());

                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getPrice());
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
                        parentId = productSaleHelper.getParentId();

                        if(productSaleHelper.getProductName()==null
                                || productSaleHelper.getProductName() == ""){
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getQuantity());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getPrice());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getLinePrice());
                        } else {
                            row = sheet.createRow(++indexRow);
                            indexCell = 0;
                            cell = row.createCell(indexCell);
                            cell.setCellValue(productSaleHelper.getProductName());

                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getQuantity());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getPrice());
                            cell = row.createCell(++indexCell);
                            cell.setCellValue(productSaleHelper.getLinePrice());
                        }
                    } else {
                        row = sheet.createRow(indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());

                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getQuantity());
                        cell = row.createCell(++indexCell);
                        cell.setCellValue(productSaleHelper.getPrice());
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

    }

    private void createProductSaleReportForDateHeader(){
        XSSFRow row = sheet.createRow(0);
        int indexCell = 0;
        XSSFCell cell = row.createCell(indexCell);
        cell.setCellValue("Category");
        cell = row.createCell(++indexCell);
        cell.setCellValue("Quantity");
        cell = row.createCell(++indexCell);
        cell.setCellValue("Price");
        cell = row.createCell(++indexCell);
        cell.setCellValue("Total");
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
