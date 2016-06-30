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
public class ChatMessagesAdapterTest {
    private final String messageText = "Text";
    private final String userName = "Name";
    private Message completeMessage;
    private Message message;
    private ChatMessagesAdapter chatMessagesAdapter;
    private ChatPresenter chatPresenter;

    @Before
    public void setup() {
        chatPresenter = Mockito.mock(ChatPresenter.class);
        chatMessagesAdapter = new ChatMessagesAdapter(chatPresenter);
        message = Mockito.mock(Message.class);
        completeMessage = new Message(userName, messageText);
    }

    @Test
    public void addMessage_whenNotGivenPosition_addsMessageToFront() {
        chatMessagesAdapter.addMessage(message);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(message));
        chatMessagesAdapter.addMessage(completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(completeMessage));
    }

    @Test
    public void addMessage_whenGivenPosition_addsMessageAtPosition() {
        chatMessagesAdapter.addMessage(message);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(message));
        chatMessagesAdapter.addMessage(1, completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(1), equalTo(completeMessage));
    }

    @Test
    public void removeMessage_whenGivenMessage_removesMessage() {
        chatMessagesAdapter.addMessage(message);
        chatMessagesAdapter.addMessage(completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(completeMessage));
        assertThat(chatMessagesAdapter.messageList.get(1), equalTo(message));
        chatMessagesAdapter.removeMessage(completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(message));
        assertThat(chatMessagesAdapter.messageList.size(), equalTo(1));
    }

    @Test
    public void sendSuccess_whenGivenMessage_removesMessage() {
        chatMessagesAdapter.addMessage(message);
        chatMessagesAdapter.addMessage(completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(completeMessage));
        assertThat(chatMessagesAdapter.messageList.get(1), equalTo(message));
        chatMessagesAdapter.removeMessage(completeMessage);
        assertThat(chatMessagesAdapter.messageList.get(0), equalTo(message));
        assertThat(chatMessagesAdapter.messageList.size(), equalTo(1));
    }
}
