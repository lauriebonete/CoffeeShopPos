package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 12/8/2015.
 */
public interface OrderService extends BaseCrudService<Order> {

    public Map<Long, Double> countUseItems(List<Order> orderList);

}
