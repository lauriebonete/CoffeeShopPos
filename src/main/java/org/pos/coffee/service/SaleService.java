package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Sale;

/**
 * Created by Laurie on 12/14/2015.
 */
public interface SaleService extends BaseCrudService<Sale> {
    public void countExpensePerOrder(Sale sale);
}
