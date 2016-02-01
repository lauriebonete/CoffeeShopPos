package org.pos.coffee.controller;

import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.bean.helper.report.CategorySaleHelper;
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
    public void exportSales(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");

        List<ProductGroup> productGroups = productGroupService.findAll();
        List<ReferenceLookUp> productCategory = referenceLookUpService.getReferenceLookUpByCategory("CATEGORY_PROD_CATEGORY");
        List<Product> products = productService.findAll();

        List<SaleOrderHelper> soldProductList = reportService.prepareSalesData(new Date(), new Date());
        List<CategorySaleHelper> categorySaleHelperList = reportService.buildReportData(products,productCategory,productGroups);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            new SalesReport(categorySaleHelperList, soldProductList).publishReport(out);
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