package com.example.kuangkuang.myactivity.addFriend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
import com.example.kuangkuang.adapter.AddFriendAdapter;
import com.example.kuangkuang.adapter.FriendAdapter;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.GroupService;
import com.example.kuangkuang.service.UserService;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriend extends AppCompatActivity {
    private ImageButton back;
    private Button add;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);
    private UserService userService = retrofitFactory.setRetrofit().create(UserService.class);
    private ListView listView;
    private AddFriendAdapter adapter;
    private String groupId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friend);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        listView = findViewById(R.id.friend_list);
        back = findViewById(R.id.back_button);
        add = findViewById(R.id.add);
        add.setOnClickListener(new myClick());
        back.setOnClickListener(new myClick());
        Call<Result<List<Friend>>> userCall = userService.getByUserId(Math.toIntExact(BaseContext.getCurrentId()));
        userCall.enqueue(new retrofit2.Callback<Result<List<Friend>>>() {
            @Override
            public void onResponse(Call<Result<List<Friend>>> call, retrofit2.Response<Result<List<Friend>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> friends = response.body().getData();
                    adapter = new AddFriendAdapter(AddFriend.this, friends);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
                    listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) adapter);
                } else {
                    // 处理响应不成功的情况，可以通过 Log 打印错误信息
                    Log.e("HomeFragment", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Result<List<Friend>>> call, Throwable t) {
                // 网络请求失败
                Log.e("HomeFragment", "API call failed: " + t.getMessage());
            }
        });
    }
    class myClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.back_button){
                finish();
            }
            if (v.getId()==R.id.add){
                List<Integer> list = adapter.getSelectedUserIds();
                HashMap<String,Object> map = new HashMap<>();
                map.put("groupId",groupId);
                map.put("list",list);
                Call<Result> resultCall = groupService.addFriend(map);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("添加好友到群聊","成功");
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e("添加好友进群聊",t.getMessage());
                    }
                });
            }
        }
    }
}