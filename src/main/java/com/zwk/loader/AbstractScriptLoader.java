package com.zwk.loader;

import com.zwk.DynamicConfig;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 10:00
 */

public abstract class AbstractScriptLoader implements ScriptLoader {

    private DynamicConfig config;

    @Override
    public void setConfig(DynamicConfig config) {
        this.config = config;
    }

    public DynamicConfig getConfig() {
        return config;
    }
}
