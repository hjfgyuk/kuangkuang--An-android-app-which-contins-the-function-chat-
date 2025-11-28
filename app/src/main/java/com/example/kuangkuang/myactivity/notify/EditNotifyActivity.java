package com.example.kuangkuang.myactivity.notify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.example.kuangkuang.entity.Notify;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.NotifyService;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNotifyActivity extends AppCompatActivity {
    private Intent intent;
    private BaseRetrofitFactory factory = new BaseRetrofitFactory();
    private NotifyService notifyService  = factory.setRetrofit().create(NotifyService.class);
    private ImageButton back;
    private EditText title;
    private EditText content;
    private DatePicker picker;
    private Button send;
    private int groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_notify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        groupId = Integer.parseInt(intent.getStringExtra("groupId"));
        title = findViewById(R.id.notify_title);
        content = findViewById(R.id.notify_content);
        back = findViewById(R.id.back_button);
        picker = findViewById(R.id.date_picker);
        send = findViewById(R.id.notify_send);
        back.setOnClickListener(new myClick());
        send.setOnClickListener(new myClick());
    }
    public class myClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.back_button){
                finish();
            }
            if (v.getId()==R.id.notify_send){
                String t = title.getText().toString();
                String c = content.getText().toString();
                String deadline = getDate();
                Notify notify = new Notify();
                notify.setContent(c);
                notify.setTitle(t);
                notify.setDeadline(deadline);
                notify.setGroupId(groupId);
                notify.setUserId((int) BaseContext.getCurrentId());
                notify.setUserName(BaseContext.getCurrentUser().name);
                Call<Result> resultCall = notifyService.addNotify(notify);
                resultCall.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(EditNotifyActivity.this, "通知发布成功", Toast.LENGTH_SHORT).show();
                            Log.d("notify",response.toString());
                        }else {
                            Log.w("notify",response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.e("notify",t.getMessage());
                    }
                });
            }
        }
    }
    public String getDate(){
        int year = picker.getYear();
        int month = picker.getMonth();
        int day = picker.getDayOfMonth();
        return String.format(Locale.getDefault(),"%d-%02d-%02d",year,month+1,day);
    }
}