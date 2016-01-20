package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Branch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 1/20/2016.
 */
@Controller
@RequestMapping("/branch")
public class BranchController extends BaseCrudController<Branch> {
}
