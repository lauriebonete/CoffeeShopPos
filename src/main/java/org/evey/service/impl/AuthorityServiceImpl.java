package org.evey.service.impl;

import org.evey.bean.Authority;
import org.evey.dao.AuthorityDao;
import org.evey.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 2/23/2016.
 */
@Service("authorityService")
public class AuthorityServiceImpl extends BaseCrudServiceImpl<Authority> implements AuthorityService {
}
