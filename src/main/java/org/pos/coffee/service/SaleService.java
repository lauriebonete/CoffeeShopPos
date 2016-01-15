package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.bean.helper.OrderExpenseHelper;

import java.util.List;

/**
 * Created by Laurie on 12/14/2015.
 */
public interface SaleService extends BaseCrudService<Sale> {
    public Double countExpensePerOrder(List<OrderExpenseHelper> orderExpenseHelperList);
    public void createSaleAndOrders(Sale sale);
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry);
    public Sale confirmSaleTransaction(Sale sale) throws Exception;
}
