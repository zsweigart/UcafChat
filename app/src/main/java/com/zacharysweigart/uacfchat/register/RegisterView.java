package com.zacharysweigart.uacfchat.register;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.uacfchat.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterView extends FrameLayout {

    @BindView(R.id.edt_activity_register_email)
    EditText emailField;
    @BindView(R.id.edt_activity_register_password)
    EditText passwordField;
    @BindView(R.id.btn_activity_register_register)
    Button registerButton;

    private Context context;
    private RegisterPresenter registerPresenter;

    public RegisterView(Context context, RegisterPresenter registerPresenter) {
        super(context);
        this.context = context;
        this.registerPresenter = registerPresenter;
        inflateLayout(context);
    }

    public void showRegistrationErrorMessage(String message) {
        showRegistrationErrorMessage(message, null);
    }

    public void showRegistrationErrorMessage(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.registration_error_title)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, onClickListener)
                .create()
                .show();
    }

    private void inflateLayout(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.activity_register, this, true);
        ButterKnife.bind(root, this);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.register(emailField.getText().toString(), passwordField.getText().toString());
            }
        });
    }
}
