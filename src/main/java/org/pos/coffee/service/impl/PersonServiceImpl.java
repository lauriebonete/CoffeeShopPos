package org.pos.coffee.service.impl;

import org.apache.log4j.Logger;
import org.evey.bean.User;
import org.evey.security.SessionUser;
import org.evey.service.impl.BaseCrudServiceImpl;
import org.evey.utility.SecurityUtil;
import org.pos.coffee.bean.Person;
import org.pos.coffee.dao.UserDao;
import org.pos.coffee.service.PersonService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kenji on 12/4/2015.
 */
@Service("personService")
public class PersonServiceImpl extends BaseCrudServiceImpl<Person> implements PersonService {

    private static Logger logger = Logger.getLogger(PersonServiceImpl.class);

}