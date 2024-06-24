package com.zwk.invoker;

import groovy.lang.Script;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 10:59
 */

public class ScriptInvoker implements Invoker {
    private Script script;

    public ScriptInvoker(Script script) {
        this.script = script;
    }

    @Override
    public Object invoke(Object... args) {
        script.getBinding().setVariable("args", args[1]);
        return script.run();
    }
}
