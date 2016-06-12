package org.evey.service.impl;

import org.apache.log4j.Logger;
import org.evey.bean.Person;
import org.evey.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * Created by kenji on 12/4/2015.
 */
@Service("personService")
public class PersonServiceImpl extends BaseCrudServiceImpl<Person> implements PersonService {

    private static Logger logger = Logger.getLogger(PersonServiceImpl.class);

}