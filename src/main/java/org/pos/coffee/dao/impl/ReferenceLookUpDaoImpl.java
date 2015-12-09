package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.dao.ReferenceLookUpDao;
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
}
