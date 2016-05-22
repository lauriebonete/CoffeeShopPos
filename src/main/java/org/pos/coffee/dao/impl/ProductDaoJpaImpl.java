package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Product;
import org.pos.coffee.dao.ProductDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 11/16/2015.
 */
@Repository("productDao")
public class ProductDaoJpaImpl extends BaseEntityDaoJpaImpl<Product,Long> implements ProductDao {

    @Override
    public Boolean validateProductCode(String productCode, Long id) {
        Query query = null;

        if(id != null){
            String queryString = "SELECT COUNT(obj.id) FROM Product obj where obj.productCode = :productCode AND obj.id != :id";
            query = getEntityManager().createQuery(queryString);
            query.setParameter("id", id);
        } else {
            String queryString = "SELECT COUNT(obj.id) FROM Product obj where obj.productCode = :productCode";
            query = getEntityManager().createQuery(queryString);
        }
        query.setParameter("productCode", productCode);

        Long count = (Long) query.getSingleResult();
        if(count!=null &&
                count>0){
            return false;
        }

        return true;
    }
}
