package com.zacharysweigart.uacfchat.chatlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.google.firebase.database.Query;
import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.model.Chat;
import com.zacharysweigart.uacfchat.util.SimpleDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatListView extends RelativeLayout {

    private ChatListPresenter chatListPresenter;
    private ChatListAdapter chatListAdapter;

    @BindView(R.id.chat_list_recycler)
    RecyclerView chatList;

    public ChatListView(Context context) {
        super(context);
        initialize(context);
    }

    public void setChatListPresenter(ChatListPresenter chatListPresenter) {
        this.chatListPresenter = chatListPresenter;
    }

    public void updateDataList(Query query) {
        chatListAdapter = new ChatListAdapter(Chat.class, R.layout.item_chat,
                ChatListAdapter.ChatListViewHolder.class, query);
        chatListAdapter.setChatListPresenter(chatListPresenter);
        chatList.setAdapter(chatListAdapter);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.content_chat_list, this);
        ButterKnife.bind(this);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList.addItemDecoration(new SimpleDividerItemDecoration(context));
    }
}
