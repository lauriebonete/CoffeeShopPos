package org.evey.utility;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurie on 11/16/2015.
 */
public class CrudUtil {

    private static final Logger _log = Logger.getLogger(CrudUtil.class);

    public static Object setProperties(Object target, Object source){
        List<Field> fieldList = fieldList = getFields(source.getClass(),source);
        for(Field field:fieldList){
            try {
                Object value = field.get(source);
                BeanUtils.setProperty(target,field.getName(),value);
            } catch (Exception e){
                _log.error(e.getMessage());
            }
        }
        return target;
    }

    private static List<Field> getFields(Class<?> type, Object source) {
        List<Field> returnFieldList = new ArrayList<>();
        for(Field field: type.getDeclaredFields()){
            field.setAccessible(true);
            if((!Modifier.isTransient(field.getModifiers())) &&
                (!Modifier.isVolatile(field.getModifiers())) &&
                (!Modifier.isStatic(field.getModifiers())) &&
                (!field.isAnnotationPresent(Transient.class))){
                returnFieldList.add(field);
            }
        }

        if(type.getSuperclass() != null){
            returnFieldList.addAll(getFields(type.getSuperclass(),source));
        }

        return returnFieldList;
    }
}
