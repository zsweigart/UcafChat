package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import retrofit2.Call;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class UacfChatTest {
    private static final String userName = "Test";
    private static final String messageText = "Test Message";
    private Message message;
    private UacfConnection uacfConnection;
    private UacfChat uacfChat;
    private UacfChat.MessageSendListener messageSendListener;
    private UacfChat.MessageRefreshListener messageRefreshListener;
    private Call call;

    @Before
    public void setup() {
        message = new Message(userName, messageText);
        uacfChat = new UacfChat(userName);
        messageSendListener = Mockito.mock(UacfChat.MessageSendListener.class);
        messageRefreshListener = Mockito.mock(UacfChat.MessageRefreshListener.class);
        uacfConnection = Mockito.mock(UacfConnection.class);
        call = Mockito.mock(Call.class);
    }

    @Test
    public void startChat_setsConnection() {
        uacfChat.startChat();
        assertThat(uacfChat.uacfConnection, notNullValue());
    }

    @Test
    public void stopChat_whenChatStarted_clearsConnection() {
        uacfChat.startChat();
        assertThat(uacfChat.uacfConnection, notNullValue());
        uacfChat.stopChat();
        assertThat(uacfChat.uacfConnection, nullValue());
    }


}
