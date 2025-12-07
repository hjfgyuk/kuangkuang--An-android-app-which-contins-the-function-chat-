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
import com.example.kuangkuang.entity.Message;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.UserInfoActivity.FriendInfoActivity;
import com.example.kuangkuang.service.GroupService;

import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Context context;
    private List<Message> messages;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);
    private int ITEM_SELF = 1;
    private int ITEM_OTHER = 0;
    private long dragon;

    public void setDragon(long dragon) {
        this.dragon = dragon;
    }
    OkHttpClient client = new  OkHttpClient();

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public MessageAdapter(Context context, List<Message> messages){
        this.context = context;
        this.messages = messages;
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        MessageAdapter.ViewHolder holder = new ViewHolder();
//        int itemType = getItemViewType(position);
//        if(itemType==ITEM_SELF)
//            convertView = LayoutInflater.from(context).inflate(R.layout.message_myitem,null);
//        else convertView = LayoutInflater.from(context).inflate(R.layout.message_item,null);
//        if(convertView==null){
//            holder.message_name = convertView.findViewById(R.id.message_name);
//            holder.message_avatar = convertView.findViewById(R.id.message_avatar);
//            holder.message_text = convertView.findViewById(R.id.message_text);
//            convertView.setTag(holder);
//        }else {
//            holder = (MessageAdapter.ViewHolder) convertView.getTag();
//        }
//        Message message=messages.get(position);
//        holder.message_name.setText(message.getUserName());
//        holder.message_avatar.setImageResource(R.drawable.avatar_default);
//        holder.message_text.setText(message.getMessage());
//        return convertView;
//    }
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    int itemType = getItemViewType(position);

    // 检查convertView是否为null或者类型不匹配
    if (convertView == null|| (Integer) convertView.getTag(R.id.view_type) != itemType) {
        holder = new ViewHolder();

        // 根据消息类型加载不同的布局
        if (itemType==ITEM_SELF) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_myitem, parent, false);
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        }

        // 初始化视图组件
        holder.message_name = convertView.findViewById(R.id.message_name);
        holder.message_avatar = convertView.findViewById(R.id.message_avatar);
        holder.message_text = convertView.findViewById(R.id.message_text);

        // 设置视图类型标签以便复用检查
        convertView.setTag(R.id.view_type, itemType);
        convertView.setTag(holder);
    } else {
        // 复用现有的视图和ViewHolder
        holder = (ViewHolder) convertView.getTag();
    }

    // 获取消息数据
    Message message = messages.get(position);

    // 设置视图内容 - 添加空值检查
    if (holder.message_name != null) {
        if(!Objects.isNull(dragon))
        holder.message_name.setText(message.getUserId()==dragon?message.getUserName()+"🐲":message.getUserName());
        else holder.message_name.setText(message.getUserName());
    }

    if (holder.message_avatar != null) {
        holder.message_avatar.setImageResource(R.drawable.avatar_default);
    }

    if (holder.message_text != null) {
        holder.message_text.setText(message.getMessage());
    }
    if (holder.message_avatar != null) {
        holder.message_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动好友信息页面
                Intent intent = new Intent(context, FriendInfoActivity.class);
                intent.putExtra("userName", message.getUserName());
                // 如果需要，可以传递更多信息
                intent.putExtra("userId", message.getUserId());
                context.startActivity(intent);
            }
        });
    }
    return convertView;
}
    @Override
    public int getItemViewType(int position) {
         Message message = messages.get(position);
         int id = message.getUserId();
         if(id== BaseContext.getCurrentId()){
             return ITEM_SELF;
         }else {
             return ITEM_OTHER;
         }
    }
    public void addItem(Message message) {
        messages.add(message); // messages是适配器中的数据列表
        notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        retrofit2.Call<Result> result = groupService.delete(messages.get(position).getId());
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
        //TODO 是否要在这里存储用户id
        public ImageView message_avatar;
        public TextView message_name;
        public TextView message_text;


    }

}
