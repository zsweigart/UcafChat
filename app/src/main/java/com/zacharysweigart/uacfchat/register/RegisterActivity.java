package com.zacharysweigart.uacfchat.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zacharysweigart.uacfchat.base.BaseActivity;
import com.zacharysweigart.uacfchat.chatlist.ChatListActivity;

public class RegisterActivity extends BaseActivity implements RegisterNavigator {

    private RegisterPresenter registerPresenter;
    private RegisterView registerView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        registerPresenter = new RegisterPresenter();
        registerView = new RegisterView(this, registerPresenter);
        registerPresenter.initialize(registerView, this);

        setMainContent(registerView);
    }

    @Override
    public void openChatList() {
        Intent intent = new Intent(RegisterActivity.this, ChatListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void register(final String email, final String password) {
        showProgress();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authUser(email, password);
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    }
                });
    }

    private void authUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openChatList();
                        }
                        hideProgress();
                    }
                });
    }

    @Override
    protected boolean hasFab() {
        return false;
    }
}
