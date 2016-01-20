package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.Branch;

/**
 * Created by Laurie on 1/20/2016.
 */
public interface BranchDao extends BaseEntityDao<Branch, Long> {
    public Branch getBranchUsingMac(String macAddress);
}
