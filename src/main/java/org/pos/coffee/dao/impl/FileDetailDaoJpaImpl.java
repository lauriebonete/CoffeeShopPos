package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.dao.FileDetailDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 11/24/2015.
 */
@Repository("fileDetailDao")
public class FileDetailDaoJpaImpl extends BaseEntityDaoJpaImpl<FileDetail,Long> implements FileDetailDao {
}
