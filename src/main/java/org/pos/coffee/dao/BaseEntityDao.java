package org.pos.coffee.dao;

import org.pos.coffee.bean.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface BaseEntityDao<T extends BaseEntity, Id extends Serializable> {

    public void save(T entity);
    public void delete(Long id);
    public List<T> findEntity(T entity) throws IllegalAccessException;
    public List<T> findAll();
    public List<Object> findByListOfIds(List<Long> ids);
    public T load(Long id);
}
