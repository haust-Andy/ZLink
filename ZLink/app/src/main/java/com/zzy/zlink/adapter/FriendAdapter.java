package com.zzy.zlink.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzy.zlink.R;
import com.zzy.zlink.bean.User;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {


    ArrayList<User> users = new ArrayList<>();

    public FriendAdapter(ArrayList<User> users) {
        this.users=users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.friend_name_tv.setText(users.get(position).getAccountName());
        holder.friend_email_tv.setText(users.get(position).getAccountEmail());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
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

    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        else return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView friend_name_tv;
        TextView friend_email_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            friend_name_tv = itemView.findViewById(R.id.friend_name_tv);
            friend_email_tv = itemView.findViewById(R.id.friend_email_tv);
        }
    }

    FriendAdapter.OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view,int p);
        void onItemLongClick(View view,int p);
    }

    public void setOnItemClickListener(FriendAdapter.OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }
}
