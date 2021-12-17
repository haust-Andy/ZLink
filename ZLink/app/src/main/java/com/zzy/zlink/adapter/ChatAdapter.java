package com.zzy.zlink.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzy.zlink.R;
import com.zzy.zlink.bean.ChatContent;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<ChatContent> chats = new ArrayList<>();

    public ChatAdapter(ArrayList<ChatContent> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_right, parent, false);
        else
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_left, parent, false);
        return new ChatAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.chat_Text_tv.setText("  "+chats.get(position).getContent()+"  ");
        holder.post_time_tv.setText(chats.get(position).getPostTime().toString());
    }

    @Override
    public int getItemViewType(int position) {
        return chats.get(position).getType();
    }

    @Override
    public int getItemCount() {
        if (chats != null)
            return chats.size();
        else
            return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chat_Text_tv;
        TextView post_time_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_Text_tv = itemView.findViewById(R.id.chat_Text_tv);
            post_time_tv = itemView.findViewById(R.id.post_time_tv);
        }
    }
}
