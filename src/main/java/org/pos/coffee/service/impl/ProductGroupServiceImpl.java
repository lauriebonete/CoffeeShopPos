package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.dao.ProductGroupDao;
import org.pos.coffee.service.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Laurie on 12/16/2015.
 */
@Service("productGroupService")
public class ProductGroupServiceImpl extends BaseCrudServiceImpl<ProductGroup> implements ProductGroupService {

    @Autowired
    private ProductGroupDao productGroupDao;

    @Override
    public List<ProductGroup> getProductGroups() {
        return productGroupDao.getProductGroups();
    }
}
