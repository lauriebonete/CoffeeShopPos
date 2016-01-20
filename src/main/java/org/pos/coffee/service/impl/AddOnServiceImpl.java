package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.AddOn;
import org.pos.coffee.service.AddOnService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/20/2016.
 */
@Service("addOnService")
public class AddOnServiceImpl extends BaseCrudServiceImpl<AddOn> implements AddOnService {
}
