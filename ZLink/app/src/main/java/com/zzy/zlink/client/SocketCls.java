package com.zzy.zlink.client;

import android.os.Build;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SocketCls {
    public interface SocketListener {
        void onOpen(ServerHandshake serverHandshake);
        void onMessage(String s);
        void onClose(int i, String s, boolean b);

    }

    SocketListener listener;

    public SocketCls(SocketListener l) {
        listener = l;
    }
    private WebSocketClient mWebSocketClient;
    private void connectWebSocket(String url) throws URISyntaxException {
        URI uri;
        uri = new URI(url);

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
                listener.onOpen(serverHandshake);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                // Here i want to use callback
                listener.onMessage(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                listener.onClose(i, s, b);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
}
