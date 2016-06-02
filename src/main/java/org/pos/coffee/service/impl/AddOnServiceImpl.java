package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.AddOn;
import org.pos.coffee.dao.AddOnDao;
import org.pos.coffee.service.AddOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/20/2016.
 */
@Service("addOnService")
public class AddOnServiceImpl extends BaseCrudServiceImpl<AddOn> implements AddOnService {

    @Autowired
    private AddOnDao addOnDao;

    @Override
    public void updateAddOnCost(Long addOnId, Double cost) {
        addOnDao.updateAddOnCost(addOnId,cost);
    }
}
