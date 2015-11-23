package org.pos.coffee.utility;

/**
 * Created by Laurie on 11/5/2015.
 */
public class NamingUtil {

    public static String toAttributeName(String name) {

        if (StringUtil.isEmpty(name))
            return "";

        if (name.length()>=2) {
            return name.substring(0,1).toLowerCase() + name.substring(1);
        } else {
            return name.toLowerCase();
        }
    }

    public static  String upperCaseFirstChar(String string){
        if(StringUtil.isEmpty(string)){
            return "";
        }
        if(string.length()>=2){
            return string.substring(0,1).toUpperCase()+string.substring(1);
        } else {
            return string.toUpperCase();
        }
    }

    public static String toGetterName(String name){
        if(!StringUtil.isEmpty(name)){
            return "get"+name.substring(0,1).toUpperCase() + name.substring(1);
        }
        return "";
    }

    public static String toCreatePath(String name) {
        if (StringUtil.isEmpty(name))
            return "";
        StringBuffer buffer = new StringBuffer();
        int startIndex = 0;
        for (int i=0; i<name.length();i++) {
            if (name.charAt(i) >= 'A' && name.charAt(i)<= 'Z') {
                if (startIndex!=0)
                    buffer.append("-");
                buffer.append(name.substring(startIndex, i).toLowerCase());
                startIndex = i;
            }
        }
        if (startIndex<name.length()) {
            if (startIndex!=0)
                buffer.append("-");
            buffer.append(name.substring(startIndex).toLowerCase());
        }
        return buffer.toString();
    }
}
