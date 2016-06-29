package com.zacharysweigart.uacfchat.friendlist.friendprovider;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zacharysweigart.uacfchat.model.Friend;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class FirebaseFriendProvider implements FriendProvider {
    @Override
    public Observable<List<Friend>> getFriends() {
        return Observable.create(new Observable.OnSubscribe<List<Friend>>() {
            @Override
            public void call(final Subscriber<? super List<Friend>> subscriber) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                        .child("friends").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Friend> friends = new ArrayList<>();
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            friends.add(child.getValue(Friend.class));
                        }
                        subscriber.onNext(friends);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
