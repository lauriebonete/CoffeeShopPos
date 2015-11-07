package org.pos.coffee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 11/5/2015.
 */

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @RequestMapping
    public String loadHomepage(){
        return "html/dashboard.html";
    }
}
