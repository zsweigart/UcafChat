package com.zacharysweigart.uacfchat.chatlist;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.Query;
import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.firebase.FirebaseRecyclerAdapter;
import com.zacharysweigart.uacfchat.model.Chat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends FirebaseRecyclerAdapter<Chat, ChatListAdapter.ChatListViewHolder> {

    private ChatListPresenter chatListPresenter;

    public ChatListAdapter(Class<Chat> modelClass, int modelLayout,
                           Class<ChatListViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public void setChatListPresenter(ChatListPresenter chatListPresenter) {
        this.chatListPresenter = chatListPresenter;
    }

    @Override
    protected void populateViewHolder(ChatListViewHolder viewHolder, Chat model, int position) {
        viewHolder.setContent(model, chatListPresenter);
    }

    public static class ChatListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ChatListPresenter chatListPresenter;
        private Chat chat;

        @BindView(R.id.list_item_chat_username)
        TextView textView;

        public ChatListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setContent(Chat chat, ChatListPresenter chatListPresenter) {
            this.chat = chat;
            this.chatListPresenter = chatListPresenter;
            textView.setText(chat.getUserName());
        }

        @Override
        public void onClick(View view) {
            chatListPresenter.openChat(chat);
        }
    }
}
