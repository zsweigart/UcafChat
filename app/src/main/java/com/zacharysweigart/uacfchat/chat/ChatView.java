package com.zacharysweigart.uacfchat.chat;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.ucafchatmanager.model.Message;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatView extends RelativeLayout {

    @BindView(R.id.chat_send_text)
    TextView chatSend;
    @BindView(R.id.chat_edit_text)
    EditText chatMessageText;
    @BindView(R.id.chat_messages_recycler)
    RecyclerView messagesRecycler;

    private ChatPresenter chatPresenter;
    private ChatMessagesAdapter chatMessagesAdapter;

    public ChatView(Context context) {
        super(context);
        initialize(context);
    }

    public void setChatPresenter(ChatPresenter chatPresenter) {
        this.chatPresenter = chatPresenter;
    }

    public void messageAdded(Message message) {
        chatMessagesAdapter.addMessage(message);
    }

    public void messageRemoved(Message message) {
        chatMessagesAdapter.removeMessage(message);
    }

    public void messageRefreshFailure() {
        //TODO Show error
    }

    public void messageSent(Message message) {
        chatMessagesAdapter.sendSuccess(message);
    }

    public void messageSendFailure(Message message) {
        chatMessagesAdapter.sendFailure(message);
    }

    @OnClick(R.id.chat_send_text)
    public void sendMessage() {
        Message message = new Message(chatMessageText.getText().toString());
        chatMessagesAdapter.addMessage(0, message);
        chatPresenter.sendMessage(message);
        chatMessageText.setText("");
        messagesRecycler.smoothScrollToPosition(0);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.content_chat, this);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setReverseLayout(true);
        messagesRecycler.setLayoutManager(linearLayoutManager);
        chatMessagesAdapter = new ChatMessagesAdapter(chatPresenter);
        messagesRecycler.setAdapter(chatMessagesAdapter);
    }
}
