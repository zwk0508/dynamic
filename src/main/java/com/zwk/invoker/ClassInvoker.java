package com.zwk.invoker;

import groovy.lang.GroovyObject;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 10:50
 */

public class ClassInvoker implements Invoker {
    private GroovyObject object;

    public ClassInvoker(GroovyObject object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object... args) {
        return object.invokeMethod((String) args[0], args[1]);
    }
}
