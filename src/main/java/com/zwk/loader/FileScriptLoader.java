package com.zwk.loader;

import com.zwk.DynamicConfig;
import com.zwk.DynamicMode;
import com.zwk.invoker.ClassInvoker;
import com.zwk.invoker.Invoker;
import com.zwk.invoker.ScriptInvoker;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 10:00
 */

public class FileScriptLoader extends AbstractScriptLoader {

    private final Map<String, FileInfo> fileInfoMap = new HashMap<>();

    @Override
    public boolean isUpdated(String className, String methodName) {
        DynamicConfig config = getConfig();
        DynamicMode mode = config.getMode();
        String key = className;
        if (mode == DynamicMode.SCRIPT) {
            key += "." + methodName;
        }
        key += config.getSuffix();
        if (fileInfoMap.containsKey(key)) {
            FileInfo fileInfo = fileInfoMap.get(key);
            File file = new File(config.getBasePath(), key);
            long lastModified = file.lastModified();

            if (lastModified > fileInfo.lastUpdate) {
                fileInfoMap.put(key, new FileInfo(key, lastModified));
                return true;
            }
            return false;
        } else {
            fileInfoMap.put(key, new FileInfo(key, new File(key).lastModified()));
        }
        return true;
    }

    @Override
    public Invoker load(String className, String methodName) {
        DynamicConfig config = getConfig();
        String fileName;
        boolean isScriptMode = config.getMode() == DynamicMode.SCRIPT;
        if (isScriptMode) {
            fileName = className + "." + methodName + config.getSuffix();
        } else {
            fileName = className + config.getSuffix();
        }

        File file = new File(config.getBasePath(), fileName);
        GroovyClassLoader loader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
        Class clazz = null;
        Object obj = null;
        try {
            clazz = loader.parseClass(file);
            obj = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (isScriptMode) {
            if (!Script.class.isAssignableFrom(clazz)) {
                throw new RuntimeException("mode SCRIPT,but not script file");
            }
            return new ScriptInvoker((Script) obj);
        }

        return new ClassInvoker((GroovyObject) obj);
    }

    private static class FileInfo {
        String key;

        long lastUpdate;

        public FileInfo(String key, long lastUpdate) {
            this.key = key;
            this.lastUpdate = lastUpdate;
        }
    }
}
