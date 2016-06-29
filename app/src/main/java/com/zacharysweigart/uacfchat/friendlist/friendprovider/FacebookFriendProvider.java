package com.zacharysweigart.uacfchat.friendlist.friendprovider;


import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.zacharysweigart.uacfchat.model.Friend;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class FacebookFriendProvider implements FriendProvider {
    @Override
    public Observable<List<Friend>> getFriends() {
        return Observable.create(new Observable.OnSubscribe<List<Friend>>() {
            @Override
            public void call(final Subscriber<? super List<Friend>> subscriber) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        Log.i(getClass().getSimpleName(), objects.toString());
                        Log.i(getClass().getSimpleName(), response.toString());
                    }
                }).executeAndWait();
            }
        });
    }
}
