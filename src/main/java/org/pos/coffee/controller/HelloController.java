package org.pos.coffee.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class HelloController {
	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping
    public String handleRequest(){

//        return new ModelAndView("hello.jsp");
        return "html/index.html";
    }

}
