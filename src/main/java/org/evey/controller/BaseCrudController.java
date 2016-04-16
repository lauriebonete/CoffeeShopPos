package org.evey.controller;

import org.apache.log4j.Logger;
import org.evey.bean.BaseEntity;
import org.evey.service.BaseCrudService;
import org.evey.utility.NamingUtil;
import org.evey.utility.StringUtil;
import org.evey.validator.BaseValidator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
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

    protected BaseValidator baseValidator;

    protected String htmlPage = "";

    protected String attributeName;

    protected TransactionTemplate transactionTemplate;

    protected Integer entityListSize = 10;

    @ExceptionHandler(Exception.class)
    public @ResponseBody Map<String,Object> handleException(Exception e){
        Map<String,Object> returnMap = new HashMap<>();

        _log.error(e.getMessage());

        returnMap.put("status", false);
        returnMap.put("message", "Ooopppsie! Something went wrong, but you're still awesome.");
        returnMap.put("error",e.getMessage());
        return returnMap;
    }

    @PostConstruct
    public void setProperties() {
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        attributeName = NamingUtil.toAttributeName(this.entityBeanType.getSimpleName());
        String serviceBean = attributeName + "Service";
        String validatorBean = attributeName + "Validator";

        _log.warn("Initiating controller's service "+serviceBean);
        if (this.baseCrudService == null
                && beanFactory.containsBean(serviceBean)) {
            this.baseCrudService = (BaseCrudService<T>) beanFactory.getBean(serviceBean);
        }
        if(this.baseValidator == null
                && beanFactory.containsBean(validatorBean)){
            this.baseValidator = (BaseValidator)beanFactory.getBean(validatorBean);
        }
        if (StringUtil.isEmpty(htmlPage)) {
            this.htmlPage = "html/" + NamingUtil.toCreatePath(attributeName) + ".html";
        }
        _log.info("Initiating HTML page "+this.htmlPage);

        PlatformTransactionManager txManager = (PlatformTransactionManager) beanFactory.getBean("transactionManager");
        this.transactionTemplate = new TransactionTemplate(txManager);
    }

    @InitBinder
    private void setValidator(WebDataBinder binder){
        if(baseValidator!=null){
            binder.addValidators(baseValidator);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public final @ResponseBody Map<String, Object> create(@RequestBody @Valid T entity, BindingResult errors) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        if(errors.hasErrors()){
            map.put("validatorError",true);
            map.put("errors",errors.getAllErrors());
        } else {
            _log.info(entity);
            createEntity(entity);
            map.put("message", "Transaction is successfully saved.");
            map.put("status", true);
            map.put("result", entity);
        }
        return map;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = "application/json")
    public final @ResponseBody Map<String, Object> delete(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (id != null && id > 0) {
            deleteEntity(id);
        }

        map.put("message", "That thing is now gone for good.");
        map.put("status",true);
        return map;
    }

    @RequestMapping(value = "/getEntityById", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody T getEntityById(Long entityId){
        T entity = baseCrudService.load(entityId);
        return entity;
    }

    @RequestMapping(value = "/addList", method = RequestMethod.POST, produces = "application/json")
    public final @ResponseBody Map<String,Object> addList(@RequestBody T[] entityList) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        for(T entity: entityList){
            createEntity(entity);
        }

        returnMap.put("message", "List is now successfully added.");
        returnMap.put("status", true);

        return returnMap;
    }

    @RequestMapping(value = "/findEntity", method = RequestMethod.POST, produces = "application/json")
    public final @ResponseBody Map<String, Object> findEntity(@RequestBody T entity) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> results = new ArrayList<Object>();
        results.addAll(baseCrudService.findEntity(entity));

        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<T> results = new ArrayList<T>();
        results = baseCrudService.findAll();
        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }

    @RequestMapping(value = "/findAllSort", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody Map<String, Object> findAllSort(T entity) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<T> results = new ArrayList<T>();
        results = baseCrudService.findAll(entity);
        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }

    @RequestMapping(value = "/findAllActive", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody Map<String, Object> findAllActive() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<T> results = new ArrayList<T>();
        results = baseCrudService.findAllActive();
        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }

    private final void createEntity(final T command) throws Exception {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                baseCrudService.save(command);
                postCreate(command);
            }
        });
    }

    private final void deleteEntity(final Long id) {
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
    public ModelAndView loadHtml() {
        _log.info("LOADING " + attributeName);
        return new ModelAndView(htmlPage);
    }

    protected void postCreate(T command){

    }

    public static Logger get_log() {
        return _log;
    }
}
