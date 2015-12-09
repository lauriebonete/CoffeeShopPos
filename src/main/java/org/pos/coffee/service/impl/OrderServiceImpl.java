package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.OrderService;
import org.springframework.stereotype.Service;


/**
 * Created by Laurie on 12/8/2015.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseCrudServiceImpl<Order> implements OrderService {
}
