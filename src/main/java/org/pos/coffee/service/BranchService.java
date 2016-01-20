package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Branch;

/**
 * Created by Laurie on 1/20/2016.
 */
public interface BranchService extends BaseCrudService<Branch> {
    public Branch getBranchUsingMac(String macAddress);
}
