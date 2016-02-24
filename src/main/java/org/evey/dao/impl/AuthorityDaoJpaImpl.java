package org.evey.dao.impl;

import org.evey.bean.Authority;
import org.evey.dao.AuthorityDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurie on 2/23/2016.
 */
@Repository("authorityDao")
public class AuthorityDaoJpaImpl extends BaseEntityDaoJpaImpl<Authority,Long> implements AuthorityDao {
}
