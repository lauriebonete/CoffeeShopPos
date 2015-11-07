package org.pos.coffee.service;

import org.pos.coffee.bean.ReferenceLookUp;

import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface ReferenceLookUpService extends BaseCrudService<ReferenceLookUp> {

    public List<String> getAllCategory();
}
