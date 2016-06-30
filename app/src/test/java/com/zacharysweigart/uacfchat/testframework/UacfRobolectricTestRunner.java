package com.zacharysweigart.uacfchat.testframework;

import com.zacharysweigart.ucafchatmanager.BuildConfig;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

public class UacfRobolectricTestRunner extends RobolectricGradleTestRunner {

    private static final int MAX_SDK_LEVEL = 21;

    private int[] sdk = {21};

    public UacfRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public Config getConfig(Method method) {
        Config config = super.getConfig(method);
        config = new Config.Implementation(ensureSdkLevel(config.sdk()),
                config.manifest(),
                config.qualifiers(),
                "com.zacharysweigart.uacfchat",
                config.resourceDir(),
                config.assetDir(),
                config.shadows(),
                config.application(),
                config.libraries(),
                ensureBuildConfig(config.constants()));


        return config;
    }

    private Class<?> ensureBuildConfig(Class<?> constants) {
        if (constants == Void.class) return BuildConfig.class;
        return constants;
    }

    private int[] ensureSdkLevel(int[] sdkLevel) {
        for(int i = 0; i < sdkLevel.length; i++) {
            if (sdkLevel[i] > MAX_SDK_LEVEL) {
                sdkLevel[i] = MAX_SDK_LEVEL;
            }

            if (sdkLevel[i] <= 0) {
                sdkLevel[i] = MAX_SDK_LEVEL;
            }
        }

        if(sdkLevel.length == 0) {
            sdkLevel = sdk;
        }

        return sdkLevel;
    }
}
