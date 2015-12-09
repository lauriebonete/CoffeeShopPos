package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.PriceSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 12/7/2015.
 */
@Controller
@RequestMapping("/priceSet")
public class PriceSetController extends BaseCrudController<PriceSet> {
}
