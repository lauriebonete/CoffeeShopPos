package org.evey.utility;

/**
 * Created by Laurie on 11/5/2015.
 */
public class StringUtil {

    public static boolean isEmpty(String obj) {
        if ((obj==null) || (obj.trim().length()==0))
            return true;
        else
            return false;
    }
}
