package org.pos.coffee.web.json;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by Laurie on 11/13/2015.
 */
public class ScopeInjectingReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ScopeInjectingReturnValueHandler(
            HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return delegate.supportsReturnType(methodParameter);
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        Class<? extends Scope.Base> viewClass = getDeclaredViewClass(methodParameter);
        if (viewClass != null) {
            o = wrapResult(o, viewClass);
        }

        delegate.handleReturnValue(o, methodParameter, modelAndViewContainer,
                nativeWebRequest);
    }

    private Class<? extends Scope.Base> getDeclaredViewClass(
            MethodParameter returnType) {
        ResponseScope annotation = returnType
                .getMethodAnnotation(ResponseScope.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return null;
        }
    }

    private Object wrapResult(Object result, Class<? extends Scope.Base> viewClass) {
        PojoScope response = new PojoScope(result, viewClass);
        return response;
    }
}
