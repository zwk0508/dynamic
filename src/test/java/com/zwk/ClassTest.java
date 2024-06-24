package com.zwk;

import com.zwk.loader.FileScriptLoader;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zwk
 * @version 1.0
 * @date 2024/6/24 11:35
 */

public class ClassTest {

    private DynamicConfig config;
    private Dynamic dynamic;

    @Before
    public void before() {
        config = new DynamicConfig("dynamic");
        config.setMode(DynamicMode.CLASS);

        dynamic = new Dynamic(config, new FileScriptLoader());
    }

    @Test
    public void test01() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(add(1, 2));
            System.out.println(sub(3, 2));
            Thread.sleep(2000);
        }
    }

    public int add(int a, int b) {
        try {
            return (int) dynamic.dynamicInvoke(a, b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int sub(int a, int b) {
        try {
            return (int) dynamic.dynamicInvoke(a, b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
