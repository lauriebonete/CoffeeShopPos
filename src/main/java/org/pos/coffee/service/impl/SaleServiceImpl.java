package org.pos.coffee.service.impl;

import org.apache.log4j.Logger;
import org.apache.commons.lang.time.DateFormatUtils;
import org.evey.dao.SequenceDao;
import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.bean.helper.ItemUsedHelper;
import org.pos.coffee.bean.helper.OrderExpenseHelper;
import org.pos.coffee.bean.helper.report.CategoryHelper;
import org.pos.coffee.bean.helper.report.ProductSaleHelper;
import org.pos.coffee.dao.SaleDao;
import org.pos.coffee.dao.SaleDaoJdbc;
import org.pos.coffee.service.AddOnService;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.OrderService;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 12/14/2015.
 */
@Service("saleService")
public class SaleServiceImpl extends BaseCrudServiceImpl<Sale> implements SaleService {

    private static Logger logger = Logger.getLogger(SaleServiceImpl.class);



    @Autowired
    private SaleDao saleDao;

    @Autowired
    private SaleDaoJdbc saleDaoJdbc;

    @Autowired
    private SequenceDao sequenceDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AddOnService addOnService;

    @Override
    @Transactional
    public Double countExpensePerOrder(List<OrderExpenseHelper> orderExpenseHelperList) {
        Double totalExpense = 0D;
        for(OrderExpenseHelper orderExpenseHelper: orderExpenseHelperList){
            totalExpense += orderExpenseHelper.getExpense();
            orderService.updateTotalExpense(orderExpenseHelper.getOrderId(),orderExpenseHelper.getExpense());
        }
        return totalExpense;
    }

    @Override
    @Transactional
    public void createSaleAndOrders(Sale sale) {
        sale.setSaleDate(new Date());
        this.save(sale);
        for(Order order: sale.getOrders()){
            order.setSale(sale);
            orderService.save(order);
            for(AddOn addOn: order.getAddOnList()){
                addOn.setOrder(order);
                addOnService.save(addOn);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Sale confirmSaleTransaction(Sale sale) throws Exception{

        this.createSaleAndOrders(sale);
        List<ItemUsedHelper> itemUsed = orderService.countUseItems(sale.getOrders());
        List<OrderExpenseHelper> orderExpenseHelperList = itemService.deductItemInventory(itemUsed);
        saleDao.updateTotalCost(sale.getId(),this.countExpensePerOrder(orderExpenseHelperList));
        return sale;
    }

    @Override
    @Transactional
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry) {
        final String datePrefix = DateFormatUtils.format(Calendar.getInstance().getTime(), "MMddyy");
        Long generatedCode = sequenceDao.incrementValue(key+datePrefix, increment, retryCount, maxRetry);
        return datePrefix+String.format("%04d",generatedCode);
    }

    @Override
    public Double getTotalSaleForDate(Date startDate, Date endDate) {
        return saleDaoJdbc.getTotalSaleForDate(startDate, endDate);
    }

    @Override
    public List<Double> getSalesPerCategory(Date startDate, Date endDate) {
        return saleDaoJdbc.getSalesPerCategory(startDate,endDate);
    }

    @Override
    public List<Map<String, Double>> getDisSurTax(Date startDate, Date endDate) {
        return saleDaoJdbc.getDisSurTax(startDate,endDate);
    }

    @Override
    public List<ProductSaleHelper> getProductSalePerDate(Date startDate, Date endDate) {
        return saleDaoJdbc.getProductSalePerDate(startDate,endDate);
    }

    @Override
    public List<Double> getSalePerMonth(Date startDate, Date endDate) {
        return saleDaoJdbc.getSalePerMonth(startDate,endDate);
    }

    @Override
    public List<CategoryHelper> getCategoryPercentage(Date startDate, Date endDate) {
        return saleDaoJdbc.getCategoryPercentage(startDate,endDate);
    }

    @Override
    public List<ProductSaleHelper> getProductExpensePerDate(Date startDate, Date endDate) {
        return saleDaoJdbc.getProductExpensePerDate(startDate,endDate);
    }
}
