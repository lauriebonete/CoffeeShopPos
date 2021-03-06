package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.dao.StockDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("stockDao")
public class StockDaoJpaImpl extends BaseEntityDaoJpaImpl<Stock,Long> implements StockDao {

    @Override
    public List<StockHelper> getStockCount(String queryName) {
        String queryString = getNamedQuery(queryName);
        Query query = getEntityManager().createQuery(queryString);
        List<StockHelper> results = query.getResultList();
        return results;
    }

    @Override
    public List<StockHelper> findStockEntity(StockHelper stockHelper) {
        StringBuilder queryBuilder = new StringBuilder();
        StringBuilder whereClause = new StringBuilder();
        StringBuilder havingClause = new StringBuilder();
        queryBuilder.append("SELECT new org.pos.coffee.bean.helper.StockHelper(obj.item, SUM(obj.quantity),item.criticalLevel, item.demarcation) FROM Stock obj ")
                .append("INNER JOIN obj.item item ");

        if(stockHelper.getItem()!= null){
            String itemName = stockHelper.getItem().getItemName();
            String itemCode = stockHelper.getItem().getItemCode();
            Long id = stockHelper.getItem().getId();

            if((itemName!=null && itemName.length()>0)
                    || (itemCode!=null && itemCode.length()>0)
                    || id!=null) {


                queryBuilder.append("where ");
                if(itemCode!=null && itemCode.length()>0){
                    whereClause.append("lower(item.itemCode) like lower(:itemCode) ");
                }
                if(itemName!=null && itemName.length()>0){
                    if(whereClause.toString().length()>0){
                        whereClause.append("and ");
                    }
                    whereClause.append("lower(item.itemName) like lower(:itemName) ");
                }
                if(id!=null){
                    if(whereClause.toString().length()>0){
                        whereClause.append("and ");
                    }
                    whereClause.append("item.id = :itemId ");
                }
            }
        }

        if(stockHelper.getStatus()!= null
                && stockHelper.getStatus().length()>0){
            String status = stockHelper.getStatus();

            if(status!=null
                    && status.length()>0
                    && status.toLowerCase().equals("empty")){

                if(!queryBuilder.toString().contains("where ")) {
                    queryBuilder.append("where ");
                }

                if(whereClause.toString().length()>0){
                    whereClause.append("and ");
                }
                whereClause.append("obj.quantity is null ");
            }
        }
        queryBuilder.append(whereClause.toString());

        queryBuilder.append("GROUP BY obj.item ");

        if(stockHelper.getStatus()!= null
                && stockHelper.getStatus().length()>0){
            String status = stockHelper.getStatus();
                switch (status.toLowerCase()){
                    case "good":
                        havingClause.append("HAVING SUM(obj.quantity) > item.criticalLevel + item.demarcation ");
                        break;
                    case "low":
                        havingClause.append("HAVING SUM(obj.quantity) <= item.criticalLevel + item.demarcation AND ")
                                .append("SUM(obj.quantity) > item.criticalLevel ");
                        break;
                    case "critical":
                        havingClause.append("HAVING SUM(obj.quantity) <= item.criticalLevel ");
                        break;
                }
            queryBuilder.append(havingClause.toString());
        }

        queryBuilder.append("ORDER BY CASE WHEN sum(obj.quantity) IS NULL THEN 1 ELSE 0 END, sum(obj.quantity) ");

        Query query = getEntityManager().createQuery(queryBuilder.toString());

        if(stockHelper.getItem()!= null){

            String itemName = stockHelper.getItem().getItemName();
            String itemCode = stockHelper.getItem().getItemCode();
            Long id = stockHelper.getItem().getId();

            if((itemName!=null && itemName.length()>0)
                    || (itemCode!=null && itemCode.length()>0)
                    || id!=null) {

                if(itemName!=null && itemName.length()>0){
                    query.setParameter("itemName","%"+stockHelper.getItem().getItemName().trim()+"%");
                }

                if(itemCode!=null && itemCode.length()>0){
                    query.setParameter("itemCode","%"+stockHelper.getItem().getItemCode().trim()+"%");
                }

                if(id!=null){
                    query.setParameter("itemId",id);
                }
            }
        }

        List<StockHelper> results = query.getResultList();

        return results;
    }
}
