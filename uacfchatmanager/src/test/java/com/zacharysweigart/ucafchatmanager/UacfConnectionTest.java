package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

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
        uacfConnection.uacfApi = Mockito.mock(UacfApi.class);
        message = Mockito.mock(Message.class);
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

    @Test
    public void getMessages_whenGivenUsername_callsApi() {
        uacfConnection.getMessages(userName);
        verify(uacfConnection.uacfApi).getMessages(userName);
    }

    @Test
    public void sendMessage_whenGivenMessage_callsApi() {
        uacfConnection.postMessage(message);
        verify(uacfConnection.uacfApi).sendMessage(message);
    }
}
