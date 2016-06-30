package com.zacharysweigart.uacfchat.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.base.BaseActivity;
import com.zacharysweigart.uacfchat.chatlist.ChatListActivity;
import com.zacharysweigart.uacfchat.register.RegisterActivity;
import com.zacharysweigart.ucafchatmanager.UacfChatManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.btn_activity_login_facebook)
    LoginButton facebookSignInButton;
    @BindView(R.id.activity_login_email_edit)
    EditText emailField;
    @BindView(R.id.activity_login_password_edit)
    EditText passwordField;
    @BindView(R.id.btn_activity_login_sign_in)
    Button signInButton;
    @BindView(R.id.btn_activity_login_register)
    Button registerButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMainContent(View.inflate(this, R.layout.acitivty_login, null));
        ButterKnife.bind(this);

        showProgress();

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    openChatList(user);
                } else {
                    hideProgress();
                }
            }
        };

        callbackManager = CallbackManager.Factory.create();
        facebookSignInButton.setReadPermissions("email", "public_profile", "user_friends");
        facebookSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.i(getClass().getSimpleName(), "CANCELED");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(getClass().getSimpleName(), error.getMessage());
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInEmail();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.login_action_sign_in);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void openChatList(FirebaseUser firebaseUser) {
        UacfChatManager.login(firebaseUser.getEmail());
        Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected boolean hasFab() {
        return false;
    }

    private void signInEmail() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                openChatList(task.getResult().getUser());
                            }
                        }
                    });
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            openChatList(task.getResult().getUser());
                        }
                    }
                });
    }
}

