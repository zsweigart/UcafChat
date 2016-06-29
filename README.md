#Under Armor Connected Fitness Chat Manager

##Setup

### Include code
The code for the module has not been published so you will need to include the module directly to use the UACF Manager in your application.

### Setup UACF Chat Manager
To get started login a user in the UcafChatManager class.  This user will be the default recipient of messages (due to limitations of the endpoints there is no sender).

`UacfChatManager.login("Zach");`

Additionally for Debugging it may be helpful to set logging enabled on the chat manager when debugging.  A convenient way to do this is to use the BuildConfig flag.

`UacfChatManager.setLoggingEnabled(BuildConfig.DEBUG);`

The Uacf Chat Manager module relies on [Retrofit2] to make rest calls and logging enabled uses an [HttpLoggingInterceptor] set to BODY.

[Retrofit2]: https://github.com/square/retrofit
[HttpLoggingInterceptor]: https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor

## Messaging

### Messages
Messages are represented using the Message class and inlude an id, username, text (body), timeout, and error.

### Create a Chat
The UacfChatManager provides two methods for creating a chat, one which takes the recipient's username as a parameter and the other which uses the default logged in user as a recipient.

`UacfChatManager.getChat()`

`UacfChatManager.getChat("Thomas")`

### Sending Messages
Sending messages is done through the UacfChat class' send message method which takes a message object as a parameter.  If you call this method it will post the message. 

`uacfChat.sendMessage(new Message("Thomas", "Hi!"));`

In order to respond to the result of the post operation you must set a MessageSendListener on the UacfChat before sending the message. The MessageSendListener provides two callbacks, messageSent which is called when the message is successfuly sent, and messageSendFailure which is called by any error including network errors and server errors.

```
uacfChat.setMessageSendListener(new UacfChat.MessageSendListener() {
            @Override
            public void messageSent(Message message) {
                chatView.messageSent(message);
            }

            @Override
            public void messageSendFailure(Message message) {
                chatView.messageSendFailure(message);
            }
        });
```

### Retrieving Messages
Retrieving messages is done through the UacfChat class' refresh message method.  If you call this method it will get all messages sent to the user defined in the chat. 

`uacfChat.refreshMessages();`

In order to respond to the result of the get operation you must set a MessageRefreshListener on the UacfChat before sending the message. The MessageRefreshListener provides three callbacks, messageAdded which is called when the message not already included in the chat has been added, messageAdded which is called when a message in the chat is no longer available on the server, and messageRefreshFailure which is called by any error including network errors and server errors.

```
uacfChat.setMessageRefreshListener(new UacfChat.MessageRefreshListener() {
            @Override
            public void messageAdded(Message message) {
                chatView.messageAdded(message);
            }

            @Override
            public void messageRemoved(Message message) {
                chatView.messageRemoved(message);
            }

            @Override
            public void messageRefreshFailure() {
                chatView.messageRefreshFailure();
            }
        });
```

## Future Considerations
### Sender
The current api does not consider sender as part of a message which makes chats difficult to comprehend.  However, adding a sender would be very simple and the following updates would need to be made:


1. The Message object would need a sender field
2. The logged in user in the UacfChatManager would be the sender
3. UcafChat's refresh would load messages where the sender and recipient are either the logged in user and the intended recipient respectively or the intended recipient and logged in user respectively

### Message Ordering
The current api does not return messages in any particular order and ids are not relative.  In order to make a chat that would be comprehensible with ordered messages the following changes would need to be made:

1. The Message object would need a timestamp field or sequential ids
2. The internal ordered message list would have to sort based on the timestamp or ids depending on which one is used.