package com.zacharysweigart.ucafchatmanager;


import android.support.annotation.VisibleForTesting;

import com.zacharysweigart.ucafchatmanager.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UacfChat {

    @VisibleForTesting
    UacfConnection uacfConnection;
    @VisibleForTesting
    String userName;
    @VisibleForTesting
    boolean canceled = false;

    private OrderedMessageList orderedMessageList;
    private MessageRefreshListener messageRefreshListener;
    private MessageSendListener messageSendListener;

    public UacfChat(String userName) {
        this.userName = userName;
        this.orderedMessageList = new OrderedMessageList();
        uacfConnection = new UacfConnection();
        setMessageListListener();
    }

    public void startChat() {
        canceled = false;
    }

    public void stopChat() {
        canceled = true;
    }

    public void setMessageRefreshListener(MessageRefreshListener messageRefreshListener) {
        this.messageRefreshListener = messageRefreshListener;
    }

    public void setMessageSendListener(MessageSendListener messageSendListener) {
        this.messageSendListener = messageSendListener;
    }

    public void sendMessage(final Message message) {
        orderedMessageList.addMessage(message);
        uacfConnection.postMessage(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
                    message.setId(response.body().getId());
                    if(!canceled && messageSendListener != null) {
                        messageSendListener.messageSent(message);
                    }
                } else {
                    message.setError(response.errorBody().toString());
                    if(!canceled && messageSendListener != null) {
                        messageSendListener.messageSendFailure(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                message.setError(t.getMessage());
                if(!canceled && messageSendListener != null) {
                    messageSendListener.messageSendFailure(message);
                }
            }
        });
    }

    public void refreshMessages() {
        uacfConnection.getMessages(userName).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                orderedMessageList.updateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                if(!canceled && messageRefreshListener != null) {
                    messageRefreshListener.messageRefreshFailure();
                }
            }
        });
    }

    private void setMessageListListener() {
        orderedMessageList.setMessageListListener(new OrderedMessageList.MessageListListener() {
            @Override
            public void messageAdded(Message message) {
                if(!canceled && messageRefreshListener != null) {
                    messageRefreshListener.messageAdded(message);
                }
            }

            @Override
            public void messageRemoved(Message message) {
                if(!canceled && messageRefreshListener != null) {
                    messageRefreshListener.messageRemoved(message);
                }
            }
        });
    }

    public interface MessageSendListener {
        void messageSent(Message message);
        void messageSendFailure(Message message);
    }

    public interface MessageRefreshListener {
        void messageAdded(Message message);
        void messageRemoved(Message message);
        void messageRefreshFailure();
    }
}
