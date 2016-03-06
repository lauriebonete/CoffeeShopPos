package org.pos.coffee.controller;

import org.evey.bean.User;
import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.bean.Person;
import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.service.FileDetailService;
import org.pos.coffee.service.PersonService;
import org.pos.coffee.service.ProductGroupService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
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

    @Autowired
    private UserService userService;

    @Autowired
    private FileDetailService fileDetailService;

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> saveProductImage(@RequestBody User user){
        Map<String, Object> returnMap = new HashMap<>();

        User saveToThis = userService.load(user.getId());
        Person person = saveToThis.getPerson();
        FileDetail personImage = fileDetailService.load(user.getPerson().getPersonImage().getId());
        saveToThis.getPerson().setPersonImage(personImage);
        saveToThis.setPerson(person);
        userService.save(saveToThis);

        returnMap.put("status", true);
        returnMap.put("message", "success");
        returnMap.put("result", saveToThis);

        return returnMap;
    }
}
