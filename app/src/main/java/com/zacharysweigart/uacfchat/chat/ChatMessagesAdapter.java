package com.zacharysweigart.uacfchat.chat;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zacharysweigart.uacfchat.R;
import com.zacharysweigart.ucafchatmanager.model.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatMessagesViewHolder> {

    private ChatPresenter chatPresenter;
    private List<Message> messageList;

    public ChatMessagesAdapter(ChatPresenter chatPresenter) {
        this.chatPresenter = chatPresenter;
        messageList = new ArrayList<>();
    }

    @Override
    public ChatMessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMessagesViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_chat_message, parent, false));
    }

    @Override
    public void onBindViewHolder(ChatMessagesViewHolder holder, int position) {
        holder.setContent(messageList.get(position), chatPresenter);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void addMessage(Message message) {
        addMessage(0, message);
    }

    public void addMessage(int position, Message message) {
        messageList.add(position, message);
        notifyItemInserted(position);
    }

    public void removeMessage(Message message) {
        int position = messageList.indexOf(message);
        if(position > -1) {
            messageList.remove(message);
            notifyItemRemoved(position);
        }
    }

    public void sendSuccess(Message message) {
        int position = messageList.indexOf(message);
        if(position > -1) {
            notifyItemChanged(position);
        }
    }

    public void sendFailure(Message message) {
        int position = messageList.indexOf(message);
        if(position > -1) {
            notifyItemChanged(position);
        }
    }

    public static class ChatMessagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_item_chat_message)
        TextView chatMessage;
        @BindView(R.id.list_item_chat_error)
        TextView chatError;

        private Context context;
        private Message message;
        private ChatPresenter chatPresenter;

        public ChatMessagesViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void setContent(Message message, ChatPresenter chatPresenter) {
            this.message = message;
            this.chatPresenter = chatPresenter;

            chatMessage.setText(message.getText());
            int textColor = !TextUtils.isEmpty(message.getId()) ? ContextCompat.getColor(context, R.color.textDarkPrimary) :
                    ContextCompat.getColor(context, R.color.textDarkSecondary);
            chatMessage.setTextColor(textColor);

            if(message.hasError()) {
                showSendError();
            } else {
                hideSendError();
            }
        }

        public void showSendError() {
            chatMessage.setTextColor(ContextCompat.getColor(context, R.color.textDarkHint));
            chatError.setVisibility(View.VISIBLE);
        }

        public void hideSendError() {
            chatMessage.setTextColor(ContextCompat.getColor(context, R.color.textDarkPrimary));
            chatError.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            if(message.hasError()) {
                chatPresenter.resendMessage(message);
            }
        }
    }
}
