package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Item;

import java.util.Map;

/**
 * Created by Laurie on 11/16/2015.
 */
public interface ItemService extends BaseCrudService<Item> {
    public void deductItemInventory(Map<Long,Double> itemUsed);
}
