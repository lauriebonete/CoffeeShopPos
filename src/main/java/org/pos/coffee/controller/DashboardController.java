package org.pos.coffee.controller;

import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.service.ProductService;
import org.pos.coffee.service.PurchaseOrderService;
import org.pos.coffee.service.SaleService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Laurie on 11/5/2015.
 */

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/sale-per-week", method = RequestMethod.GET, produces = "application/json")
      public @ResponseBody Map<String,Object> getSalePerWeek() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Calendar cal = Calendar.getInstance();

        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        Date startDate = dateFormat.parse(dateFormat.format(first.getTime()));
        Date endDate = dateFormat.parse(dateFormat.format(last.getTime()));

        Map salesPerWeekMap = saleService.getSalePerWeek(startDate, endDate);
        List<String> week = new ArrayList<>();
        List<String> sales = new ArrayList<>();

        Iterator entries = salesPerWeekMap.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            Date daySale = dateFormat.parse(dateTodayString.substring(0,2) + "/" + (String) thisEntry.getKey() + "/" + dateTodayString.substring(6));
            week.add(new SimpleDateFormat("EE").format(daySale));
            sales.add(thisEntry.getValue().toString());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("week",week);
        returnMap.put("sales",sales);

        return returnMap;

    }

    @RequestMapping(value = "/sale-per-day", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getSalePerDay() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Date startDate = dateFormat.parse(dateFormat.format(new Date().getTime()));
        Date endDate = dateFormat.parse(dateFormat.format(new Date().getTime()));

        List<Double> saleList = saleService.getSalePerDay(startDate, endDate);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("sale",saleList);

        return returnMap;

    }

    @RequestMapping(value = "/sale-count-per-day", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getSaleCountPerDay() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Date startDate = dateFormat.parse(dateFormat.format(new Date().getTime()));

        List<Double> saleList = saleService.getSaleCountPerDay(startDate, startDate);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("salesCount",saleList);

        return returnMap;

    }

    @RequestMapping(value = "/sale-per-month", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getSalePerMonth() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Date startDate = dateFormat.parse("01/01/"+dateTodayString.substring(6));
        Date endDate = dateFormat.parse("12/31/"+dateTodayString.substring(6));

        Map salesPerMonthMap = saleService.getSalePerMonth(startDate, endDate);
        List<String> month = new ArrayList<>();
        List<String> sales = new ArrayList<>();

        Iterator entries = salesPerMonthMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            month.add(new DateFormatSymbols().getMonths()[Integer.parseInt((String) thisEntry.getKey())-1]);
            sales.add(thisEntry.getValue().toString());
        }

        Collections.reverse(month);
        Collections.reverse(sales);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("month",month);
        returnMap.put("sales",sales);

        return returnMap;

    }

    @RequestMapping(value = "/category-percentage-week", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getCategoryPercentagePerWeek() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Calendar cal = Calendar.getInstance();

        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        Date startDate = dateFormat.parse(dateFormat.format(first.getTime()));
        Date endDate = dateFormat.parse(dateFormat.format(last.getTime()));

        List<CategoryHelper> categoryHelperList = saleService.getCategoryPercentage(startDate, endDate);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("category",categoryHelperList);

        return returnMap;
    }

    @RequestMapping(value = "/category-percentage-month", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getCategoryPercentagePerMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        Date lastDayOfMonth = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        Date firstDayOfMonth = cal.getTime();

        List<CategoryHelper> categoryHelperList = saleService.getCategoryPercentage(firstDayOfMonth,lastDayOfMonth);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("category",categoryHelperList);

        return returnMap;
    }

    @RequestMapping(value = "/category-percentage-today", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getCategoryPercentageToday() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Date today = dateFormat.parse(dateFormat.format(new Date().getTime()));

        List<CategoryHelper> categoryHelperList = saleService.getCategoryPercentage(today,today);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("category",categoryHelperList);

        return returnMap;
    }

    @RequestMapping(value = "/inventory-snapshot-critical", method = RequestMethod.GET, produces = "application/json")
     public @ResponseBody Map<String,Object> getInventorySnapShotCritical(){
        StockHelper stockHelper = new StockHelper();
        stockHelper.setStatus("critical");
        List<StockHelper> stockHelperList = new ArrayList<>();

        stockHelperList.addAll(stockService.findStockEntity(stockHelper));

        List<Double> returnList = new ArrayList<>();
        int max = stockHelperList.size();

        List<String> label = new ArrayList<>();

        for(int i=0; i<max; i++){
            StockHelper stockHelperFound = stockHelperList.get(i);
            label.add(stockHelperFound.getItem().getItemName());
            returnList.add(stockHelperList.get(i).getQuantity());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("label",label);
        returnMap.put("results",returnList);

        return returnMap;
    }

    @RequestMapping(value = "/inventory-snapshot-low", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getInventorySnapShotLow(){
        StockHelper stockHelper = new StockHelper();
        stockHelper.setStatus("low");
        List<StockHelper> stockHelperList = new ArrayList<>();

        stockHelperList.addAll(stockService.findStockEntity(stockHelper));

        List<Double> returnList = new ArrayList<>();
        int max = stockHelperList.size();

        List<String> label = new ArrayList<>();

        for(int i=0; i<max; i++){
            StockHelper stockHelperFound = stockHelperList.get(i);
            label.add(stockHelperFound.getItem().getItemName());
            returnList.add(stockHelperList.get(i).getQuantity());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("label",label);
        returnMap.put("results",returnList);

        return returnMap;
    }

    @RequestMapping(value = "/pending-purchase", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getPendingPurchases() throws Exception{

        Map pendingPurchasesMap = purchaseOrderService.getPendingPurchases();

        Iterator entries = pendingPurchasesMap.entrySet().iterator();
        List<String> purchaseCode = new ArrayList<>();
        List<String> purchaseStatus = new ArrayList<>();

        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            purchaseCode.add(thisEntry.getKey().toString());
            purchaseStatus.add(thisEntry.getValue().toString());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("purchaseCode",purchaseCode);
        returnMap.put("purchaseStatus",purchaseStatus);

        return returnMap;

    }

    @RequestMapping(value = "/sale-products-day", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getAllSalesByProductTodayk() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Date startDate = dateFormat.parse(dateFormat.format(new Date().getTime()));;

        Map salesOfProductsByWeek = saleService.getAllSalesByProductPerDate(startDate, startDate);

        Iterator entries = salesOfProductsByWeek.entrySet().iterator();
        List<String> productName = new ArrayList<>();
        List<String> productSales = new ArrayList<>();
        Product found = null;
        Product lookFor = new Product();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            lookFor = new Product();
            lookFor.setId(Long.valueOf(thisEntry.getKey().toString()));

            List<Product> results = new ArrayList<>();
            try {
                results= productService.findEntity(lookFor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            found = null;
            if(!results.isEmpty()){
                found = results.get(0);
            }

            productName.add(found.getProductName());
            productSales.add(thisEntry.getValue().toString());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("productName", productName);
        returnMap.put("productSales", productSales);

        return returnMap;

    }

    @RequestMapping(value = "/sale-products-week", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getAllSalesByProductPerWeek() throws Exception{

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTodayString = dateFormat.format(new Date()).toString();

        Calendar cal = Calendar.getInstance();

        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));

        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        Date startDate = dateFormat.parse(dateFormat.format(first.getTime()));
        Date endDate = dateFormat.parse(dateFormat.format(last.getTime()));

        Map salesOfProductsByWeek = saleService.getAllSalesByProductPerDate(startDate, endDate);

        Iterator entries = salesOfProductsByWeek.entrySet().iterator();
        List<String> productName = new ArrayList<>();
        List<String> productSales = new ArrayList<>();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();

            productName.add(thisEntry.getKey().toString());
            productSales.add(thisEntry.getValue().toString());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("productName", productName);
        returnMap.put("productSales", productSales);

        return returnMap;

    }

    @RequestMapping(value = "/sale-products-month", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getAllSalesByProductPerMonth() throws Exception{

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        Date lastDayOfMonth = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        Date firstDayOfMonth = cal.getTime();

        Map salesOfProductsByWeek = saleService.getAllSalesByProductPerDate(firstDayOfMonth, lastDayOfMonth);

        Iterator entries = salesOfProductsByWeek.entrySet().iterator();
        List<String> productName = new ArrayList<>();
        List<String> productSales = new ArrayList<>();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();

            productName.add(thisEntry.getKey().toString());
            productSales.add(thisEntry.getValue().toString());
        }

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("productName", productName);
        returnMap.put("productSales", productSales);

        return returnMap;

    }

    @RequestMapping
    public String loadHomepage(){
        return "html/dashboard.html";
    }
}
