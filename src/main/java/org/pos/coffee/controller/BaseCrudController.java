package org.pos.coffee.controller;

import org.apache.log4j.Logger;
import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.service.BaseCrudService;
import org.pos.coffee.utility.NamingUtil;
import org.pos.coffee.utility.StringUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            this.htmlPage = "html/"+NamingUtil.toCreatePath(attributeName)+".html";
        }

        PlatformTransactionManager txManager = (PlatformTransactionManager) beanFactory.getBean("transactionManager");
        this.transactionTemplate = new TransactionTemplate(txManager);
    }

    @RequestMapping(method= RequestMethod.POST, produces = "application/json")
    public final @ResponseBody String create(T entity) {
        _log.info(entity);
        createEntity(entity);
        return "here";
    }

    @RequestMapping(value = "/findEntity", method = RequestMethod.GET, produces = "application/json")
    public final @ResponseBody Map<String, Object> findEntity(final T entity){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> results = new ArrayList<Object>();

        return map;
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
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

    public BaseCrudService getService() {
        return baseCrudService;
    }

    @RequestMapping
    public String loadHtml(){
        _log.info("LOADING "+attributeName);
        return htmlPage;
    }
}
