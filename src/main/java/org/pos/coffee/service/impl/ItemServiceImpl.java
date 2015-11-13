package org.pos.coffee.service.impl;

import org.pos.coffee.bean.Item;
import org.pos.coffee.service.ItemService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/11/2015.
 */

@Service("itemService")
public class ItemServiceImpl extends BaseCrudServiceImpl<Item> implements ItemService {
}
