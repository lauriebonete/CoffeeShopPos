package org.pos.coffee.controller;

import org.pos.coffee.bean.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 11/16/2015.
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseCrudController<Item> {
}
