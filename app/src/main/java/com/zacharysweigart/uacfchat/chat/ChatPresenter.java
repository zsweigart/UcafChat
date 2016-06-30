package com.zacharysweigart.uacfchat.chat;


import com.zacharysweigart.uacfchat.model.Chat;
import com.zacharysweigart.ucafchatmanager.UacfChat;
import com.zacharysweigart.ucafchatmanager.UacfChatManager;
import com.zacharysweigart.ucafchatmanager.model.Message;

public class ChatPresenter {

    private ChatView chatView;
    private ChatNavigator chatNavigator;
    private Chat chat;
    private UacfChat uacfChat;

    public ChatPresenter() {
    }

    public void initialize(final ChatView chatView, ChatNavigator chatNavigator, Chat chat) {
        this.chatView = chatView;
        this.chatNavigator = chatNavigator;
        this.chat = chat;

        uacfChat = UacfChatManager.getChat(chat.getUserName());
        uacfChat.setMessageRefreshListener(new UacfChat.MessageRefreshListener() {
            @Override
            public void messageAdded(Message message) {
                chatView.messageAdded(message);
            }

            @Override
            public void messageRemoved(Message message) {
                chatView.messageRemoved(message);
            }

            @Override
            public void messageRefreshFailure() {
                chatView.messageRefreshFailure();
            }
        });

        uacfChat.setMessageSendListener(new UacfChat.MessageSendListener() {
            @Override
            public void messageSent(Message message) {
                chatView.messageSent(message);
            }

            @Override
            public void messageSendFailure(Message message) {
                chatView.messageSendFailure(message);
            }
        });
    }

    public void onStart() {
        uacfChat.startChat();
        uacfChat.refreshMessages();
    }

    public void onStop() {
        uacfChat.stopChat();
    }

    public void refreshMessages() {
        uacfChat.refreshMessages();
    }

    public void sendMessage(Message message) {
        message.setUsername(chat.getUserName());
        uacfChat.sendMessage(message);
    }

    public void resendMessage(Message message) {
        message.setError(null);
        uacfChat.sendMessage(message);
    }
}
