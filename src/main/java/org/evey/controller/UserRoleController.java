package org.evey.controller;

import org.evey.bean.Authority;
import org.evey.bean.UserRole;
import org.evey.service.AuthorityService;
import org.evey.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Laurie on 2/18/2016.
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController extends BaseCrudController<UserRole> {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "/get-all-authorities", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,String> getAllAuthorities(){
        return userRoleService.getAllDeclaredAuthorites();
    }

    @RequestMapping(value = "/save-access", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> saveAccessCodes(@RequestBody UserRole userRole){

        Map<String,Object> params = new HashMap<>();
        params.put("userRoleId",userRole.getId());
        authorityService.executeUpdateByNamedQuery("jpql.authority.delete-old-access", params);

        Set authorities = new HashSet();
        for(Authority authority: userRole.getAuthorityList()){
            authority.setUserRole(userRole);
            authorityService.save(authority);
            authorities.add(authority);
        }

        userRole.setAuthorities(authorities);
        userRoleService.save(userRole);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "success");
        map.put("status", true);
        map.put("result", userRole);

        return map;
    }
}
