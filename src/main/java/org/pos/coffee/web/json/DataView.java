package org.pos.coffee.web.json;

/**
 * Created by Laurie on 11/13/2015.
 */
public interface DataView {
    boolean hasView();
    Class<? extends Scope.Base> getView();
    Object getData();
}
