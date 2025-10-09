package com.example.kuangkuang.manager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.kuangkuang.entity.Message;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.OkHttpClient;
import okhttp3.internal.http2.Http2Reader;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;

public class WebSocketManager {
    private static final String TAG = "WebSocketManager";
    private static WebSocketManager instance;
    private WebSocket webSocket;
    private OkHttpClient client;
    private String serverUrl = "ws://192.168.43.98:8080/chat";

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String groupId;

    private WebSocketListener webSocketListener;
    private WebSocketCallback callback;

    public static WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }

    private WebSocketManager() {
        client = new OkHttpClient.Builder()
                .pingInterval(30, TimeUnit.SECONDS) // 设置心跳包
                .build();
    }

    public void setCallback(WebSocketCallback callback) {
        this.callback = callback;
    }

    public void connect(String groupId) {
        if (webSocket != null) {
            webSocket.close(1000, "重新连接");
        }

        Request request = new Request.Builder()
                .url(serverUrl+"/"+groupId)
                .build();

        webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.d(TAG, "WebSocket连接已建立");
                if (callback != null) {
                    callback.onConnected();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "收到消息: " + text);
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    String from = jsonObject.getString("userId");
                    String content = jsonObject.getString("message");
                    String groupId = jsonObject.getString("groupId");
                    String timestamp = jsonObject.getString("time");
                    String name = jsonObject.getString("userName");

                    if (callback != null) {
                        Log.d("1","2222222");
                        Message message = new Message();
                        message.setMessage(content);
                        message.setGroupId(Integer.parseInt(groupId));
                        message.setUserId(Integer.parseInt(from));
                        message.setTime(timestamp);
                        callback.onMessageReceived(message);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "消息解析错误: " + e.getMessage());
                    if (callback != null) {
                        callback.onError("消息格式错误");
                    }
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d(TAG, "连接关闭中: " + code + " - " + reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d(TAG, "连接已关闭: " + code + " - " + reason);
                if (callback != null) {
                    callback.onDisconnected();
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                Log.e(TAG, "连接失败: " + t.getMessage());
                if (callback != null) {
                    callback.onError(t.getMessage());
                }
                // 尝试重新连接
                reconnect();
            }
        };

        webSocket = client.newWebSocket(request, webSocketListener);
    }
//TODO 设置传输图片的函数
    public void sendMessage(Message message) {
        if (webSocket == null) {
            if (callback != null) {
                callback.onError("未连接到服务器");
            }
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", message.getMessage());
            jsonObject.put("groupId", message.getGroupId());
            jsonObject.put("time", message.getTime());
            jsonObject.put("userId",message.getUserId());
            jsonObject.put("userName",message.getUserName());

            boolean isSent = webSocket.send(jsonObject.toString());
            if (!isSent && callback != null) {
                callback.onError("消息发送失败");
            }
        } catch (JSONException e) {
            Log.e(TAG, "创建消息JSON错误: " + e.getMessage());
            if (callback != null) {
                callback.onError("消息格式错误");
            }
        }
    }

    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(1000, "正常关闭");
            webSocket = null;
        }
    }

    private void reconnect() {
        // 延迟3秒后重新连接
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "尝试重新连接...");
                connect(groupId); // 重新连接
            }
        }, 3000);
    }

    public boolean isConnected() {
        return webSocket != null;
    }

    public interface WebSocketCallback {
        void onConnected();
        void onMessageReceived(Message message);
        void onDisconnected();
        void onError(String error);
    }
}