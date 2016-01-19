package org.pos.coffee.controller;

import org.pos.coffee.bean.User;
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
    public @ResponseBody User getLoggedUser(){
        User user = userService.getCurrentUser();
        if(user!=null
                && user.getPerson()!=null){
            user.getPerson().getFirstName();
        }

        return user;
    }

}



