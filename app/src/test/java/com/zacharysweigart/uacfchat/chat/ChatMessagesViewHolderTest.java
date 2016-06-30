package com.zacharysweigart.uacfchat.chat;


import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.zacharysweigart.uacfchat.R;
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
public class ChatMessagesViewHolderTest {
    private final String messageText = "Text";
    private final String userName = "Name";
    private final String id = "id";
    private final String error = "Error";
    private Message message;
    private ChatPresenter chatPresenter;
    private ChatMessagesAdapter.ChatMessagesViewHolder chatMessagesViewHolder;

    @Before
    public void setup() {
        chatPresenter = Mockito.mock(ChatPresenter.class);
        message = new Message(userName, messageText);
        chatMessagesViewHolder = new ChatMessagesAdapter.ChatMessagesViewHolder(LayoutInflater.from(RuntimeEnvironment.application).
                inflate(R.layout.item_chat_message, null, false));
    }

    @Test
    public void setContent_whenMessageIdNullAndNoError_setsTextAndColor() {
        chatMessagesViewHolder.setContent(message, chatPresenter);
        assertThat(chatMessagesViewHolder.chatMessage.getText().toString(), equalTo(messageText));
        assertThat(chatMessagesViewHolder.chatMessage.getTextColors().getDefaultColor(), equalTo(ContextCompat.getColor(RuntimeEnvironment.application, R.color.textDarkSecondary)));
    }

    @Test
    public void setContent_whenMessageIdAndNoError_setsTextAndColor() {
        message.setId(id);
        chatMessagesViewHolder.setContent(message, chatPresenter);
        assertThat(chatMessagesViewHolder.chatMessage.getText().toString(), equalTo(messageText));
        assertThat(chatMessagesViewHolder.chatMessage.getTextColors().getDefaultColor(), equalTo(ContextCompat.getColor(RuntimeEnvironment.application, R.color.textDarkPrimary)));
    }

    @Test
    public void setContent_whenMessageHasError_setsTextAndColor() {
        message.setError(error);
        chatMessagesViewHolder.setContent(message, chatPresenter);
        assertThat(chatMessagesViewHolder.chatError.getVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void clickViewHolder_whenMessageSet_callsResendOnPresenter() {
        message.setId(id);
        message.setError(error);
        chatMessagesViewHolder.setContent(message, chatPresenter);
        chatMessagesViewHolder.itemView.performClick();
        verify(chatPresenter).resendMessage(message);
    }
}
