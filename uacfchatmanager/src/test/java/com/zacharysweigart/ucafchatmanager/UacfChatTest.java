package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import retrofit2.Call;

import static org.hamcrest.CoreMatchers.equalTo;
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
    public void constructor_setsConnection() {
        assertThat(uacfChat.uacfConnection, notNullValue());
    }

    @Test
    public void startChat_whenConstructed_setsCancelledToFalse() {
        uacfChat.startChat();
        assertThat(uacfChat.canceled, equalTo(false));
    }

    @Test
    public void stopChat_whenStarted_setsCancelledToTrue() {
        uacfChat.startChat();
        uacfChat.stopChat();
        assertThat(uacfChat.canceled, equalTo(true));
    }

}
