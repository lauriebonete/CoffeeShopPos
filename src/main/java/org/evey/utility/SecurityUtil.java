package org.evey.utility;

import org.apache.log4j.Logger;
import org.evey.security.SessionUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Laurie on 1/19/2016.
 */
public class SecurityUtil {

    private static Logger _log = Logger.getLogger(SecurityUtil.class);

    public static SessionUser getSessionUser(){
        try {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(object instanceof SessionUser){
                return ((SessionUser) object);
            }
        } catch (NullPointerException npe){
            _log.warn("No Security Context Found!");
        } catch (Exception e){
            _log.error(e.getMessage());
        }

        return null;
    }
}
