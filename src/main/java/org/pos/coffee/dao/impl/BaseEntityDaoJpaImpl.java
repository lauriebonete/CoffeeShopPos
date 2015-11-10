package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.dao.BaseEntityDao;
import org.pos.coffee.utility.CrudUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
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
    public List<Object> findEntity(T entity) throws Exception{
        StringBuffer buffer = new StringBuffer();
        Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
        map = buildWhereClause(entity);
        buffer.append(buildSelectQuery());
        buffer.append(map.get("query").get("query"));

        Query query = getEntityManager().createQuery(buffer.toString());
        System.out.println(buffer.toString());
        for (Map.Entry<String, Object> entry : map.get("fields").entrySet()) {
            if(entry.getValue().equals(String.class)) {
                query.setParameter(entry.getKey(),"%"+CrudUtil.getValue(entity, entry.getKey())+"%");
                System.out.println(CrudUtil.getValue(entity, entry.getKey()));
            } else {
                System.out.println(CrudUtil.getValue(entity, entry.getKey()));
                query.setParameter(entry.getKey(), CrudUtil.getValue(entity, entry.getKey()));
            }

        }
        return query.getResultList();
    }

    @Override
    public List<Object> findAll() {
         Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj");
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("select obj from ")
                .append(getEntityBeanType().getName())
                .append(" obj ");
        return  buffer.toString();
    }

    private Map<String,Map<String,Object>> buildWhereClause(T entity) throws Exception{
        Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
        StringBuffer buffer = new StringBuffer();
        buffer.append("where ");

        Map<String,Object> fields = CrudUtil.getAllFields(entity,entity.getClass());
        boolean first = true;
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            if(!first){
                buffer.append(" and ");
            } else {
                first = false;
            }
            if(entry.getValue().equals(String.class)) {
                buffer.append("lower(obj."+entry.getKey()+") like lower(:"+entry.getKey()+") ");
            } else {
                buffer.append("obj."+entry.getKey()+" = :"+entry.getKey());
            }

        }

        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("query", buffer.toString());
        map.put("query",queryMap);
        map.put("fields",fields);
        return map;
    }
}
