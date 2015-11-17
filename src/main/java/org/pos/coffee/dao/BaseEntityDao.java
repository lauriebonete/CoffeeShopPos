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
    public List<Object> findEntity(T entity) throws IllegalAccessException;
    public List<Object> findAll();
}
