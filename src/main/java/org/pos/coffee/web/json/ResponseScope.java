package org.pos.coffee.web.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Laurie on 11/13/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseScope {
    public Class<? extends Scope.Base> value();
}
