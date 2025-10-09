package com.example.kuangkuang.entity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.GroupService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputDialog extends Dialog {
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);

    private EditText groupName;
    private EditText describe;
    private EditText number;
    private Button submit;
    public InputDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.inputdialog);
        groupName = findViewById(R.id.edit_text);
        describe = findViewById(R.id.edit_describe);
        number = findViewById(R.id.edit_maxNumber);
        submit = findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.button_submit){
                    String name = groupName.getText().toString();
                    Group group = new Group();
                    group.setName(name);
                    group.setDescription(describe.getText().toString());
                    group.setMaxNumber(number.getText().toString());
                    Call<Result> resultCall = groupService.create(group, (int) BaseContext.getCurrentId());
                    resultCall.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            Log.d("建立群聊",response.body().toString());
                            Toast.makeText(context, "建立成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Log.e("建立群聊",t.getMessage());
                            Toast.makeText(context, "建立失败", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }
            }
        });

    }



}
