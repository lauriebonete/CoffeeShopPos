package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Branch;
import org.pos.coffee.dao.BranchDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurie on 1/20/2016.
 */
@Repository("branchDao")
public class BranchDaoJpaImpl extends BaseEntityDaoJpaImpl<Branch,Long> implements BranchDao {

    @Override
    public Branch getBranchUsingMac(String macAddress) {
        /*String query = "SELECT obj FROM Branch obj LEFT JOIN obj.macAddresses mac where VALUE(obj.macAddresses) in (:macAddresses)";*/
        String query = "SELECT obj FROM Branch obj LEFT JOIN obj.macAddresses mac where :macAddresses in elements(obj.macAddresses)";
        Query queryObj = getEntityManager().createQuery(query);
        List<String> list = new ArrayList<>();
        list.add(macAddress);
        queryObj.setParameter("macAddresses", list);
        Branch object = (Branch) queryObj.getSingleResult();
        return object;
    }
}
