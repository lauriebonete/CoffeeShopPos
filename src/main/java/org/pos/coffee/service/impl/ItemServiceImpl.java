package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Laurie on 11/16/2015.
 */
@Service("itemService")
public class ItemServiceImpl extends BaseCrudServiceImpl<Item> implements ItemService {

    @Override
    public void deductItemInventory(Map<Long, Double> itemUsed) {

    }
}
