package com.zacharysweigart.uacfchat.chatlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.base.BaseActivity;
import com.zacharysweigart.uacfchat.chat.ChatActivity;
import com.zacharysweigart.uacfchat.friendlist.FriendListActivity;
import com.zacharysweigart.uacfchat.login.LoginActivity;
import com.zacharysweigart.uacfchat.model.Chat;
import com.zacharysweigart.ucafchatmanager.UacfChatManager;

import java.util.List;

public class ChatListActivity extends BaseActivity implements ChatListNavigator {

    private ChatListPresenter chatListPresenter;
    private ChatListView chatListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatListPresenter = new ChatListPresenter();
        chatListView = new ChatListView(this);
        chatListView.setChatListPresenter(chatListPresenter);
        chatListPresenter.initialize(chatListView, this);

        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_content_add));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatListActivity.this, FriendListActivity.class));
            }
        });

        setMainContent(chatListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_list_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.chat_list_menu_my_messages:
                openChatActivity(new Chat(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                return true;
            case R.id.chat_list_logout:
                logout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasFab() {
        return true;
    }

    @Override
    public void openChatActivity(Chat chat) {
        startActivity(ChatActivity.getChatIntent(this, chat));
    }

    private void logout() {
        UacfChatManager.logout();

        List<String> providers = FirebaseAuth.getInstance().getCurrentUser().getProviders();
        if(providers != null && providers.contains("facebook.com")) {
            LoginManager.getInstance().logOut();
        }

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
