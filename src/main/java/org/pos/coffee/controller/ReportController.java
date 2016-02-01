package org.pos.coffee.controller;

import org.apache.commons.lang.time.DateUtils;
import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.CategorySaleHelper;
import org.pos.coffee.bean.helper.report.MonthlySaleCategoryHelper;
import org.pos.coffee.bean.helper.report.MonthlySaleHelper;
import org.pos.coffee.bean.helper.report.SaleOrderHelper;
import org.pos.coffee.bean.poi.format.SalesReport;
import org.pos.coffee.service.ProductGroupService;
import org.pos.coffee.service.ProductService;
import org.pos.coffee.service.ReferenceLookUpService;
import org.pos.coffee.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private ProductService productService;

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @RequestMapping(value = "/export-sales", method = RequestMethod.GET)
    public void exportSales(HttpServletRequest request, HttpServletResponse response, @RequestParam String startDateString, @RequestParam String endDateString) throws Exception{

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");

        MonthlySaleHelper monthlySaleHelper = new MonthlySaleHelper();

        Date startDate = new SimpleDateFormat("MM-dd-yyyy").parse(startDateString);
        Date endDate = new SimpleDateFormat("MM-dd-yyyy").parse(endDateString);

        List<MonthlySaleCategoryHelper> monthlySaleCategoryHelperList = new ArrayList<>();
        Date startDateLooper = startDate;
        do {
            monthlySaleCategoryHelperList.add(reportService.createSaleReportSummary(startDateLooper, startDateLooper));
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
            new SalesReport(monthlySaleHelper).publishReport(out);
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
