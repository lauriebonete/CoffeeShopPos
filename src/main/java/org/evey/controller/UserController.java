package org.evey.controller;

import org.evey.bean.User;
import org.evey.bean.FileDetail;
import org.evey.service.FileDetailService;
import org.evey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
        FileDetail personImage = fileDetailService.load(user.getPerson().getPersonImage().getId());
        saveToThis.getPerson().setPersonImage(personImage);
        userService.save(saveToThis);

        returnMap.put("status", true);
        returnMap.put("message", "User image was successfully saved. "+saveToThis.getPerson().getFirstName()+" looks awesome in this photo!");
        returnMap.put("result", saveToThis);

        return returnMap;
    }

    @RequestMapping(value = "/remove-image", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> removeUserImage(@RequestBody User user){
        Map<String, Object> returnMap = new HashMap<>();

        User saveToThis = userService.load(user.getId());
        saveToThis.getPerson().setPersonImage(null);
        userService.save(saveToThis);

        returnMap.put("status", true);
        returnMap.put("message", "That awesome photo of "+saveToThis.getPerson().getFirstName()+" is now removed.");
        returnMap.put("result", saveToThis);

        return  returnMap;
    }
}
