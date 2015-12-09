package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.service.FileDetailService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/24/2015.
 */
@Service("fileDetailService")
public class FileDetailServiceImpl extends BaseCrudServiceImpl<FileDetail> implements FileDetailService {
}
