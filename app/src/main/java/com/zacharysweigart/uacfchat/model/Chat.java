package com.zacharysweigart.uacfchat.model;


import java.io.Serializable;

public class Chat implements Serializable {
    private String userName;

    public Chat() {
    }

    public Chat(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
