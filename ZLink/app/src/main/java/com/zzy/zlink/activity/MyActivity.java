package com.zzy.zlink.activity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zzy.zlink.bean.Conversation;
import com.zzy.zlink.bean.User;
import com.zzy.zlink.bean.UserFriend;
import com.zzy.zlink.client.JWebSocketClient;

import java.net.URI;
import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {
    public final String baseUrl = "http://49.232.80.153:8080/";
//    public final String localBaseUrl = "http://10.0.2.2:8080/";
    public final String localSocketUrl="ws://10.0.2.2:8080/ZLink/imServer/zzy";


    View.OnClickListener finishClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
