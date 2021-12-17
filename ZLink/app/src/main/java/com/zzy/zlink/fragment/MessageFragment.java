package com.zzy.zlink.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzy.zlink.R;
import com.zzy.zlink.activity.ChatActivity;

import com.zzy.zlink.adapter.HomeMessageAdapter;

import com.zzy.zlink.bean.ConversationInfo;


import java.util.ArrayList;


public class MessageFragment extends Fragment {
    public final String baseUrl = "49.232.80.153";
    public final String localBaseUrl = "http://10.0.2.2:8080/";
    public final String localSocketUrl="ws://10.0.2.2:8080/ZLink/imServer/dzzy";
    ArrayList<ConversationInfo> conversationArrayList;
    static Handler handler = new Handler(Looper.getMainLooper());
    public MessageFragment(ArrayList<ConversationInfo> conversationArrayList) {
        this.conversationArrayList = conversationArrayList;
    }

    public MessageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_message=inflater.inflate(R.layout.fragment_message,container,false);
        RecyclerView conversation_rv = fragment_message.findViewById(R.id.conversation_rv);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        conversation_rv.setLayoutManager(layoutManager);
        HomeMessageAdapter adapter=new HomeMessageAdapter(conversationArrayList);
        adapter.setOnItemClickListener(new HomeMessageAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view,int position) {
                LinearLayout linearLayout=(LinearLayout)conversation_rv.getChildAt(position);
                TextView conversation_title_tv = linearLayout.findViewById(R.id.conversation_title_tv);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("friendName",conversation_title_tv.getText().toString());
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
                        conversationArrayList.remove(position);
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

        conversation_rv.setAdapter(adapter);
        return fragment_message;
    }


}
