package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.Product;

/**
 * Created by Laurie on 11/16/2015.
 */
public interface ProductDao extends BaseEntityDao<Product,Long> {
    public Boolean validateProductCode(String productCode,Long id);
}
