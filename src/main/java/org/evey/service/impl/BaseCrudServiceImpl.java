package org.evey.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.evey.annotation.JoinList;
import org.evey.annotation.JoinSet;
import org.evey.bean.BaseEntity;
import org.evey.dao.BaseEntityDao;
import org.evey.service.BaseCrudService;
import org.evey.utility.NamingUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Laurie on 11/5/2015.
 */
public class BaseCrudServiceImpl<T extends BaseEntity> implements BaseCrudService<T> {

    private Class<T> entityBeanType;

    @Autowired
    private BeanFactory beanFactory;

    protected BaseEntityDao<T,Long> baseEntityDao;

    private String attributeName;

    @Override
    @Transactional
    public final void save(T entity) {
        preloadEntity(entity);
        baseEntityDao.save(entity);
    }

    @Override
    public List<T> findActiveEntity(T entity) throws Exception {
        entity.setIsActive(true);
        return baseEntityDao.findEntity(entity);
    }

    @Override
    public List<T> findEntity(T entity)  throws Exception{
        return baseEntityDao.findEntity(entity);
    }

    @Override
    public List<T> findAllActive() {
        return baseEntityDao.findAllActive();
    }

    @Override
    public List<T> findAll() {
        return baseEntityDao.findAll();
    }

    @Override
    public List<T> findAll(T entity) {
        return baseEntityDao.findAll(entity);
    }

    @Override
    public void delete(Long id) {
        baseEntityDao.delete(id);
    }

    @Override
    public T load(Long id) {
        return baseEntityDao.load(id);
    }

    @Override
    public BaseEntityDao<T,Long> getDao() {
        return baseEntityDao;
    }

    @Override
    public List<Object> findByListOfIds(List<Long> ids) {
        return baseEntityDao.findByListOfIds(ids);
    }

    @Override
    public Set<Object> findBySetOfIds(Set<Long> ids) {
        return baseEntityDao.findBySetOfIds(ids);
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

    @Override
    public List<T> findEntityByNamedQuery(String queryName) {
        return baseEntityDao.findEntityByNamedQuery(queryName);
    }

    @Override
    public List<T> findEntityByNamedQuery(String queryName, Map<String, Object> parameters) {
        return baseEntityDao.findEntityByNamedQuery(queryName, parameters);
    }

    @Override
    @Transactional
    public void executeUpdateByNamedQuery(String queryName, Map<String, Object> parameters) {
        baseEntityDao.executeUpdateByNamedQuery(queryName,parameters);
    }

    private void preloadEntity(T entity) {
        try {
            for(Field field: entityBeanType.getDeclaredFields()) {
                field.setAccessible(true);
                if (!Modifier.isVolatile(field.getModifiers()) &&
                        !Modifier.isStatic(field.getModifiers()) &&
                        !field.isAnnotationPresent(Transient.class) &&
                        field.get(entity) != null &&
                        field.get(entity) != "") {
                    Method getterMethod = entityBeanType.getMethod(NamingUtil.toGetterName(field.getName()));
                    Object value = getterMethod.invoke(entity);

                    if(field.isAnnotationPresent(JoinList.class)
                            && List.class.isAssignableFrom(getterMethod.invoke(entity).getClass())){
                        List list = (List)getterMethod.invoke(entity);
                        List<Long> idList = new ArrayList<>();
                        for(Object object:list){
                            //search for loadable objects
                            BaseEntity baseEntity = (BaseEntity) object;
                            if(baseEntity.getId()!=null){
                                idList.add(baseEntity.getId());
                            }
                        }
                        if(list.size()>0){
                            String serviceName=NamingUtil.toAttributeName(list.get(0).getClass().getSimpleName());
                            BaseCrudService baseCrudServiceLoader = (BaseCrudService) beanFactory.getBean(serviceName + "Service");
                            List foundList = baseCrudServiceLoader.findByListOfIds(idList);
                            BeanUtils.setProperty(entity,field.getName(),foundList);
                        }
                    } else if (field.isAnnotationPresent(JoinSet.class)
                            && Set.class.isAssignableFrom(getterMethod.invoke(entity).getClass())){
                        Set set = (Set)getterMethod.invoke(entity);
                        Set<Long> idSet = new HashSet<>();
                        for(Object object:set){
                            //search for loadable objects
                            BaseEntity baseEntity = (BaseEntity) object;
                            if(baseEntity.getId()!=null){
                                idSet.add(baseEntity.getId());
                            }
                        }
                        if(set.size()>0){
                            String serviceName=NamingUtil.toAttributeName(set.iterator().next().getClass().getSimpleName());
                            BaseCrudService baseCrudServiceLoader = (BaseCrudService) beanFactory.getBean(serviceName + "Service");
                            Set foundList = baseCrudServiceLoader.findBySetOfIds(idSet);
                            BeanUtils.setProperty(entity,field.getName(),foundList);
                        }
                    } else {
                        if (BaseEntity.class.isAssignableFrom(value.getClass())) {
                            BaseEntity baseEntity = (BaseEntity) value;

                            //if id is not null and version is not null we should load the entity from DBx
                            if(baseEntity.getId()!=null && baseEntity.getVersion()==null){
                                String serviceName = NamingUtil.toAttributeName(value.getClass().getSimpleName());
                                BaseCrudService baseCrudServiceLoader = (BaseCrudService) beanFactory.getBean(serviceName + "Service");
                                Object foundValue = baseCrudServiceLoader.load(((BaseEntity) value).getId());
                                BeanUtils.setProperty(entity, field.getName(), foundValue);
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException| NoSuchMethodException e){
            e.printStackTrace();
        }
    }
}
