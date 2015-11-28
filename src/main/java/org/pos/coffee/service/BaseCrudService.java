package org.pos.coffee.service;

import org.pos.coffee.bean.BaseEntity;
import org.pos.coffee.dao.BaseEntityDao;

import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface BaseCrudService<T extends BaseEntity> extends BaseService {

    public void save(T entity);
    public void delete(Long id);
    public List<T> findEntity(T entity) throws Exception;
    public List<T> findAll();
    public List<Object> findByListOfIds(List<Long> ids);
    public T load(Long id);

    public BaseEntityDao<T,Long> getDao();
}

