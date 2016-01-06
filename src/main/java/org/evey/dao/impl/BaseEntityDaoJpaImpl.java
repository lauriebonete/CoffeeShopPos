package org.evey.dao.impl;

import org.apache.log4j.Logger;
import org.evey.annotation.JoinList;
import org.evey.annotation.JoinSet;
import org.evey.annotation.UniqueField;
import org.evey.bean.BaseEntity;
import org.evey.bean.QueryHelper;
import org.evey.dao.BaseEntityDao;
import org.evey.utility.CrudUtil;
import org.evey.utility.NamingUtil;
import org.evey.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by Laurie on 11/5/2015.
 */
public class BaseEntityDaoJpaImpl<T extends BaseEntity, Id extends Serializable> implements BaseEntityDao<T,Id> {

    protected static Logger _log = Logger.getLogger(BaseEntityDaoJpaImpl.class);

    @Autowired
    private Properties queryProperties;

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityBeanType;

    public BaseEntityDaoJpaImpl() {
        this.entityBeanType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        if(entity.isNew()) {
            getEntityManager().persist(entity);
        } else {

            if(entity.getVersion()==null){
                entity = (T)CrudUtil.setProperties(load(entity.getId()),entity);
            }
            getEntityManager().merge(entity);
            getEntityManager().flush();
        }
    }

    @Override
    public void delete(Long id) {
        T entity = getEntityManager().find(getEntityBeanType(), id);
        deleteEntity(entity);
    }

    public void deleteEntity(final T entity){
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }

    @Override
    public List<T> findEntity(T entity) throws IllegalAccessException{
        StringBuffer queryBuilder = new StringBuffer();
        queryBuilder.append(buildSelectQuery());
        queryBuilder.append(buildJoinQuery(getEntityBeanType(),entity));
        List<QueryHelper> fieldAndValueList = getFields(getEntityBeanType(),entity);
        String whereClause = buildWhereQuery(fieldAndValueList);
        queryBuilder.append(whereClause);
        queryBuilder.append(appendToWhere(whereClause, entity));

        _log.warn(queryBuilder.toString());

        Query query = getEntityManager().createQuery(queryBuilder.toString());
        createParameters(query,fieldAndValueList);
        appendToParameters(query,entity);
        return query.getResultList();
    }

    @Override
    public List<T> findAll() {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj");
        return query.getResultList();
    }

    @Override
    public List<T> findAllActive() {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj where obj.isActive = true");
        return query.getResultList();
    }

    @Override
    public List<Object> findByListOfIds(List<Long> ids) {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj where obj.id in (:ids)");
        query.setParameter("ids",ids);
        return query.getResultList();
    }

    @Override
    public Set<Object> findBySetOfIds(Set<Long> ids) {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj where obj.id in (:ids)");
        query.setParameter("ids",ids);

        Set<Object> result = new HashSet<>();
        for(Object object:  query.getResultList()) {
            result.add(object);
        }

        return result;
    }

    @Override
    public List<T> findEntityByNamedQuery(String name) {
        String queryString = getNamedQuery(name);
        Query query = getEntityManager().createQuery(queryString);

        return query.getResultList();
    }

    @Override
    public T load(Long id) {
        T entity = getEntityManager().find(getEntityBeanType(), id);
        return entity;
    }

    protected EntityManager getEntityManager() {
        if(entityManager==null) {
            throw new IllegalStateException("Entity Manager is not yet set.");
        }
        return entityManager;
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    private String buildSelectQuery() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select obj from "+getEntityBeanType().getName()+" obj");
        return queryBuffer.toString();
    }

    private String buildJoinQuery(Class<?> type, T entity) {
        StringBuffer queryBuffer = new StringBuffer();

        for(Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            //if JoinList annotation is present Join should be added on JPA query
            if(field.isAnnotationPresent(JoinList.class) ||
                    field.isAnnotationPresent(JoinSet.class)) {
                queryBuffer.append(" LEFT JOIN obj."+field.getName()+" "+field.getName());
            }
        }

        return queryBuffer.toString();
    }

