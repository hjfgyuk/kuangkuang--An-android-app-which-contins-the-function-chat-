package com.example.kuangkuang.myactivity.UserInfoActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.BaseActivity;
import com.example.kuangkuang.service.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends BaseActivity {
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private OkHttpClient httpClient = new OkHttpClient();
    private UserService userService = retrofitFactory.setRetrofit().create(UserService.class);
    ImageButton backButton;
    Button saveButton;
    EditText name;
    EditText sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.info_name);
        sex  = findViewById(R.id.info_sex);
        name.setText(BaseContext.getCurrentUser().name);
        sex.setText(BaseContext.getCurrentUser().getSex());
        saveButton = findViewById(R.id.info_save);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new MyClick());
        saveButton.setOnClickListener(new MyClick());
    }
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.back_button){
                finish();
            } else if (v.getId()==R.id.info_save) {
                if(sex.getText().toString().equals("男")||sex.getText().toString().equals("女")||sex.getText().toString().equals("")){
                    User user = new User();
                    user.setName(name.getText().toString());
                    user.setSex(sex.getText().toString().equals("男")?"1":"0");
                    user.setId((int) BaseContext.getCurrentId());
                    Call<Result> response = userService.update(user);
                    response.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Result result = response.body();
                            Log.d("updateUserInfo",result.getCode().toString());
                        }
                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.w("updateUserInfo","修改用户信息失败"+user.toString());
                        }
                    });

                }else{
                    Toast.makeText(UserInfoActivity.this, "性别输入错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}