package org.pos.coffee.bean.poi.format;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
        Map<String,Integer> returnMap = createSaleReportSummary(monthlySaleHelper.getMonthlySaleCategoryHelperList());
        createSaleReportSummaryTotal(monthlySaleHelper.getMonthlySaleCategoryHelperList(),returnMap);

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
        Double categoryQuantity = 0D;
        Double categoryTotal= 0D;
        Double groupQuantity = 0D;
        Double groupTotal= 0D;
        Double parentQuantity = 0D;
        Double parentTotal= 0D;

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
                createCategoryTotal(categoryRow, categoryQuantity, categoryTotal);
                createGroupTotal(groupRow,groupQuantity,groupTotal);
                createParentTotal(parentRow,parentQuantity,parentTotal);
                categoryQuantity = 0D;
                categoryTotal = 0D;
                groupQuantity = 0D;
                groupTotal = 0D;
                parentQuantity = 0D;
                parentTotal = 0D;


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
                    createCell(cell,row,indexCell,productSaleHelper);
                    categoryQuantity+=productSaleHelper.getQuantity();
                    categoryTotal+=productSaleHelper.getLinePrice();

                    groupQuantity+=productSaleHelper.getQuantity();
                    groupTotal+=productSaleHelper.getLinePrice();

                    parentQuantity+=productSaleHelper.getQuantity();
                    parentTotal+=productSaleHelper.getLinePrice();
                } else {
                    row = sheet.createRow(++indexRow);
                    indexCell = 0;
                    cell = row.createCell(indexCell);
                    cell.setCellValue(productSaleHelper.getProductName());
                    cell.setCellStyle(alignStyle);

                    createCell(cell,row,indexCell,productSaleHelper);
                    categoryQuantity+=productSaleHelper.getQuantity();
                    categoryTotal+=productSaleHelper.getLinePrice();

                    groupQuantity+=productSaleHelper.getQuantity();
                    groupTotal+=productSaleHelper.getLinePrice();

                    parentQuantity+=productSaleHelper.getQuantity();
                    parentTotal+=productSaleHelper.getLinePrice();
                }

            } else {
                if(productSaleHelper.getGroupId()!=groupId){
                    createGroupTotal(groupRow,groupQuantity,groupTotal);
                    groupQuantity = 0D;
                    groupTotal = 0D;

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
                        createCell(cell,row,indexCell,productSaleHelper);
                        categoryQuantity+=productSaleHelper.getQuantity();
                        categoryTotal+=productSaleHelper.getLinePrice();

                        groupQuantity+=productSaleHelper.getQuantity();
                        groupTotal+=productSaleHelper.getLinePrice();

                        parentQuantity+=productSaleHelper.getQuantity();
                        parentTotal+=productSaleHelper.getLinePrice();
                    } else {
                        row = sheet.createRow(++indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());
                        cell.setCellStyle(alignStyle);

                        createCell(cell,row,indexCell,productSaleHelper);
                        categoryQuantity+=productSaleHelper.getQuantity();
                        categoryTotal+=productSaleHelper.getLinePrice();

                        groupQuantity+=productSaleHelper.getQuantity();
                        groupTotal+=productSaleHelper.getLinePrice();

                        parentQuantity+=productSaleHelper.getQuantity();
                        parentTotal+=productSaleHelper.getLinePrice();
                    }
                } else {
                    if(productSaleHelper.getParentId()!=parentId){
                        createParentTotal(parentRow,parentQuantity,parentTotal);
                        parentQuantity = 0D;
                        parentTotal = 0D;

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
                            createCell(cell,row,indexCell,productSaleHelper);
                            categoryQuantity+=productSaleHelper.getQuantity();
                            categoryTotal+=productSaleHelper.getLinePrice();

                            groupQuantity+=productSaleHelper.getQuantity();
                            groupTotal+=productSaleHelper.getLinePrice();

                            parentQuantity+=productSaleHelper.getQuantity();
                            parentTotal+=productSaleHelper.getLinePrice();
                        } else {
                            row = sheet.createRow(++indexRow);
                            indexCell = 0;
                            cell = row.createCell(indexCell);
                            cell.setCellValue(productSaleHelper.getProductName());
                            cell.setCellStyle(alignStyle);

                            createCell(cell,row,indexCell,productSaleHelper);
                            categoryQuantity+=productSaleHelper.getQuantity();
                            categoryTotal+=productSaleHelper.getLinePrice();

                            groupQuantity+=productSaleHelper.getQuantity();
                            groupTotal+=productSaleHelper.getLinePrice();

                            parentQuantity+=productSaleHelper.getQuantity();
                            parentTotal+=productSaleHelper.getLinePrice();
                        }
                    } else {
                        row = sheet.createRow(indexRow);
                        indexCell = 0;
                        cell = row.createCell(indexCell);
                        cell.setCellValue(productSaleHelper.getProductName());
                        cell.setCellStyle(alignStyle);

                        createCell(cell,row,indexCell,productSaleHelper);
                        categoryQuantity+=productSaleHelper.getQuantity();
                        categoryTotal+=productSaleHelper.getLinePrice();

                        groupQuantity+=productSaleHelper.getQuantity();
                        groupTotal+=productSaleHelper.getLinePrice();

                        parentQuantity+=productSaleHelper.getQuantity();
                        parentTotal+=productSaleHelper.getLinePrice();
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

        createCategoryTotal(categoryRow, categoryQuantity, categoryTotal);
        createGroupTotal(groupRow,groupQuantity,groupTotal);
        createParentTotal(parentRow,parentQuantity,parentTotal);
        categoryQuantity = 0D;
        categoryTotal = 0D;
        parentQuantity = 0D;
        parentTotal = 0D;
        groupQuantity = 0D;
        groupTotal = 0D;

        sheet.setRowGroupCollapsed(categoryRow+1,true);
        sheet.autoSizeColumn(0);

    }

    private void createCategoryTotal(int categoryRow, Double categoryQuantity, Double categoryTotal){
        XSSFCell categoryCellQuantity = sheet.getRow(categoryRow).createCell(1);
        categoryCellQuantity.setCellValue(categoryQuantity);
        XSSFCell categoryCellTotal = sheet.getRow(categoryRow).createCell(3);
        categoryCellTotal.setCellValue(categoryTotal);
    }

    private void createParentTotal(int parentRow, Double parentQuantity, Double parentTotal){
        XSSFCell parentCellQuantity = sheet.getRow(parentRow).createCell(1);
        parentCellQuantity.setCellValue(parentQuantity);
        XSSFCell parentCellTotal = sheet.getRow(parentRow).createCell(3);
        parentCellTotal.setCellValue(parentTotal);
    }

    private void createGroupTotal(int groupRow, Double groupQuantity, Double groupTotal){
        XSSFCell groupCellQuantity = sheet.getRow(groupRow).createCell(1);
        groupCellQuantity.setCellValue(groupQuantity);
        XSSFCell groupCellTotal = sheet.getRow(groupRow).createCell(3);
        groupCellTotal.setCellValue(groupTotal);
    }

    private void createCell(XSSFCell cell, XSSFRow row, int indexCell, ProductSaleHelper productSaleHelper){
        cell = row.createCell(++indexCell);
        cell.setCellValue(productSaleHelper.getQuantity());
        cell = row.createCell(++indexCell);
        cell.setCellValue(productSaleHelper.getPrice());
        cell = row.createCell(++indexCell);
        cell.setCellValue(productSaleHelper.getLinePrice());
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
        cell.setCellValue("Price");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Total");
        cell.setCellStyle(alignStyle);
    }


    private void createSaleReportSummaryHeader(List<String> categoryHeaders){

        XSSFCellStyle alignStyle = workbook.createCellStyle();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        alignStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        alignStyle.setFont(boldFont);

        XSSFRow row = sheet.createRow(0);
        int indexCell = 0;
        XSSFCell cell = row.createCell(indexCell);

        cell.setCellValue("Date");
        cell.setCellStyle(alignStyle);
        for(String category: categoryHeaders){
            indexCell++;
            cell = row.createCell(indexCell);
            cell.setCellValue(category);
            cell.setCellStyle(alignStyle);
        }

        cell = row.createCell(++indexCell);
        cell.setCellValue("Discount");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Surcharge");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Tax");
        cell.setCellStyle(alignStyle);

        cell = row.createCell(++indexCell);
        cell.setCellValue("Total Sales");
        cell.setCellStyle(alignStyle);

        for(String category: categoryHeaders){
            indexCell++;
            cell = row.createCell(indexCell);
            cell.setCellValue(category);
            cell.setCellStyle(alignStyle);
        }
    }

    private Map<String,Integer> createSaleReportSummary(List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList){
        int indexRow = 1;
        int maxIndexCell=0;

        XSSFCellStyle percentageStyle = workbook.createCellStyle();
        percentageStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0#"));

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
                cell.setCellStyle(percentageStyle);
            }

            indexRow++;
            maxIndexCell = indexCell;
        }

        for(int i=0;i<=maxIndexCell;i++){
            sheet.autoSizeColumn(i);
        }

        Integer categorySize = monthlySaleCategoryHelperList.get(0).getCategorySale().size();

        Map<String,Integer> returnMap = new HashMap<>();
        returnMap.put("indexRow", indexRow);
        returnMap.put("maxIndexCell", maxIndexCell-categorySize);
        returnMap.put("categorySize",categorySize);
        return returnMap;

    }

    private void createSaleReportSummaryTotal(List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList, Map<String,Integer> indexes){
        XSSFCellStyle borderStyle = workbook.createCellStyle();
        XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);
        borderStyle.setFont(boldFont);
        borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        borderStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        borderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        borderStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM_DASHED);
        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        XSSFCellStyle borderPercentageStyle = workbook.createCellStyle();
        XSSFFont boldPercentageFont = workbook.createFont();
        boldPercentageFont.setBold(true);
        borderPercentageStyle.setFont(boldPercentageFont);
        borderPercentageStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        borderPercentageStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        borderPercentageStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        borderPercentageStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM_DASHED);
        borderPercentageStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        borderPercentageStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        borderPercentageStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        borderPercentageStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        borderPercentageStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0#"));


        int indexCell = 0;
        XSSFRow row = sheet.createRow(indexes.get("indexRow"));
        XSSFCell cell = row.createCell(indexCell);
        cell.setCellValue("TOTAL");
        cell.setCellStyle(borderStyle);

        indexCell++;
        for(int i = indexCell;i<=indexes.get("maxIndexCell");i++, indexCell++){
            StringBuilder formulaBuilder = new StringBuilder();
            formulaBuilder.append("SUM(")
                    .append(CellReference.convertNumToColString(i))
                    .append("2:"+CellReference.convertNumToColString(i))
                    .append(indexes.get("indexRow")+")");

            cell = row.createCell(i);
            cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula(formulaBuilder.toString());
            cell.setCellStyle(borderStyle);
        }

        for(int i = 1; i<=indexes.get("categorySize"); i++,indexCell++){
            StringBuilder formulaBuilder = new StringBuilder();
            formulaBuilder.append("(")
                    .append(CellReference.convertNumToColString(i))
                    .append(indexes.get("indexRow")+1+"/"+CellReference.convertNumToColString(indexes.get("maxIndexCell")))
                    .append(indexes.get("indexRow")+1+")*100");

            cell = row.createCell(indexCell);
            cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula(formulaBuilder.toString());
            cell.setCellStyle(borderPercentageStyle);
        }
    }

}
