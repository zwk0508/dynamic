package com.zwk;

import com.zwk.invoker.Invoker;
import com.zwk.loader.ScriptLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 9:50
 */

public class Dynamic {
    private DynamicConfig config;
    private ScriptLoader loader;

    private final Map<Key, Invoker> invokerMap = new HashMap<>();

    public Dynamic(DynamicConfig config, ScriptLoader scriptLoader) {
        this.config = config;
        this.loader = scriptLoader;
        loader.setConfig(config);
    }


    public Object dynamicInvoke(Object... args) throws Exception {
        StackTraceElement element = new Throwable().getStackTrace()[1];
        String clazzName = element.getClassName();
        String methodName = element.getMethodName();

        Key key = new Key(clazzName, methodName, config.getMode());

        if (invokerMap.containsKey(key)) {
            if (loader.isUpdated(clazzName, methodName)) {
                invokerMap.remove(key);
                return loadAndInvoke(clazzName, methodName, key, args);
            } else {
                return invokerMap.get(key).invoke(methodName, args);
            }
        } else {
            return loadAndInvoke(clazzName, methodName, key, args);
        }

    }


    private Object loadAndInvoke(String clazzName, String methodName, Key key, Object args) {
        Invoker invoker;
        synchronized (invokerMap) {
            if (invokerMap.containsKey(key)) {
                invoker = invokerMap.get(key);
            } else {
                invoker = loader.load(clazzName, methodName);
                invokerMap.put(key, invoker);
            }
        }

        return invoker.invoke(methodName, args);
    }


    private static class Key {
        String className;
        String methodName;
        DynamicMode mode;

        public Key(String className, String methodName, DynamicMode mode) {
            this.className = className;
            this.methodName = methodName;
            this.mode = mode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            if (mode == DynamicMode.CLASS) {
                return Objects.equals(className, key.className);
            } else {
                return Objects.equals(className, key.className) && Objects.equals(methodName, key.methodName);
            }
        }

        @Override
        public int hashCode() {
            if (mode == DynamicMode.CLASS) {
                return Objects.hash(className);
            } else {
                return Objects.hash(className, methodName);
            }
        }
    }

}
