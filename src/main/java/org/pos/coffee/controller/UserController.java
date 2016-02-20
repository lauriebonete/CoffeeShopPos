package org.pos.coffee.controller;

import org.evey.bean.User;
import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.service.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 12/16/2015.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseCrudController<User> {

}
