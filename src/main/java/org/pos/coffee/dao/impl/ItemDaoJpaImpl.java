package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.dao.ItemDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 11/16/2015.
 */
@Repository("itemDao")
public class ItemDaoJpaImpl extends BaseEntityDaoJpaImpl<Item, Long> implements ItemDao {
}
