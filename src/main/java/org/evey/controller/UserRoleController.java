package org.evey.controller;

import org.evey.bean.UserRole;
import org.evey.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Laurie on 2/18/2016.
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController extends BaseCrudController<UserRole> {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping(value = "/get-all-authorities", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,String> getAllAuthorities(){
        return userRoleService.getAllDeclaredAuthorites();
    }
}
