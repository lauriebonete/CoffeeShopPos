package org.evey.dao.impl;

import org.evey.bean.AuditLog;
import org.evey.dao.AuditLogDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 12/30/2015.
 */
@Repository("auditLogDao")
public class AuditLogDaoJpaImpl extends BaseEntityDaoJpaImpl<AuditLog,Long> implements AuditLogDao {
}
