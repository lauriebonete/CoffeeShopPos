package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.evey.bean.Person;
import org.pos.coffee.dao.PersonDao;
import org.springframework.stereotype.Repository;

/**
 * Created by kenji on 12/4/2015.
 */
@Repository("personDao")
public class PersonDaoJpaImpl extends BaseEntityDaoJpaImpl<Person,Long> implements PersonDao {

}
