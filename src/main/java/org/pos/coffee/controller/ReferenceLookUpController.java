package org.pos.coffee.controller;

import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.service.ReferenceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/5/2015.
 */

@Controller
@RequestMapping("/reference")
public class ReferenceLookUpController extends BaseCrudController<ReferenceLookUp> {

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @RequestMapping(value = "/findAllCategory", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody List<String> findAllCategory() {
        List<String> results = new ArrayList<String>();
        results = referenceLookUpService.getAllCategory();
        return results;
    }

    @RequestMapping(value="/findReferenceLookUpByCategory/{category}", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody List<ReferenceLookUp> findReferenceLookUpByCategory(@PathVariable(value = "category")String category) {
        return referenceLookUpService.getReferenceLookUpByCategory(category);
    }
}
