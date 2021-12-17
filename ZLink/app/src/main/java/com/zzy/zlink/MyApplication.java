package com.zzy.zlink;

import android.app.Application;
import android.util.Log;

import com.zzy.zlink.client.JWebSocketClient;
import com.zzy.zlink.client.SocketCls;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.net.URI;

public class MyApplication extends Application {
//    public String localSocketUrl = "ws://10.0.2.2:8080/ZLink/imServer/";
    public String localSocketUrl = "ws://49.232.80.153:8080/ZLink/imServer/";
    public JWebSocketClient webSocketClient;

    public interface SocketListener {
        void acceptMessage(String s) throws JSONException;
    }

    public SocketListener listener;

    public void setListener(SocketListener listener) {
        this.listener = listener;
    }

    public void setWebSocketClient(String userName) {
        URI uri = URI.create(localSocketUrl + userName);
        webSocketClient = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                try {
                    Log.d("TAG", "apppppppppppppppppppppppppppppppppppppp: ");
                    listener.acceptMessage(message);
                    Log.d("TAG", "apppppppppppppppppppppppppppppppppppppp: ");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
