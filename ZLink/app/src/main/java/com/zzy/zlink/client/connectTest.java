package com.zzy.zlink.client;

import android.util.Log;

import java.net.URI;

public class connectTest {
    URI uri = URI.create("ws:localhost:8080/ZLink/imServer/{zzy}");
    JWebSocketClient client = new JWebSocketClient(uri) {
        @Override
        public void onMessage(String message) {
            //message就是接收到的消息
            Log.e("JWebSClientService", message);
        }
    };
}
