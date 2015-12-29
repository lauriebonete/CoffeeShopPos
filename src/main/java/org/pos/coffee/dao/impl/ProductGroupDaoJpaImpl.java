package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.dao.ProductGroupDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Laurie on 12/16/2015.
 */
@Repository("productGroupDao")
public class ProductGroupDaoJpaImpl extends BaseEntityDaoJpaImpl<ProductGroup,Long> implements ProductGroupDao {
    @Override
    public List<ProductGroup> getProductGroups() {
        Query query = getEntityManager().createQuery("select obj from ProductGroup obj");
        return query.getResultList();
    }
}
