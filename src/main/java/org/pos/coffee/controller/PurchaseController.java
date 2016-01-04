package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Purchase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 1/4/2016.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseCrudController<Purchase> {
}
