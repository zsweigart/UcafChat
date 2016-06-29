package com.zacharysweigart.ucafchatmanager;


import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class UacfChatManagerTest {
    private static final String userName = "Test";

    @Before
    public void setup() {
        UacfChatManager.userName = "";
    }

    @Test
    public void login_whenGivenUserName_setsManagerUserName() {
        UacfChatManager.login(userName);
        assertThat(UacfChatManager.userName, equalTo(userName));
    }

    @Test
    public void logout_whenUserNameSet_clearsUserName () {
        UacfChatManager.userName = userName;
        UacfChatManager.logout();
        assertThat(UacfChatManager.userName, nullValue());
    }

    @Test
    public void getChat_whenNotGivenUserName_returnsChatForLoggedInUser() {
        UacfChatManager.login(userName);
        UacfChat uacfChat = UacfChatManager.getChat();
        assertThat(uacfChat.userName, equalTo(userName));
    }

    @Test
    public void getChat_whenGivenUserName_returnsChatForGivenUser() {
        String newUserName = "New";
        UacfChatManager.login(userName);
        UacfChat uacfChat = UacfChatManager.getChat(newUserName);
        assertThat(uacfChat.userName, equalTo(newUserName));
    }

    @Test
    public void setLogging_whenGivenEnabled_setsConnectionLoggingEnabled() {
        UacfChatManager.setLoggingEnabled(true);
        assertThat(UacfConnection.LOG, equalTo(true));
    }
}
