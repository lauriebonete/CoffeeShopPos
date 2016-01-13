package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.helper.ItemUsedHelper;
import org.pos.coffee.bean.helper.OrderExpenseHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/16/2015.
 */
public interface ItemService extends BaseCrudService<Item> {
    public List<OrderExpenseHelper> deductItemInventory(List<ItemUsedHelper> itemUsedHelperList) throws Exception;
}
