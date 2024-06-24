package com.zwk.loader;

import com.zwk.DynamicConfig;
import com.zwk.invoker.Invoker;

public interface ScriptLoader {
    void setConfig(DynamicConfig config);

    boolean isUpdated(String className, String methodName);

    Invoker load(String className, String methodName);

}
