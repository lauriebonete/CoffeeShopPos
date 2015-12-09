package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.OrderHelper;
import org.pos.coffee.bean.PriceSet;
import org.pos.coffee.bean.Sale;

import java.util.List;

/**
 * Created by Laurie on 12/7/2015.
 */
public interface PriceSetService extends BaseCrudService<PriceSet> {
    public List<PriceSet> getPossiblePriceSets(OrderHelper orderHelper) throws Exception;
    public List<PriceSet> getApplicablePriceSets(List<PriceSet> priceSetList, OrderHelper orderHelper);
    public List<PriceSet> getPossibleSalePriceSets() throws Exception;
    public Sale applyPriceSets(List<PriceSet> priceSetList, Double total, Double totalDiscount, Double totalSurcharge);
}
