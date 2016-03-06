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
@RequestMapping("/about")
public class AboutController {

    @Autowired
    @Qualifier("appProperties")
    private Properties appProperties;

    @RequestMapping(value = "/get-properties", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,String> getAboutProperties(){
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("product", appProperties.get("product.version").toString());
        returnMap.put("build", appProperties.get("build.version").toString());
        returnMap.put("framework", appProperties.get("framework.version").toString());

        return returnMap;
    }

    @RequestMapping
    public String loadHomepage(){
        return "html/about.html";
    }

}
