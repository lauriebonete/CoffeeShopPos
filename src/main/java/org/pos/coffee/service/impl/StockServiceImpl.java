package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.service.StockService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("stockService")
public class StockServiceImpl extends BaseCrudServiceImpl<Stock> implements StockService {
}
