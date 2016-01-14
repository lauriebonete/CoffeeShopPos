package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Order;
import org.pos.coffee.bean.helper.ItemUsedHelper;

import java.util.List;

/**
 * Created by Laurie on 12/8/2015.
 */
public interface OrderService extends BaseCrudService<Order> {

    public List<ItemUsedHelper> countUseItems(List<Order> orderList);

}
