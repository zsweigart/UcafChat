package com.zacharysweigart.uacfchat.friendlist;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zacharysweigart.uacfchat.friendlist.friendprovider.FacebookFriendProvider;
import com.zacharysweigart.uacfchat.friendlist.friendprovider.FirebaseFriendProvider;
import com.zacharysweigart.uacfchat.model.Chat;
import com.zacharysweigart.uacfchat.model.Friend;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendListPresenter {

    private FriendListView friendListView;
    private FriendListNavigator friendListNavigator;
    private Subscriber<List<Friend>> friendsSubscriber;

    public FriendListPresenter() {
    }

    public void initialize(FriendListView friendListView, FriendListNavigator friendListNavigator) {
        this.friendListView = friendListView;
        this.friendListNavigator = friendListNavigator;

        friendsSubscriber = getFriendsSubscriber();
        FacebookFriendProvider facebookFriendProvider = new FacebookFriendProvider();
        facebookFriendProvider.getFriends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .mergeWith(new FirebaseFriendProvider().getFriends())
                .subscribe(friendsSubscriber);
    }

    public void addFriend(Friend friend) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.push().setValue(friend);
    }

    public void createChat(Friend friend) {
        Chat chat = new Chat(friend.getName());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.push().setValue(chat);
    }

    public void openChat(Friend friend) {
        friendListNavigator.openChat(friend);
    }

    private Subscriber<List<Friend>> getFriendsSubscriber() {
        return new Subscriber<List<Friend>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Friend> friends) {
                friendListView.addFriends(friends);
            }
        };
    }
}
