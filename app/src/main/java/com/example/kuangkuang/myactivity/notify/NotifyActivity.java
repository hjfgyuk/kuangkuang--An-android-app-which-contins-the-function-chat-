package com.example.kuangkuang.myactivity.notify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kuangkuang.R;
import com.example.kuangkuang.entity.Notify;

public class NotifyActivity extends AppCompatActivity {
    private Intent intent;
    private Notify notify;
    private TextView title;
    private TextView content;
    private TextView name;
    private TextView time;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent = getIntent();
        notify = (Notify) intent.getSerializableExtra("notify");
        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        time = findViewById(R.id.notify_time);
        name = findViewById(R.id.notify_user);
        content = findViewById(R.id.notify_content);
        title = findViewById(R.id.notify_title);
        title.setText(notify.getTitle());
        //TODO 如何设置缩进
        content.setText(notify.getContent());
        name.setText(notify.getUserName());
        time.setText(notify.getTime());
    }
}