package com.example.kuangkuang.myactivity.UserInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendInfoActivity extends AppCompatActivity {
    private Button button;
    private ImageButton button_back;
    private Intent intent;
    private boolean isFriend;
    private String name;
    private int userId;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private UserService userService = retrofitFactory.setRetrofit().create(UserService.class);
    private OkHttpClient okHttpClient = new OkHttpClient();
    private TextView friendName;
    private TextView friendSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button_back = findViewById(R.id.back_button);
        button_back.setOnClickListener(new MyClick());
        button = findViewById(R.id.friend_button);
        button.setOnClickListener(new MyClick());
        name = intent.getStringExtra("userName");
        userId =  intent.getIntExtra("userId",1);
        isFriend = intent.getBooleanExtra("isFriend",false);
        if(userId==BaseContext.getCurrentId()){
            button.setText("修改个人信息");
        } else if (isFriend) {
            button.setText("删除好友");
        }
        friendName = findViewById(R.id.friend_name);
        friendSex = findViewById(R.id.friend_sex);
        friendName.setText(name);
        User user = new User();
        user.setId(userId);
        Call<Result<User>> resultCall = userService.getUser(user);
        resultCall.enqueue(new Callback<Result<User>>() {
            @Override
            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                Result<User> result = response.body();
                user.setSex(result.getData().getSex());
                friendSex.setText(user.getSex().equals("1")?"男":"女");
            }
            @Override
            public void onFailure(Call<Result<User>> call, Throwable t) {
                Log.e("fiendInfo",t.getMessage());
            }
        });
    }
public  class MyClick implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.friend_button&&button.getText().equals("添加好友")){
            Friend friend = new Friend();
            friend.setUser2Id(String.valueOf(userId));
            friend.setUser1Id(String.valueOf(BaseContext.getCurrentId()));
            Call<Result> resultCall = userService.addFriend(friend);
            resultCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result result = response.body();
                    Toast.makeText(FriendInfoActivity.this, "添加好友"+friend.getUser2Id()+"成功", Toast.LENGTH_SHORT).show();
                    Log.d("friend","添加好友成功"+friend);
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("friend","添加好友失败"+friend+t.getMessage());
                }
            });
        } else if (v.getId() == R.id.friend_button && button.getText().equals("修改个人信息")) {
            Intent intent1 = new Intent(FriendInfoActivity.this,UserInfoActivity.class);
            startActivity(intent1);
        } else if (v.getId() == R.id.friend_button && button.getText().equals("删除好友")) {
            Call<Result> resultCall = userService.deleteFriend(String.valueOf(userId));
            resultCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Log.d("friend","删除成功"+response.body());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("friend","删除失败"+t.getMessage());
                }
            });
        }
        if(v.getId()==R.id.back_button){
            finish();
        }
    }
}
}