package com.zacharysweigart.uacfchat;


import android.app.Application;

import com.facebook.FacebookSdk;
import com.zacharysweigart.ucafchatmanager.UacfChatManager;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(this);
        UacfChatManager.setLoggingEnabled(BuildConfig.DEBUG);
    }
}
