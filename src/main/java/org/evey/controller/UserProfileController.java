package org.evey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Laurie on 3/6/2016.
 */
@Controller
@RequestMapping("/user-profile")
public class UserProfileController {

    @RequestMapping
    public ModelAndView loadHomepage(){
        return new ModelAndView("html/user-profile.html");
    }

}
