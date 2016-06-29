package com.zacharysweigart.uacfchat.friendlist.friendprovider;


import com.zacharysweigart.uacfchat.model.Friend;

import java.util.List;

import rx.Observable;

public interface FriendProvider {

    Observable<List<Friend>> getFriends();
}
