package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 1/4/2016.
 */
@Controller
@RequestMapping("/stock")
public class StockController extends BaseCrudController<Stock> {
}
