package com.zzy.zlink.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzy.zlink.R;
import com.zzy.zlink.bean.Conversation;
import com.zzy.zlink.bean.ConversationInfo;
import com.zzy.zlink.bean.ResultAllConversation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HomeMessageAdapter extends RecyclerView.Adapter<HomeMessageAdapter.ViewHolder> {

    private ArrayList<ConversationInfo> conversationInfos = new ArrayList<>();

    public HomeMessageAdapter(ArrayList<ConversationInfo> conversationArrayList) {
        this.conversationInfos = conversationArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ConversationInfo conversation = conversationInfos.get(position);
        String date=conversation.getPostTime();
        holder.conversation_last_time_tv.setText(conversation.getPostTime());
        holder.conversation_title_tv.setText(conversation.getAccountName2());
        holder.last_message_tv.setText("  "+conversation.getContent()+"  ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(holder.itemView,position);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            //如果payloads没数据，说明不是局部刷新，下面这句是关键，通过源码看 会执行不带payloads参数的onBindViewHolder
            super.onBindViewHolder(holder, position, payloads);
            return;
        } else {
            for(Object conversationInfo:payloads){
                ConversationInfo conversationInfo1= (ConversationInfo) conversationInfo;
                holder.conversation_last_time_tv.setText(conversationInfo1.getPostTime());
                holder.conversation_title_tv.setText(conversationInfo1.getAccountName2());
                holder.last_message_tv.setText(conversationInfo1.getContent());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClick(holder.itemView,position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mItemClickListener.onItemLongClick(v,position);
                        return true;
                    }
                });
            }
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        if (conversationInfos != null)
            return conversationInfos.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView conversation_last_time_tv;
        TextView conversation_title_tv;
        TextView last_message_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            conversation_last_time_tv = itemView.findViewById(R.id.conversation_last_time_tv);
            conversation_title_tv = itemView.findViewById(R.id.conversation_title_tv);
            last_message_tv = itemView.findViewById(R.id.last_message_tv);
        }
    }

    OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }
}
