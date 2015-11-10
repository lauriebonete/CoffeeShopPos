package org.pos.coffee.utility;

import org.pos.coffee.bean.BaseEntity;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/10/2015.
 */
public class CrudUtil {

    public static Map<String,Object> getAllFields(BaseEntity lookFor, Class<?> lookForClass) throws Exception {
        Map<String,Object> properties = new HashMap<String,Object>();
        List<String> whereClauseCriteria = new ArrayList<String>();
        for(Field field : lookForClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(lookFor) != null && !"".equals(field.get(lookFor))) {
                if ( (!Modifier.isTransient(field.getModifiers())) &&
                        (!Modifier.isVolatile(field.getModifiers())) &&
                        (!Modifier.isStatic(field.getModifiers())) &&
                        (!field.isAnnotationPresent(Transient.class)) ) {
                   properties.put(field.getName(),field.getType());
                }
            }
        }
        if(lookForClass.getSuperclass()!=null){
            properties.putAll(getAllFields(lookFor, lookForClass.getSuperclass()));
        }

        return properties;
    }


    public static Object getValue(BaseEntity entity, String property) throws Exception{
        Object value = null;
        Method getterMethod = entity.getClass().getMethod(NamingUtil.buildGetterMethodName(property));
        value = getterMethod.invoke(entity);
        return value;
    }
}
