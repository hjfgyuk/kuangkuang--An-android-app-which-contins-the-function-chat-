package com.example.kuangkuang.sign;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.root.RootActivity;
import com.example.kuangkuang.ui.login.LoginFormState;
import com.example.kuangkuang.ui.login.LoginViewModel;
import com.example.kuangkuang.ui.login.LoginViewModelFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class SignActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private JsonObject result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign);

        final EditText nameEdit = findViewById(R.id.sa_username);
        final TextView tvResult = findViewById(R.id.sa_result);
        final EditText passwordEdit = findViewById(R.id.sa_password);
        final Button signButton = findViewById(R.id.sign);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                signButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    nameEdit.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEdit.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEdit.getText().toString()==""){
                    Toast.makeText(SignActivity.this, "用户名不可为空", Toast.LENGTH_SHORT).show();
                }
                else if (passwordEdit.getText().toString()==""){
                    Toast.makeText(SignActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(nameEdit.getText().toString().length()<5||passwordEdit.getText().toString().length()<6){
                    Toast.makeText(SignActivity.this, "用户名长度应不小于5，密码长度不小于6", Toast.LENGTH_SHORT).show();
                }
                else {

                            String name = nameEdit.getText().toString();
                            String password = passwordEdit.getText().toString();
                            MediaType type = MediaType.parse("application/json;charset=utf-8");

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("name", name);
                                jsonObject.put("password", password);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            RequestBody requestBody = RequestBody.create(jsonObject.toString(),type);
                            OkHttpClient okHttpClient = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:8080/user/sign")
                                    .post(requestBody)
                                    .build();

                            Log.d("注册", request.url().toString() + request.body().toString());
                            Call call = okHttpClient.newCall(request);

                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    runOnUiThread(()->Log.e("注册",e.getMessage()));
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    String jsonData = response.body().string(); // 获取响应体字符串
                                    result = JsonParser.parseString(jsonData).getAsJsonObject(); // 解析JSON
                                    int code = result.get("code").getAsInt();
                                    String text;
                                    if (code==1) {
                                        text ="注册成功" ;
                                        BaseContext.setCurrentId(result.get("id").getAsLong());
                                    } else if (code==0) {
                                      text ="用户名已存在";
                                    } else {
                                        text = "";
                                    }
                                    runOnUiThread(()->{tvResult.setText(text);Log.d("注册",jsonData);if(code==1){
                                        Intent intent = new Intent(SignActivity.this, RootActivity.class);
                                        startActivity(intent);
                                    }
                                    });
                                }
                            });

                        }
                }
        });
    }

}