package com.zacharysweigart.uacfchat.friendlist;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.model.Friend;
import com.zacharysweigart.uacfchat.util.SimpleDividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendListView extends RelativeLayout {

    private FriendListPresenter friendListPresenter;
    private FriendListAdapter friendListAdapter;

    @BindView(R.id.friend_list_recycler)
    RecyclerView friendList;

    public FriendListView(Context context) {
        super(context);
        initialize(context);
    }

    public void setFriendListPresenter(FriendListPresenter friendListPresenter) {
        this.friendListPresenter = friendListPresenter;
        friendListAdapter.setFriendListPresenter(friendListPresenter);
    }

    public void addFriends(List<Friend> friendList) {
        friendListAdapter.addFriends(friendList);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.content_friend_list, this);
        ButterKnife.bind(this);

        friendListAdapter = new FriendListAdapter();
        friendList.setLayoutManager(new LinearLayoutManager(context));
        friendList.setAdapter(friendListAdapter);
        friendList.addItemDecoration(new SimpleDividerItemDecoration(context));
    }
}
