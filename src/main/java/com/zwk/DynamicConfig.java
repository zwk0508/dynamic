package com.zwk;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 9:51
 */

public class DynamicConfig {
    private static final String DEFAULT_BASE_PATH = "dynamic";
    private static final String DEFAULT_SUFFIX = ".groovy";

    private String basePath;
    private String suffix;

    private DynamicMode mode = DynamicMode.SCRIPT;

    public DynamicConfig() {
        this(DEFAULT_BASE_PATH, DEFAULT_SUFFIX);
    }

    public DynamicConfig(String basePath) {
        this(basePath, DEFAULT_SUFFIX);
    }

    public DynamicConfig(String basePath, String suffix) {
        this.basePath = basePath;
        this.suffix = suffix;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public DynamicMode getMode() {
        return mode;
    }

    public void setMode(DynamicMode mode) {
        this.mode = mode;
    }
}
