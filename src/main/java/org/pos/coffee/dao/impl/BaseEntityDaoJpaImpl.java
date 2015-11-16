package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.dao.BaseEntityDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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
    public List<Object> findEntity(T entity) {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj");
        return query.getResultList();
    }

    @Override
    public List<Object> findAll() {
         Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj");
        return query.getResultList();
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
}
