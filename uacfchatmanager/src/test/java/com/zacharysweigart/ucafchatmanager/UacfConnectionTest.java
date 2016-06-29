package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UacfConnectionTest {
    private static final String userName = "Test";
    private static final String messageText = "Test Message";
    private Message message;
    private UacfConnection uacfConnection;

    @Before
    public void setup() {
        message = new Message(userName, messageText);
        uacfConnection = new UacfConnection();
    }

    @Test
    public void uacfConnection_whenLogSetToFalse_setRetrofitLoggingOff() {
        UacfChatManager.setLoggingEnabled(false);
        uacfConnection = new UacfConnection();
        assertThat(uacfConnection.interceptor.getLevel(), equalTo(HttpLoggingInterceptor.Level.NONE));
    }

    @Test
    public void uacfConnection_whenLogSetToTrue_setRetrofitLoggingOn() {
        UacfChatManager.setLoggingEnabled(true);
        uacfConnection = new UacfConnection();
        assertThat(uacfConnection.interceptor.getLevel(), equalTo(HttpLoggingInterceptor.Level.BODY));
    }
}
