package com.example.kuangkuang.myactivity.message;



import static android.app.PendingIntent.getActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
import com.example.kuangkuang.adapter.MessageAdapter;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Message;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.manager.WebSocketManager;
import com.example.kuangkuang.myactivity.BaseActivity;
import com.example.kuangkuang.myactivity.addFriend.AddFriend;
import com.example.kuangkuang.service.MessageService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends BaseActivity  implements WebSocketManager.WebSocketCallback{
    private String TAG="网络连接";
    private Intent intent;

    private String currentGroupId;
    private String userId;
    private BaseRetrofitFactory factory = new BaseRetrofitFactory();
    private MessageService messageService = factory.setRetrofit().create(MessageService.class);
    private MessageAdapter adapter;
    private WebSocketManager webSocketManager;
    private String message = "";
    private EditText text;
    private Button button;
    private ImageButton back;
    private ImageButton info;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        currentGroupId = String.valueOf(intent.getIntExtra("groupId",1));
        userId = String.valueOf(intent.getLongExtra("userId",1));
        webSocketManager = WebSocketManager.getInstance();
        webSocketManager.setCallback(this); // 设置回调
        webSocketManager.setGroupId(currentGroupId);
        webSocketManager.connect(currentGroupId);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView= findViewById(R.id.message_list);
        Call<Result<List<Message>>> result = messageService.getByGroupId(currentGroupId);
        result.enqueue(new Callback<Result<List<Message>>>() {
            @Override
            public void onResponse(Call<Result<List<Message>>> call, Response<Result<List<Message>>> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    List<Message> messages = response.body().getData();
                    adapter = new MessageAdapter(MessageActivity.this,messages);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
                    listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) adapter);
                }else {
                    Log.e(TAG,"获取列表为空"+response);
                }
            }
            @Override
            public void onFailure(Call<Result<List<Message>>> call, Throwable t) {
                    Log.e(TAG,"获取信息列表失败"+t.getMessage());
            }
        });
        text = findViewById(R.id.message_edit);
        button = findViewById(R.id.message_send);
        back = findViewById(R.id.back_button);
        info = findViewById(R.id.group_info);
        info.setOnClickListener(new myClick());
        back.setOnClickListener(new myClick());
        button.setOnClickListener(new myClick());
    }
    class myClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.message_send){
                if(text.getText().toString()!=null){
                    message = text.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String formattedTime = sdf.format(new Date());
                    Message me = new Message();
                    me.setTime(formattedTime);
                    User user = BaseContext.getCurrentUser();
                    me.setUserName(user.name);
                    me.setUserId(Integer.parseInt(userId));
                    me.setGroupId(Integer.parseInt(currentGroupId));
                    me.setMessage(message);
                    addMessageToUI(me);
                    webSocketManager.sendMessage(me);
                    text.setText("");
                }
            }
            if(v.getId()==R.id.back_button){
                finish();
            }
            if(v.getId()==R.id.group_info){
                Intent intent1 = new Intent(MessageActivity.this,AddFriend.class);
                intent1.putExtra("groupId",currentGroupId);
                startActivity(intent1);
            }
        }
    }
    @Override
    public void onConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"已连接");
            }
        });
    }
    @Override
    public void onMessageReceived(Message message) {
        // 只显示当前群组的消息
        if (!String.valueOf(message.getGroupId()).equals(currentGroupId)) {
            return;
        }
        addMessageToUI(message);
    }
    public void addMessageToUI(Message message){
//        adapter.notifyDataSetChanged();
//         adapter.addItem(message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 确保adapter不为null
                if (adapter == null) {
                    Log.e(TAG, "Adapter is null when adding message");
                    return;
                }
                adapter.addItem(message);
                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(adapter.getCount() - 1);
                Log.d(TAG, "Message added to UI: " + message.getMessage());
            }
        });
    }
    @Override
    public void onDisconnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"已断开");
            }
        });
    }

    @Override
    public void onError(String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"出现错误");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 断开WebSocket连接
        WebSocketManager.getInstance().disconnect();
    }

}