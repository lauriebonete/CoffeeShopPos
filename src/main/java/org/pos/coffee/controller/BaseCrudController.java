package org.pos.coffee.controller;

import org.apache.log4j.Logger;
import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.service.BaseCrudService;
import org.pos.coffee.utility.NamingUtil;
import org.pos.coffee.utility.StringUtil;
import org.pos.coffee.web.json.ResponseScope;
import org.pos.coffee.web.json.Scope;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Created by Laurie on 11/5/2015.
 */
public abstract class BaseCrudController<T extends BaseEntity> {

    private static Logger _log = Logger.getLogger(BaseCrudController.class);

    @Autowired
    protected BeanFactory beanFactory;

    protected Class<T> entityBeanType;

    protected BaseCrudService<T> baseCrudService;

    protected String htmlPage = "";

    protected String attributeName;

    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void setProperties(){
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        attributeName = NamingUtil.toAttributeName(this.entityBeanType .getSimpleName());
        String serviceBean = attributeName + "Service";

        System.out.println(serviceBean);
        if(this.baseCrudService == null
                && beanFactory.containsBean(serviceBean)) {
            this.baseCrudService = (BaseCrudService<T>) beanFactory.getBean(serviceBean);
        }
        if(StringUtil.isEmpty(htmlPage)) {
            /*this.htmlPage = "redirect:html/"+NamingUtil.toCreatePath(attributeName)+".html";*/
            this.htmlPage = "html/"+NamingUtil.toCreatePath(attributeName)+".html";
        }

        PlatformTransactionManager txManager = (PlatformTransactionManager) beanFactory.getBean("transactionManager");
        this.transactionTemplate = new TransactionTemplate(txManager);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String,Object> catchException(Exception e) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("message", "Error encountered");
        map.put("status", false);

        e.printStackTrace();

        return map;
    }

    @RequestMapping(method= RequestMethod.POST, produces = "application/json")
    public final @ResponseBody Map<String,Object> create(T entity) {
        _log.info(entity);
        Map<String, Object> map = new HashMap<String, Object>();
            createEntity(entity);
            map.put("message","success");
            map.put("record",entity);
            map.put("status", true);

        return map;
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final @ResponseBody Map<String,Object> delete(@PathVariable("id")Long id){
        Map<String, Object> map = new HashMap<String, Object>();

        if(id != null && id>0){
            deleteEntity(id);
        }

        map.put("message", "success");
        map.put("remove", id);
        map.put("status", true);

        return map;
    }

    @RequestMapping(value = "/findEntity", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody Map<String, Object> findEntity(final T entity){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> results = new ArrayList<Object>();
        try{
            results = baseCrudService.findEntity(entity);
            map.put("status",true);
            map.put("records",results);
            map.put("message","Success");
        }catch (Exception e){
            map.put("message", "Error encountered");
            map.put("status", false);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/loadTable", method = RequestMethod.GET, produces = "application/json")
    @ResponseScope(Scope.Search.class)
    public final @ResponseBody Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> results = new ArrayList<Object>();
        results = baseCrudService.findAll();
        map.put("results", results);
        map.put("size", results.size());
        return map;
    }

    private final void createEntity(final T command){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
              baseCrudService.save(command);
            }
        });
    }

    private final void deleteEntity(final Long id){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
               baseCrudService.delete(id);
            }
        });
    }

    public BaseCrudService getService() {
        return baseCrudService;
    }

    @RequestMapping
    public ModelAndView loadHtml(){
        _log.info("LOADING "+attributeName);
        return new ModelAndView(htmlPage);
    }
}
