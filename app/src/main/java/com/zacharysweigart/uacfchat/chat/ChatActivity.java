package com.zacharysweigart.uacfchat.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.base.BaseActivity;
import com.zacharysweigart.uacfchat.model.Chat;

public class ChatActivity extends BaseActivity implements ChatNavigator {

    private static final String ARG_CHAT = "ARG_CHAT";

    private ChatPresenter chatPresenter;
    private ChatView chatView;

    public static Intent getChatIntent(Context context, Chat chat) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(ARG_CHAT, chat);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Chat chat = (Chat) getIntent().getExtras().getSerializable(ARG_CHAT);
        chatPresenter = new ChatPresenter();
        chatView = new ChatView(this);
        chatView.setChatPresenter(chatPresenter);
        chatPresenter.initialize(chatView, this, chat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_arrow_back);

        setMainContent(chatView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.chat_menu_refresh:
                chatPresenter.refreshMessages();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasFab() {
        return false;
    }
}
