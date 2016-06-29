package com.zacharysweigart.uacfchat.register;


public class RegisterPresenter {

    private RegisterView registerView;
    private RegisterNavigator registerNavigator;

    public RegisterPresenter() {
    }

    public void initialize(RegisterView registerView, RegisterNavigator registerNavigator) {
        this.registerView = registerView;
        this.registerNavigator = registerNavigator;
    }

    public void register(final String email, final String password) {
        registerNavigator.register(email, password);
    }
}
