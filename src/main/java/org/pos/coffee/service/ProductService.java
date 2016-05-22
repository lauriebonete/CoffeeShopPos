package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.Product;

/**
 * Created by Laurie on 11/16/2015.
 */
public interface ProductService extends BaseCrudService<Product> {
    public Boolean validateProductCode(String productCode);
    public Boolean validateProductCode(String productCode,Long id);
}
