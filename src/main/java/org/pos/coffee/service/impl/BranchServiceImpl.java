package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Branch;
import org.pos.coffee.dao.BranchDao;
import org.pos.coffee.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/20/2016.
 */
@Service("branchService")
public class BranchServiceImpl extends BaseCrudServiceImpl<Branch> implements BranchService {

    @Autowired
    private BranchDao branchDao;

    @Override
    public Branch getBranchUsingMac(String macAddress) {
        return branchDao.getBranchUsingMac(macAddress);
    }
}