    private String buildWhereQuery(List<QueryHelper> queryHelper) throws IllegalAccessException{
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append(" where ");
        boolean first = true;
        for(QueryHelper query:queryHelper){
            if(!first){
                queryBuffer.append(" and ");
            } else {
                first = false;
            }

            if(List.class.isAssignableFrom(query.getEntityType()) ||
                    Set.class.isAssignableFrom(query.getEntityType())){
                queryBuffer.append(query.getFieldName()+" in (:"+NamingUtil.toParamName(query.getFieldName()) + ") ");
            } else if(
                    (Boolean.class.isAssignableFrom(query.getEntityType()) ||
                            Long.class.isAssignableFrom(query.getEntityType()) ||
                            Integer.class.isAssignableFrom(query.getEntityType()) ||
                            Date.class.isAssignableFrom(query.getEntityType())
            ) || (query.getIsUnique()!=null &&
                    query.getIsUnique() )){
                queryBuffer.append("obj."+query.getFieldName()+" = :"+NamingUtil.toParamName(query.getFieldName()));
            } else {
                queryBuffer.append("lower(obj."+query.getFieldName()+") like :"+NamingUtil.toParamName(query.getFieldName()));
            }
        }

        return queryBuffer.toString();
    }

    private List<QueryHelper> getFields(Class<?> type, T entity) throws IllegalAccessException {
        Map<String, Object> fieldAndValue = new HashMap<String,Object>();
        List<QueryHelper> queryHelper = new ArrayList<>();
        if(type.getSuperclass()!=null) {
            List<QueryHelper> parentValues = getFields(type.getSuperclass(), entity);
            queryHelper.addAll(parentValues);
        }

        for(Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if((!Modifier.isTransient(field.getModifiers())) &&
                    (!Modifier.isVolatile(field.getModifiers())) &&
                    (!Modifier.isStatic(field.getModifiers())) &&
                    (!field.isAnnotationPresent(Transient.class)) &&
                    field.get(entity)!=null &&
                    field.get(entity)!=""){
                try {
                    Method method = type.getMethod(NamingUtil.toGetterName(field.getName()));
                    Object value = method.invoke(entity);
                    boolean isList = false;
                    if(field.isAnnotationPresent(JoinList.class)
                            && List.class.isAssignableFrom(method.invoke(entity).getClass())){
                        List list = (List)method.invoke(entity);
                        value = (T) list.get(0);
                        isList = true;
                    }

                    boolean isSet = false;
                    if(field.isAnnotationPresent(JoinSet.class) &&
                            Set.class.isAssignableFrom(method.invoke(entity).getClass())) {
                        Set set = (Set)method.invoke(entity);
                        value = (T) set.iterator().next();
                        isSet = true;
                    }

                    if(BaseEntity.class.isAssignableFrom(value.getClass())){
                        List<QueryHelper> innerValue = getFields(value.getClass(), (T)value);
                        for(QueryHelper baseEntity:innerValue){
                            baseEntity.setFieldName(field.getName()+"."+baseEntity.getFieldName());
                            if(isList){
                                baseEntity.setEntityType(List.class);
                            } else if(isSet) {
                                baseEntity.setEntityType(Set.class);
                            }
                        }
                        queryHelper.addAll(innerValue);
                    }else {
                        QueryHelper query = new QueryHelper();
                        query.setFieldName(field.getName());
                        if(isList){
                            query.setEntityType(List.class);
                        } else if(isSet){
                            query.setEntityType(Set.class);
                        } else {
                            query.setEntityType(value.getClass());
                        }
                        query.setValue(value);

                        if(field.isAnnotationPresent(UniqueField.class)) {
                            query.setIsUnique(true);
                        }

                        queryHelper.add(query);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return queryHelper;
    }

    private Query createParameters(Query query, List<QueryHelper> queryHelper) {
        for(QueryHelper helper:queryHelper){
            if((!Boolean.class.isAssignableFrom(helper.getEntityType()) &&
               !Long.class.isAssignableFrom(helper.getEntityType()) &&
               !Integer.class.isAssignableFrom(helper.getEntityType())
                )
                && !(helper.getIsUnique()!=null && helper.getIsUnique())
                && !List.class.isAssignableFrom(helper.getEntityType())) {
                query.setParameter(NamingUtil.toParamName(helper.getFieldName()),"%"+helper.getValue()+"%");
            } else if(List.class.isAssignableFrom(helper.getEntityType())) {
                query.setParameter(NamingUtil.toParamName(helper.getFieldName()), helper.getValue());
            } else {
                query.setParameter(NamingUtil.toParamName(helper.getFieldName()),helper.getValue());
            }
        }
        return query;
    }

    public String appendToWhere(String whereClause, T entity) {
       return "";
    }

    public Query appendToParameters(Query query, T entity) {
        return null;
    }

    public final String getNamedQuery(String queryName){
        String query = (String) queryProperties.get(queryName);
        if(!StringUtil.isEmpty(query)){
            return query;
        } else {
            _log.error("NO property with name "+queryName+" found.");
        }
        return null;
    }
}
