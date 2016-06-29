package com.zacharysweigart.uacfchat.friendlist;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.model.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {

    private FriendListPresenter friendListPresenter;
    private List<Friend> friendList;

    public FriendListAdapter() {
        friendList = new ArrayList<>();
    }

    public void setFriendListPresenter(FriendListPresenter friendListPresenter) {
        this.friendListPresenter = friendListPresenter;
    }

    @Override
    public FriendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendListViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendListViewHolder holder, int position) {
        holder.setContent(friendList.get(position), friendListPresenter);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void addFriends(List<Friend> friendList) {
        int position = friendList.size();
        this.friendList.addAll(friendList);
        notifyItemRangeChanged(position, friendList.size());
    }

    public static class FriendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FriendListPresenter friendListPresenter;
        private Friend friend;

        @BindView(R.id.list_item_friend_name)
        TextView friendNameTextView;

        public FriendListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setContent(Friend friend, FriendListPresenter friendListPresenter) {
            this.friendListPresenter = friendListPresenter;
            this.friend = friend;
            friendNameTextView.setText(friend.getName());
        }

        @Override
        public void onClick(View view) {
            friendListPresenter.openChat(friend);
        }
    }
}
