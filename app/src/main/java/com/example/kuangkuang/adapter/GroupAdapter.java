package com.example.kuangkuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.message.MessageActivity;
import com.example.kuangkuang.service.GroupService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Context context;
    private List<Group> groups;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);
    OkHttpClient client = new  OkHttpClient();

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public GroupAdapter(Context context, List<Group> groups){
        this.context = context;
        this.groups = groups;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.group_item,null);
            holder.group_name = convertView.findViewById(R.id.group_name);
            holder.group_avatar = convertView.findViewById(R.id.group_avatar);
            holder.group_unread = convertView.findViewById(R.id.group_unread);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Group group=groups.get(position);
        holder.group_name.setText(group.getName());
        holder.group_avatar.setImageResource(R.drawable.avatar_default);
        holder.group_unread.setText("未读消息："+ group.getMessageUnread());
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("groupId", groups.get(position).getId());
        intent.putExtra("userId", BaseContext.getCurrentId());
        context.startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        retrofit2.Call<Result> result = groupService.delete(groups.get(position).getId());
        result.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.d("delete","删除成功"+call.toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.w("delete","删除失败"+call.toString());
            }
        });
        return true;
    }

    public final class ViewHolder{
        public ImageView group_avatar;
        public TextView group_name;
        public TextView group_unread;

    }

}
