package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.User;
import org.pos.coffee.dao.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by kenji on 12/4/2015.
 */
@Repository("userDao")
public class UserDaoJpaImpl extends BaseEntityDaoJpaImpl<User,Long> implements UserDao {

}
