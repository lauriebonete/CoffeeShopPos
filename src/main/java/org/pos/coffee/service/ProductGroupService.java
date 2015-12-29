package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.ProductGroup;

import java.util.List;

/**
 * Created by Laurie on 12/16/2015.
 */
public interface ProductGroupService extends BaseCrudService<ProductGroup> {
    public List<ProductGroup> getProductGroups();
}
