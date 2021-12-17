package com.zzy.zlink.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzy.zlink.MyApplication;
import com.zzy.zlink.R;
import com.zzy.zlink.adapter.ChatAdapter;
import com.zzy.zlink.api.MessageInterface;
import com.zzy.zlink.bean.ChatContent;
import com.zzy.zlink.bean.ConversationInfo;
import com.zzy.zlink.bean.ResultMessage;
import com.zzy.zlink.client.JWebSocketClient;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends MyActivity {
    String TAG = "TAG";
    MyApplication application;
    JWebSocketClient client;

    ArrayList<ChatContent> chats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        application = (MyApplication) getApplication();
        TextView friend_name_tv = findViewById(R.id.friend_name_tv);

        application.setListener(new MyApplication.SocketListener() {
            @Override
            public void acceptMessage(String s) throws JSONException {
                JSONObject jsonObject = new JSONObject(s);
                String fromName = jsonObject.getString("from");
                if (fromName.equals(friend_name_tv.getText())) {
                    String text = jsonObject.getString("text");
                    ChatContent chatContent = new ChatContent();
                    chatContent.setContent(text);
                    chatContent.setType(0);
                    chatContent.setPostTime(new Date());
                    ChatActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateChatRv(chatContent);
                        }
                    });
                }
            }
        });

        client = application.webSocketClient;
        initOnClick();
        initData();
    }

    private void initData() {
        getdataChatRv();
        TextView friend_name_tv = findViewById(R.id.friend_name_tv);
        Bundle bundle = getIntent().getExtras();//获取intent对象携带的数据
        String friendName = bundle.getString("friendName");
        friend_name_tv.setText(friendName);
        ConversationInfo conversation = new ConversationInfo();
        conversation.setAccountName2(friendName);
        getMessage(conversation);

    }

    void getdataChatRv() {
        RecyclerView message_rv = findViewById(R.id.message_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        message_rv.setLayoutManager(layoutManager);
        ChatAdapter adapter = new ChatAdapter(chats);
        message_rv.setAdapter(adapter);
        message_rv.scrollToPosition(adapter.getItemCount() - 1);
    }

    void updateChatRv(ChatContent contents) {
        RecyclerView message_rv = findViewById(R.id.message_rv);
        ChatAdapter adapter = new ChatAdapter(chats);
        adapter.notifyItemChanged(chats.size(), contents);
        message_rv.scrollToPosition(adapter.getItemCount());
        Toast.makeText(ChatActivity.this,"新消息！",Toast.LENGTH_SHORT);
        chats.add(contents);
    }

    public void getMessage(ConversationInfo conversationInfo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MessageInterface request = retrofit.create(MessageInterface.class);
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String token = userData.getString("accountToken", "");
        Call<ResultMessage> userCall = request.getMessage(token, conversationInfo);
        userCall.enqueue(new Callback<ResultMessage>() {

            @Override
            public void onResponse(Call<ResultMessage> call, Response<ResultMessage> response) {
                chats = response.body().data;
                getdataChatRv();
            }

            @Override
            public void onFailure(Call<ResultMessage> call, Throwable t) {
            }
        });
    }

    private void initOnClick() {
        ImageView send_img = findViewById(R.id.send_img);
        ImageView back_img = findViewById(R.id.back_img);
        EditText chat_content_et = findViewById(R.id.chat_content_et);
        TextView friend_name_tv = findViewById(R.id.friend_name_tv);

        send_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                client.onMessage("a");
                JSONObject jsonObject = new JSONObject();
                if (chat_content_et != null) {
                    try {
                        String chatText = chat_content_et.getText().toString();
                        jsonObject.put("to", friend_name_tv.getText().toString());
                        jsonObject.put("text", chatText);
                        System.out.println(client.isOpen() + client.toString());
                        if (client != null && client.isOpen()) {

                            client.send(jsonObject.toString());
                            ChatContent chatContent = new ChatContent();
                            chatContent.setType(1);
                            chatContent.setContent(chatText);
                            chatContent.setPostTime(new Date());

                            updateChatRv(chatContent);
                            chat_content_et.setText("");
                            //发送完毕后关闭软键盘
                            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        } else
                            Log.d(TAG, "onClick: 发送失败");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ChatActivity.this,HomeActivity.class));
            }
        });
    }


}