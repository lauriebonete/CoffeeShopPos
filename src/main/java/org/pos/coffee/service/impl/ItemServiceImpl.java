package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.service.ItemService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/16/2015.
 */
@Service("itemService")
public class ItemServiceImpl extends BaseCrudServiceImpl<Item> implements ItemService {
}
