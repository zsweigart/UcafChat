package com.zacharysweigart.uacfchat.friendlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.base.BaseActivity;
import com.zacharysweigart.uacfchat.chat.ChatActivity;
import com.zacharysweigart.uacfchat.model.Chat;
import com.zacharysweigart.uacfchat.model.Friend;

public class FriendListActivity extends BaseActivity implements FriendListNavigator {

    private FriendListPresenter friendListPresenter;
    private FriendListView friendListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendListPresenter = new FriendListPresenter();
        friendListView = new FriendListView(this);
        friendListView.setFriendListPresenter(friendListPresenter);
        friendListPresenter.initialize(friendListView, this);

        floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_content_add));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFriendDialog();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_arrow_back);

        setMainContent(friendListView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openChat(Friend friend) {
        friendListPresenter.createChat(friend);
        startActivity(ChatActivity.getChatIntent(this, new Chat(friend.getName())));
        finish();
    }

    @Override
    protected boolean hasFab() {
        return true;
    }

    private void showFriendDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_add_friend, null, false);
        final EditText friendName = (EditText) view.findViewById(R.id.alert_dialog_friend_name);

        new AlertDialog.Builder(this)
                .setTitle(R.string.add_friend_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Friend friend = new Friend(friendName.getText().toString());
                        friendListPresenter.addFriend(friend);
                        openChat(friend);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
