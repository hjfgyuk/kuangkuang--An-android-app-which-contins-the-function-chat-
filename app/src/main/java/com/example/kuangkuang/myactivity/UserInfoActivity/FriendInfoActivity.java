package com.example.kuangkuang.myactivity.UserInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
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
        if(v.getId()==R.id.friend_button){
            User user = new User();
            user.setId(userId);
            user.setName(name);
            Call<Result> resultCall = userService.addFriend(user);
            resultCall.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result result = response.body();
                    Log.d("friend","添加好友成功"+user.toString());
                }
                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.e("friend","添加好友失败"+user.toString()+t.getMessage());
                }
            });
        }
        if(v.getId()==R.id.back_button){
            finish();
        }
    }
}
}