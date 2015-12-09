package org.evey.service;

import org.evey.bean.BaseEntity;
import org.evey.dao.BaseEntityDao;

import java.util.List;
import java.util.Set;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface BaseCrudService<T extends BaseEntity> extends BaseService {

    public void save(T entity);
    public void delete(Long id);
    public List<T> findEntity(T entity) throws Exception;
    public List<T> findAll();
    public List<T> findAllActive();
    public List<Object> findByListOfIds(List<Long> ids);
    public Set<Object> findBySetOfIds(Set<Long> ids);
    public T load(Long id);

    public BaseEntityDao<T,Long> getDao();
}

