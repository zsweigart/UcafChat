package com.zacharysweigart.ucafchatmanager;


import android.support.annotation.VisibleForTesting;

public class UacfChatManager {
    @VisibleForTesting
    static String userName;

    public static void login(String userName) {
        UacfChatManager.userName = userName;
    }

    public static void logout() {
        UacfChatManager.userName = null;
    }

    public static UacfChat getChat() {
        return new UacfChat(userName);
    }

    public static UacfChat getChat(String userName) {
        return new UacfChat(userName);
    }

    public static void setLoggingEnabled(boolean loggingEnabled) {
        UacfConnection.LOG = loggingEnabled;
    }
}
