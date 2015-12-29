package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.dao.ReferenceLookUpDao;
import org.pos.coffee.service.ReferenceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
@Service("referenceLookUpService")
public class ReferenceLookUpServiceImpl extends BaseCrudServiceImpl<ReferenceLookUp> implements ReferenceLookUpService {

    @Autowired
    private ReferenceLookUpDao referenceLookUpDao;

    @Override
    public List<String> getAllCategory() {
        return referenceLookUpDao.getAllCategory();
    }

    @Override
    public List<ReferenceLookUp> getReferenceLookUpByCategory(String category) {
        return referenceLookUpDao.getReferenceLookUpByCategory(category);
    }

    @Override
    public List<ReferenceLookUp> getActiveReferenceLookUpByCategory(String category) {
        return referenceLookUpDao.getActiveReferenceLookUpByCategory(category);
    }

    @Override
    public ReferenceLookUp getReferenceLookUpByKey(String key) {
        return referenceLookUpDao.getReferenceLookUpByKey(key);
    }
}
