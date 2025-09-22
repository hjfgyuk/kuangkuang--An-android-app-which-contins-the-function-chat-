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
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.UserInfoActivity.FriendInfoActivity;
import com.example.kuangkuang.myactivity.message.MessageActivity;
import com.example.kuangkuang.service.GroupService;
import com.example.kuangkuang.service.UserService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private Context context;
    private List<Friend> friends;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private UserService UserService = retrofitFactory.setRetrofit().create(UserService.class);
    OkHttpClient client = new  OkHttpClient();

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public FriendAdapter(Context context, List<Friend> groups){
        this.context = context;
        this.friends = groups;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendAdapter.ViewHolder holder = new FriendAdapter.ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.friend_item,null);
            holder.friend_name = convertView.findViewById(R.id.friend_name);
            holder.friend_avatar = convertView.findViewById(R.id.friend_avatar);
            convertView.setTag(holder);
        }else {
            holder = (FriendAdapter.ViewHolder) convertView.getTag();
        }
        Friend friend=friends.get(position);
        holder.friend_name.setText(friend.getFriendName());
        holder.friend_avatar.setImageResource(R.drawable.avatar_default);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        Intent intent = new Intent(context,  FriendInfoActivity.class);
        intent.putExtra("userName", friends.get(position).getFriendName());
        intent.putExtra("userId", friends.get(position).getUser2Id());
        intent.putExtra("isFriend",true);
        context.startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        retrofit2.Call<Result> result = UserService.deleteFriend(friends.get(position).getId());
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
        public ImageView friend_avatar;
        public TextView friend_name;


    }
}
