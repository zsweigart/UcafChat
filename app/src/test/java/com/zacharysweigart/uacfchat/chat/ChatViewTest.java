package com.zacharysweigart.uacfchat.chat;


import com.zacharysweigart.uacfchat.testframework.UacfRobolectricTestRunner;
import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(UacfRobolectricTestRunner.class)
public class ChatViewTest {
    private static final String messageText = "Message";
    private ChatView chatView;
    private ChatPresenter chatPresenter;

    @Before
    public void setup() {
        chatPresenter = Mockito.mock(ChatPresenter.class);
        chatView = new ChatView(RuntimeEnvironment.application);
        chatView.setChatPresenter(chatPresenter);
    }

    @Test
    public void clickingSendButton_whenTextInField_callsSendMessageOnPresenter() {
        chatView.chatMessageText.setText(messageText);
        chatView.chatSend.performClick();

        verify(chatPresenter).sendMessage(new Message(messageText));
        assertThat(chatView.chatMessageText.getText().toString(), equalTo(""));
    }
}
