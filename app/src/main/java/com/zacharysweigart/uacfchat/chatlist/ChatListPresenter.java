package com.zacharysweigart.uacfchat.chatlist;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zacharysweigart.uacfchat.model.Chat;

public class ChatListPresenter {
    private ChatListView chatListView;
    private ChatListNavigator chatListNavigator;
    private DatabaseReference chatListRef;

    public ChatListPresenter() {
    }

    public void initialize(final ChatListView chatListView, ChatListNavigator chatListNavigator) {
        this.chatListView = chatListView;
        this.chatListNavigator = chatListNavigator;
        chatListRef = FirebaseDatabase.getInstance().getReference()
                .child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        chatListView.updateDataList(chatListRef.orderByChild("userName"));
    }

    public void openChat(Chat chat) {
        chatListNavigator.openChatActivity(chat);
    }
}
