package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.ProductGroup;

import java.util.List;

/**
 * Created by Laurie on 12/16/2015.
 */
public interface ProductGroupDao extends BaseEntityDao<ProductGroup,Long> {
    public List<ProductGroup> getProductGroups();
}
