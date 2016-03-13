package org.evey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Laurie on 3/6/2016.
 */
@Controller
@RequestMapping("/userprofile")
public class UserProfileController {

    @RequestMapping
    public String loadHomepage(){
        return "html/userprofile.html";
    }

}
