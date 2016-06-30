package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrderedMessageListTest {

    private OrderedMessageList orderedMessageList;
    private OrderedMessageList.MessageListListener messageListListener;
    private Message message1;
    private Message message2;
    private Message message3;

    @Before
    public void setup() {
        orderedMessageList = new OrderedMessageList();
        messageListListener = Mockito.mock(OrderedMessageList.MessageListListener.class);
        message1 = Mockito.mock(Message.class);
        message2 = Mockito.mock(Message.class);
        message3 = Mockito.mock(Message.class);
        orderedMessageList.setMessageListListener(messageListListener);
    }

    @Test
    public void addMessage_whenGivenMessage_addsToFrontOfList() {
        orderedMessageList.addMessage(message1);
        assertThat(orderedMessageList.messageList.size(), equalTo(1));
        assertThat(orderedMessageList.messageList.get(0), equalTo(message1));
    }

    @Test
    public void updateMessageList_whenListEmpty_setsListToNewList() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        orderedMessageList.updateList(messageList);
        assertThat(orderedMessageList.messageList.size(), equalTo(1));
        assertThat(orderedMessageList.messageList.get(0), equalTo(message1));
        verify(messageListListener, times(1)).messageAdded(Matchers.any(Message.class));
        verify(messageListListener, times(0)).messageRemoved(Matchers.any(Message.class));
    }

    @Test
    public void updateMessageList_whenNewListContainsElements_maintainsElements() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);
        orderedMessageList.addMessage(message1);
        orderedMessageList.updateList(messageList);
        assertThat(orderedMessageList.messageList.size(), equalTo(2));
        assertThat(orderedMessageList.messageList.get(0), equalTo(message1));
        assertThat(orderedMessageList.messageList.get(1), equalTo(message2));
        verify(messageListListener, times(1)).messageAdded(Matchers.any(Message.class));
        verify(messageListListener, times(0)).messageRemoved(Matchers.any(Message.class));
    }

    @Test
    public void updateMessageList_whenNewListContainsSomeElements_removesItemsNotInCommon() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message2);
        messageList.add(message3);
        orderedMessageList.addMessage(message1);
        orderedMessageList.addMessage(message2);
        orderedMessageList.updateList(messageList);
        assertThat(orderedMessageList.messageList.size(), equalTo(2));
        assertThat(orderedMessageList.messageList.get(0), equalTo(message2));
        assertThat(orderedMessageList.messageList.get(1), equalTo(message3));
        verify(messageListListener, times(1)).messageAdded(Matchers.any(Message.class));
        verify(messageListListener, times(1)).messageRemoved(Matchers.any(Message.class));
    }

    @Test
    public void updateMessageList_whenNewListContainsNoneOfTheElements_removesAllItems() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(message3);
        orderedMessageList.addMessage(message1);
        orderedMessageList.addMessage(message2);
        orderedMessageList.updateList(messageList);
        assertThat(orderedMessageList.messageList.size(), equalTo(1));
        assertThat(orderedMessageList.messageList.get(0), equalTo(message3));
        verify(messageListListener, times(1)).messageAdded(Matchers.any(Message.class));
        verify(messageListListener, times(2)).messageRemoved(Matchers.any(Message.class));
    }

    @Test
    public void updateMessageList_whenNewListIsEmpty_removesAllElements() {
        orderedMessageList.addMessage(message1);
        orderedMessageList.updateList(new ArrayList<Message>());
        assertThat(orderedMessageList.messageList.size(), equalTo(0));
        verify(messageListListener, times(0)).messageAdded(Matchers.any(Message.class));
        verify(messageListListener, times(1)).messageRemoved(Matchers.any(Message.class));
    }
}
