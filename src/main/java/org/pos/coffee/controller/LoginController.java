package org.pos.coffee.controller;

import org.pos.coffee.bean.Branch;
import org.pos.coffee.bean.User;
import org.pos.coffee.service.BranchService;
import org.pos.coffee.service.LoginService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenji on 12/4/2015.
 */
@Controller
@RequestMapping("/login")
public class LoginController implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private BranchService branchService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return loginService.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return loginService.supports(aClass);
    }

    @RequestMapping
    public ModelAndView loadHtml() {
        return new ModelAndView("html/login.html");
    }

    @RequestMapping(value = "/get-logged", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> getLoggedUser() throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        User user = userService.getCurrentUser();
        if(user!=null
                && user.getPerson()!=null){
            user.getPerson().getFirstName();
        }
        returnMap.put("user",user);
        returnMap.put("branch",branchService.getBranchUsingMac(loginService.getMacAddress()));


        return returnMap;
    }

    @RequestMapping(value="/get-mac-address", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getMacAddress() {
        return loginService.getMacAddress();
    }

}



