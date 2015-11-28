package org.pos.coffee.controller;

import org.pos.coffee.bean.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 11/14/2015.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseCrudController<Order> {
}
