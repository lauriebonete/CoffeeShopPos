package org.evey.dao.impl;

import org.evey.bean.ReferenceLookUp;
import org.evey.dao.ReferenceLookUpDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
@Repository("referenceLookUpDao")
public class ReferenceLookUpDaoImpl extends BaseEntityDaoJpaImpl<ReferenceLookUp,Long> implements ReferenceLookUpDao {

    @Override
    public List<String> getAllCategory() {
        Query query = getEntityManager().createQuery("select distinct (obj.category) from ReferenceLookUp obj");
        return query.getResultList();
    }

    @Override
    public List<ReferenceLookUp> getReferenceLookUpByCategory(String category) {
        Query query = getEntityManager().createQuery("select obj from ReferenceLookUp obj where obj.category = :category");
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<ReferenceLookUp> getActiveReferenceLookUpByCategory(String category) {
        Query query = getEntityManager().createQuery("select obj from ReferenceLookUp obj where obj.category = :category and obj.isActive = true");
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public ReferenceLookUp getReferenceLookUpByKey(String key) {
        Query query = getEntityManager().createQuery("select obj from ReferenceLookUp obj where obj.key = :lookKey");
        query.setParameter("lookKey", key);
        ReferenceLookUp found = (ReferenceLookUp) query.getSingleResult();
        return found;
    }

    @Override
    public Boolean validateUniqueKey(String key, Long id) {
        Query query = null;

        if(id != null){
            String queryString = "SELECT COUNT(obj.id) FROM ReferenceLookUp obj where obj.key = :key AND obj.id != :id";
            query = getEntityManager().createQuery(queryString);
            query.setParameter("id", id);
        } else {
            String queryString = "SELECT COUNT(obj.id) FROM ReferenceLookUp obj where obj.key = :key";
            query = getEntityManager().createQuery(queryString);
        }
        query.setParameter("key", key);

        Long count = (Long) query.getSingleResult();
        if(count!=null &&
                count>0){
            return false;
        }

        return true;
    }
}
