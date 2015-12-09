package org.evey.dao;

import org.evey.bean.BaseEntity;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface BaseEntityDao<T extends BaseEntity, Id extends Serializable> {

    public void save(T entity);
    public void delete(Long id);
    public List<T> findEntity(T entity) throws IllegalAccessException;
    public List<T> findAll();
    public List<T> findAllActive();
    public List<Object> findByListOfIds(List<Long> ids);
    public Set<Object> findBySetOfIds(Set<Long> ids);
    public T load(Long id);

    public String appendToWhere(String whereClause, T entity);
    public Query appendToParameters(Query query, T entity);
}
