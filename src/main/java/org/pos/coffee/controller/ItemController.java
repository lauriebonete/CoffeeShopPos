package org.pos.coffee.controller;

import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.service.ReferenceLookUpService;
import org.pos.coffee.web.BaseEntityEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Laurie on 11/11/2015.
 */

@Controller
@RequestMapping("/item")
public class ItemController extends BaseCrudController<Item> {

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @InitBinder
    public void setInitBinder(WebDataBinder binder) throws Exception{
        binder.registerCustomEditor(ReferenceLookUp.class, new BaseEntityEditor(referenceLookUpService));
    }


    @RequestMapping(value="/findAllUom", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<ReferenceLookUp> findAllUom(){
        return referenceLookUpService.getReferenceLookUpByCategory("CATEGORY_UNIT_OF_MEASURE");
    }

}
