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

    @Override
    public User loadUserByUsername(String username) {
        String queryString = "SELECT obj FROM User obj where obj.username = :username";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
