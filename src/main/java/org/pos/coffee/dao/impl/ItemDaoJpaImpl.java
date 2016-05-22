package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.dao.ItemDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 11/16/2015.
 */
@Repository("itemDao")
public class ItemDaoJpaImpl extends BaseEntityDaoJpaImpl<Item, Long> implements ItemDao {

    @Override
    public Boolean validateItemCode(String itemCode, Long id) {
        Query query = null;

        if(id != null){
            String queryString = "SELECT COUNT(obj.id) FROM Item obj where obj.itemCode = :itemCode AND obj.id != :id";
            query = getEntityManager().createQuery(queryString);
            query.setParameter("id", id);
        } else {
            String queryString = "SELECT COUNT(obj.id) FROM Item obj where obj.itemCode = :itemCode";
            query = getEntityManager().createQuery(queryString);
        }
        query.setParameter("itemCode", itemCode);

        Long count = (Long) query.getSingleResult();
        if(count!=null &&
                count>0){
            return false;
        }

        return true;
    }
}
