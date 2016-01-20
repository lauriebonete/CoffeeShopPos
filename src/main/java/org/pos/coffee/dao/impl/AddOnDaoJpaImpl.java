package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.AddOn;
import org.pos.coffee.dao.AddOnDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 1/20/2016.
 */
@Repository("addOnDao")
public class AddOnDaoJpaImpl extends BaseEntityDaoJpaImpl<AddOn,Long> implements AddOnDao {
}
