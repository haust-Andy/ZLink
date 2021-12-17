package com.zzy.zlink.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzy.zlink.R;
import com.zzy.zlink.activity.ChatActivity;
import com.zzy.zlink.adapter.FriendAdapter;
import com.zzy.zlink.adapter.HomeMessageAdapter;
import com.zzy.zlink.bean.ConversationInfo;
import com.zzy.zlink.bean.User;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    public final String baseUrl = "49.232.80.153";
    public final String localBaseUrl = "http://10.0.2.2:8080/";
    public final String localSocketUrl="ws://10.0.2.2:8080/ZLink/imServer/dzzy";
    ArrayList<User> users=new ArrayList<>();

    public FriendFragment() {
    }


    public FriendFragment(ArrayList<User> users) {
        this.users=users;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_friend = inflater.inflate(R.layout.fragment_friend, container,false);

        RecyclerView friend_rv = fragment_friend.findViewById(R.id.friend_rv);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        friend_rv.setLayoutManager(layoutManager);
        FriendAdapter adapter=new FriendAdapter(users);
        adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                LinearLayout linearLayout=(LinearLayout)friend_rv.getChildAt(position);
                TextView conversation_title_tv = linearLayout.findViewById(R.id.friend_name_tv);
                Intent intent=new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("friendName",conversation_title_tv.getText());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setIcon(R.drawable.question_dialog_icon);
                builder.setTitle("确定要删除对话吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        users.remove(position);
                        getActivity().recreate();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        friend_rv.setAdapter(adapter);
        return fragment_friend;
    }
}
