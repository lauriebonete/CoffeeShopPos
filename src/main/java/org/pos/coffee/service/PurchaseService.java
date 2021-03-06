package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.bean.helper.report.PurchaseReportHelper;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
public interface PurchaseService extends BaseCrudService<Purchase> {
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry);
    public Purchase savePurchaseAndPO(Purchase purchase);
    public Purchase receivedPurchaseOrder(Purchase purchase) throws Exception;
    public Purchase createPurchase(Purchase purchase);
    public List<PurchaseReportHelper> getPurchaseUsingDateRange(Date startDate, Date endDate);

}
