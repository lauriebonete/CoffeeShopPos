package org.evey.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.evey.annotation.Auditable;
import org.evey.bean.AuditLog;
import org.evey.bean.BaseEntity;
import org.evey.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Laurie on 12/29/2015.
 */
@Aspect
@Component("auditLogAspect")
public class AuditLogAspect {

    private static Logger logger = Logger.getLogger(AuditLogAspect.class);

    @Autowired
    private AuditLogService auditLogService;

    @PostConstruct
    public void init(){
        logger.warn("Initiating audit log aspect");
    }

    @After("execution(* org.evey.service.impl.BaseCrudServiceImpl.save(..))")
    public void logAfter(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Object obj = (Object) args[0];
        if(obj != null && obj.getClass().isAnnotationPresent(Auditable.class) && obj instanceof BaseEntity){
            logger.warn("Saving audit log");
            BaseEntity entity = (BaseEntity) obj;
            AuditLog log = new AuditLog();
            /*log.setUserId();
            log.setUsername();*/
            log.setEntityClass(obj.getClass());
            log.setEntityId(entity.getId());
            log.setAction("Saved "+obj.getClass().getSimpleName());
            log.setMessage("");
            auditLogService.save(log);
        }
    }
}
