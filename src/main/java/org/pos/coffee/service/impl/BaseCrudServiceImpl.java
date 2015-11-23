package org.pos.coffee.service.impl;

import org.pos.coffee.annotation.JoinList;
import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.bean.QueryHelper;
import org.pos.coffee.dao.BaseEntityDao;
import org.pos.coffee.service.BaseCrudService;
import org.pos.coffee.utility.NamingUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
public class BaseCrudServiceImpl<T extends BaseEntity> implements BaseCrudService<T> {

    private Class<T> entityBeanType;

    private BaseCrudService baseCrudServiceLoader;

    @Autowired
    private BeanFactory beanFactory;

    protected BaseEntityDao<T,Long> baseEntityDao;

    private String attributeName;

    @Override
    public final void save(T entity) {
        baseEntityDao.save(entity);
    }

    @Override
    public List<Object> findEntity(T entity)  throws Exception{
        return baseEntityDao.findEntity(entity);
    }

    @Override
    public List<Object> findAll() {
        return baseEntityDao.findAll();
    }

    @Override
    public void delete(Long id) {
        baseEntityDao.delete(id);
    }

    @Override
    public BaseEntityDao<T,Long> getDao() {
        return baseEntityDao;
    }

    @PostConstruct
    public void setProperties() {
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        attributeName = NamingUtil.toAttributeName(this.entityBeanType.getSimpleName());

        String daoName = attributeName+"Dao";
        if(baseEntityDao==null && beanFactory.containsBean(daoName)) {
            this.baseEntityDao = (BaseEntityDao) beanFactory.getBean(daoName);
        }
    }

    private void preloadEntity(T entity) throws Exception{
        String serviceName=null;
        for(Field field: entityBeanType.getDeclaredFields()) {
            field.setAccessible(true);
            if(!Modifier.isVolatile(field.getModifiers()) &&
                    !Modifier.isStatic(field.getModifiers()) &&
                    !field.isAnnotationPresent(Transient.class) &&
                    field.get(entity)!=null &&
                    field.get(entity)!=""){
                Method method = entityBeanType.getMethod(NamingUtil.toGetterName(field.getName()));
                Object value = method.invoke(entity);
                if(field.isAnnotationPresent(JoinList.class)
                        && List.class.isAssignableFrom(method.invoke(entity).getClass())){
                    List list = (List)method.invoke(entity);
                    value = (T) list.get(0);
                }
                if(BaseEntity.class.isAssignableFrom(value.getClass())){
                    serviceName = NamingUtil.toAttributeName(value.getClass().getName());
                }
                this.baseCrudServiceLoader = (BaseCrudService) beanFactory.getBean(serviceName);
            }
        }
        /*this.baseCrudServiceLoader = (BaseCrudService<T>) beanFactory.getBean(serviceBean);*/
    }
}
