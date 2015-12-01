package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.ListPrice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 12/1/2015.
 */
@Controller
@RequestMapping("/price")
public class ListPriceController extends BaseCrudController<ListPrice> {
}
