package org.evey.service.impl;

import org.evey.bean.AuditLog;
import org.evey.service.AuditLogService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Laurie on 12/30/2015.
 */
@Service("auditLogService")
public class AuditLogServiceImpl extends BaseCrudServiceImpl<AuditLog> implements AuditLogService {
}

