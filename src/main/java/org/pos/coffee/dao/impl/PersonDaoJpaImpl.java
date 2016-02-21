package org.pos.coffee.dao.impl;

import org.evey.bean.User;
import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Person;
import org.pos.coffee.dao.PersonDao;
import org.pos.coffee.dao.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by kenji on 12/4/2015.
 */
@Repository("personDao")
public class PersonDaoJpaImpl extends BaseEntityDaoJpaImpl<Person,Long> implements PersonDao {

}
