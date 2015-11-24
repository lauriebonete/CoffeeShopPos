package org.pos.coffee.dao.impl;

import org.pos.coffee.annotation.JoinList;
import org.pos.coffee.annotation.UniqueField;
import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.bean.QueryHelper;
import org.pos.coffee.dao.BaseEntityDao;
import org.pos.coffee.utility.CrudUtil;
import org.pos.coffee.utility.NamingUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/5/2015.
 */
public class BaseEntityDaoJpaImpl<T extends BaseEntity, Id extends Serializable> implements BaseEntityDao<T,Id> {

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
    public List<Object> findEntity(T entity) throws IllegalAccessException{
        StringBuffer queryBuilder = new StringBuffer();
        queryBuilder.append(buildSelectQuery());
        queryBuilder.append(buildJoinQuery(getEntityBeanType(),entity));
        List<QueryHelper> fieldAndValueList = getFields(getEntityBeanType(),entity);
        queryBuilder.append(buildWhereQuery(fieldAndValueList));

        Query query = getEntityManager().createQuery(queryBuilder.toString());
        return createParameters(query,fieldAndValueList).getResultList();
    }

    @Override
    public List<Object> findAll() {
         Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj");
        return query.getResultList();
    }

    @Override
    public List<Object> findByListOfIds(List<Long> ids) {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj where obj.id in (:ids)");
        query.setParameter("ids",ids);
        return query.getResultList();
    }

    @Override
    public Object load(Long id) {
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
            if(field.isAnnotationPresent(JoinList.class)) {
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

            if(List.class.isAssignableFrom(query.getEntityType())){
                queryBuffer.append(query.getFieldName()+" in (:"+NamingUtil.toParamName(query.getFieldName()) + ") ");
            } else if((
                    Boolean.class.isAssignableFrom(query.getEntityType()) ||
                            Long.class.isAssignableFrom(query.getEntityType()) ||
                            Integer.class.isAssignableFrom(query.getEntityType())
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
                    if(BaseEntity.class.isAssignableFrom(value.getClass())){
                        List<QueryHelper> innerValue = getFields(value.getClass(), (T)value);
                        for(QueryHelper baseEntity:innerValue){
                            baseEntity.setFieldName(field.getName()+"."+baseEntity.getFieldName());
                            if(isList){
                                baseEntity.setEntityType(List.class);
                            }
                        }
                        queryHelper.addAll(innerValue);
                    }else {
                        QueryHelper query = new QueryHelper();
                        query.setFieldName(field.getName());
                        if(isList){
                            query.setEntityType(List.class);
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
}
