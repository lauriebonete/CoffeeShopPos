package org.pos.coffee.web.json;

/**
 * Created by Laurie on 11/13/2015.
 */
public class PojoScope implements DataView {

    private final Object data;
    private final Class<? extends Scope.Base> view;

    public PojoScope(Object data, Class<? extends Scope.Base> view) {
        super();
        this.data = data;
        this.view = view;
    }

    @Override
    public boolean hasView() {
        return true;
    }

    @Override
    public Class<? extends Scope.Base> getView() {
        return view;
    }

    @Override
    public Object getData() {
        return data;
    }
}
