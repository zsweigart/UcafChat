package com.zacharysweigart.ucafchatmanager;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UacfChatTest {
    private static final String userName = "Test";
    private UacfChat uacfChat;

    @Before
    public void setup() {
        uacfChat = new UacfChat(userName);
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
