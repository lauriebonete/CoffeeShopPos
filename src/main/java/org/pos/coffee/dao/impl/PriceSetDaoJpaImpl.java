package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.PriceSet;
import org.pos.coffee.dao.PriceSetDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 12/7/2015.
 */
@Repository("priceSetDao")
public class PriceSetDaoJpaImpl extends BaseEntityDaoJpaImpl<PriceSet,Long> implements PriceSetDao {

    @Override
    public String appendToWhere(String whereClause, PriceSet entity) {
        StringBuilder builder = new StringBuilder();

        if(entity.getLookForStartDate()!=null) {
            if((whereClause!= null &&
                    whereClause.length() > 7) ||
                    builder.toString().length()>5){
                builder.append(" and ");
            }
            builder.append("obj.startDate <= :lookForStartDate");
        }

        if(entity.getLookForEndDate()!=null){
            if((whereClause!= null &&
                    whereClause.length() > 7) ||
                    builder.toString().length()>5){
                builder.append(" and ");
            }
            builder.append("obj.endDate >= :lookForEndDate");
        }

        return builder.toString();
    }

    @Override
    public Query appendToParameters(Query query, PriceSet entity) {
        if(entity.getLookForStartDate() != null){
            query.setParameter("lookForStartDate",entity.getLookForStartDate());
        }

        if(entity.getLookForEndDate()!=null){
            query.setParameter("lookForEndDate", entity.getLookForStartDate());
        }
        return query;
    }
}
