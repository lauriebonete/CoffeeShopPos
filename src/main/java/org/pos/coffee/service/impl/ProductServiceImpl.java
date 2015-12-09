package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Product;
import org.pos.coffee.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/16/2015.
 */
@Service("productService")
public class ProductServiceImpl extends BaseCrudServiceImpl<Product> implements ProductService {
}
