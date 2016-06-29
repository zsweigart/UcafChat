package com.zacharysweigart.ucafchatmanager;


import com.zacharysweigart.ucafchatmanager.model.Message;

import java.util.ArrayList;
import java.util.List;

class OrderedMessageList {

    private List<Message> messageList;
    private MessageListListener messageListListener;

    public OrderedMessageList() {
        messageList = new ArrayList<>();
    }

    public void setMessageListListener(MessageListListener messageListListener) {
        this.messageListListener = messageListListener;
    }

    public void addMessage(int position, Message message) {
        messageList.add(position, message);
    }

    /**
     * Update list given a new list of messages, the given list should be in sorted order by id.
     * This method will notify the list listener (if it exists) for whether messages have been
     * removed or added to the existing list.
     *
     * @param newMessageList The list of messages that should be contained when the method finishes
     */
    public void updateList(List<Message> newMessageList) {
        List<Message> messagesToRemove = new ArrayList<>();
        List<Message> messagesToAdd = new ArrayList<>();

        if (newMessageList == null || newMessageList.size() == 0) {
            for (Message message : messageList) {
                if (messageListListener != null) {
                    messageListListener.messageRemoved(message);
                }
            }
            messageList = new ArrayList<>();
            return;
        }

        for (Message message : messageList) {
            if (!newMessageList.contains(message)) {
                messagesToRemove.add(message);
                messageListListener.messageRemoved(message);
            }
        }
        messageList.removeAll(messagesToRemove);

        for (Message message : newMessageList) {
            if (!messageList.contains(message)) {
                messagesToAdd.add(message);
                messageListListener.messageAdded(message);
            }
        }
        messageList.addAll(messagesToAdd);
    }

    public interface MessageListListener {
        void messageAdded(Message message);

        void messageRemoved(Message message);
    }
}
