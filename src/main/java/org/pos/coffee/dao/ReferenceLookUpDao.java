package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.ReferenceLookUp;

import java.util.List;

/**
 * Created by Laurie on 11/5/2015.
 */
public interface ReferenceLookUpDao extends BaseEntityDao<ReferenceLookUp,Long> {

    public List<String> getAllCategory();

    public List<ReferenceLookUp> getReferenceLookUpByCategory(String category);
    public List<ReferenceLookUp> getActiveReferenceLookUpByCategory(String category);

    public ReferenceLookUp getReferenceLookUpByKey(String key);
}
