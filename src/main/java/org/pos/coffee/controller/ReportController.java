package org.pos.coffee.controller;

import org.apache.commons.lang.time.DateUtils;
import org.evey.service.ReferenceLookUpService;
import org.evey.utility.DateUtil;
import org.evey.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.bean.helper.report.*;
import org.pos.coffee.bean.poi.format.*;
import org.pos.coffee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/21/2016.
 */

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/export-sales", method = RequestMethod.GET)
    public void exportSales(HttpServletRequest request, HttpServletResponse response, @RequestParam String startDateString, @RequestParam String endDateString) throws Exception{

        MonthlySaleHelper monthlySaleHelper = new MonthlySaleHelper();

        Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(startDateString);
        Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(endDateString);

        String fileName = "Sale_Report_for_"+startDateString+"_to_"+endDateString;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");

        List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList = new ArrayList<>();
        List<ProductSaleHelperHolder> productSaleHelperHolderList = new ArrayList<>();
        Date startDateLooper = startDate;
        do {
            monthlySaleCategoryHelperList.add(reportService.createSaleReportSummary(startDateLooper, startDateLooper));

            ProductSaleHelperHolder productSaleHelperHolder = new ProductSaleHelperHolder();
            productSaleHelperHolder.setDate(startDateLooper);
            productSaleHelperHolder.setProductSaleHelperList(saleService.getProductSalePerDate(startDateLooper,startDateLooper));
            productSaleHelperHolderList.add(productSaleHelperHolder);

            startDateLooper = DateUtils.addDays(startDateLooper, 1);
        } while(startDateLooper.before(DateUtils.addDays(endDate, 1)));

        List<ReferenceLookUp> referenceLookUpList = referenceLookUpService.getReferenceLookUpByCategory("CATEGORY_PROD_CATEGORY");
        List<String> headers = new ArrayList<>();
        for(ReferenceLookUp referenceLookUp: referenceLookUpList){
            headers.add(referenceLookUp.getValue());
        }

        monthlySaleHelper.setMonthlySaleCategoryHelperList(monthlySaleCategoryHelperList);
        monthlySaleHelper.setHeaders(headers);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new SalesReport(monthlySaleHelper, productSaleHelperHolderList).publishReport(out);
            out.flush();
        } finally {
            if(out != null)
                out.close();
        }

    }

    @RequestMapping(value = "/create-consumption", method = RequestMethod.GET)
    public void createConsumptionExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam String startDateString, @RequestParam String endDateString) throws Exception{
        Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(startDateString);
        Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(endDateString);

        String fileName = "Consumption_Report_for_"+startDateString+"_to_"+endDateString;
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");

        List<ConsumptionHelper> consumptionHelperList = itemService.oountConsumedItem(startDate,endDate);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new ConsumptionReport(consumptionHelperList).publishReport(out);
            out.flush();
        } finally {
            if(out != null)
                out.close();
        }
    }

    @RequestMapping(value = "/download-inventory", method = RequestMethod.GET)
    public void createInventoryExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String fileName = "Inventory_Report_"+ DateUtil.dateToString(new Date(), "MM-dd-yyyy");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");

        List<StockHelper> stockList = stockService.getStockCount("jpql.inventory.display-inventory-count");


        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new InventoryReport(stockList).publishReport(out);
            out.flush();
        } finally {
            if(out != null)
                out.close();
        }
    }

    @RequestMapping(value = "/create-purchase", method = RequestMethod.GET)
    public  void createPurchaseReport(HttpServletRequest request, HttpServletResponse response, @RequestParam String startDateString, @RequestParam String endDateString) throws Exception{
        String fileName = "Purchase_Report_"+ DateUtil.dateToString(new Date(), "MM-dd-yyyy");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");

        Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(startDateString);
        Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(endDateString);

        List<PurchaseReportHelper> purchaseReportHelperList = purchaseService.getPurchaseUsingDateRange(startDate,endDate);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new PurchaseReport(purchaseReportHelperList).publishReport(out);
            out.flush();
        } finally {
            if(out != null)
                out.close();
        }
    }

    @RequestMapping(value = "/create-expense", method = RequestMethod.GET)
    public void createExpenseReport(HttpServletRequest request, HttpServletResponse response, @RequestParam String startDateString, @RequestParam String endDateString) throws Exception{
        String fileName = "Expense_Report_"+ DateUtil.dateToString(new Date(), "MM-dd-yyyy");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xlsx");

        Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(startDateString);
        Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(endDateString);

        List<ProductSaleHelperHolder> productSaleHelperHolderList = new ArrayList<>();
        Date startDateLooper = startDate;
        do {
            ProductSaleHelperHolder productSaleHelperHolder = new ProductSaleHelperHolder();
            productSaleHelperHolder.setDate(startDateLooper);
            productSaleHelperHolder.setProductSaleHelperList(saleService.getProductExpensePerDate(startDateLooper, startDateLooper));
            productSaleHelperHolderList.add(productSaleHelperHolder);

            startDateLooper = DateUtils.addDays(startDateLooper, 1);
        } while(startDateLooper.before(DateUtils.addDays(endDate, 1)));

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new ExpenseReport(productSaleHelperHolderList).publishReport(out);
            out.flush();
        } finally {
            if(out != null)
                out.close();
        }
    }

    @RequestMapping
    public ModelAndView loadHtml() {
        return new ModelAndView("html/report.html");
    }

}
