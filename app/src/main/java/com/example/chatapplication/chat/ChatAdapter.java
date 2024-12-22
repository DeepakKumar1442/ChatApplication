package com.example.chatapplication.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_RECEIVE = 1;
    private static final int ITEM_SENT = 2;

    private Context context;

    private ArrayList<ChatResponse> messageList;

    public ChatAdapter(Context context, ArrayList<ChatResponse> messageList) {
        this.context=context;
        this.messageList=messageList;
    }


    @Override
    public int getItemViewType(int position) {
        ChatResponse currentMessage = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser() != null &&
                FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currentMessage.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_RECEIVE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout_receivermsg, parent, false);
            return new ReceiveViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout_sendermsg, parent, false);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatResponse currentMessage = messageList.get(position);
        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).sentMessage.setText(currentMessage.getMessage());
        } else if (holder instanceof ReceiveViewHolder) {
            ((ReceiveViewHolder) holder).receiveMessage.setText(currentMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView sentMessage;

        SentViewHolder(View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.txt_sent_message);
        }
    }

    static class ReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView receiveMessage;

        ReceiveViewHolder(View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
        }
    }
}

