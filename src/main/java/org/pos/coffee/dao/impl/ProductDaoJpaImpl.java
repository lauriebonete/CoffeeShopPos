package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.Product;
import org.pos.coffee.dao.ProductDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 11/11/2015.
 */
@Repository("productDao")
public class ProductDaoJpaImpl extends BaseEntityDaoJpaImpl<Product, Long> implements ProductDao {
}
