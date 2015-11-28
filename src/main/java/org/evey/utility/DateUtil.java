package org.evey.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Laurie on 11/27/2015.
 */
public class DateUtil {

    public static Date stringToDate(String strDate, String format) throws ParseException {
        if (StringUtil.isEmpty(strDate))
            return null;
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        return dtFormatter.parse(strDate.trim());
    }

    public static String dateToString(Date obj, String format) {
        if (obj == null)
            return "";
        SimpleDateFormat dtFormatter = new SimpleDateFormat(format);
        return dtFormatter.format(obj);
    }

    public static String convertShortDate(Date obj) {
        return dateToString(obj, "yyyyMMdd");
    }

    public static Date convertShortDate(String str) throws ParseException {
        return stringToDate(str, "yyyyMMdd");
    }
}
