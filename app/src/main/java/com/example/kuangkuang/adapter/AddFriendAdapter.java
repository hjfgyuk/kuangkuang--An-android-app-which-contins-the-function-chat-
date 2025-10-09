package com.example.kuangkuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuangkuang.R;
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.UserInfoActivity.FriendInfoActivity;
import com.example.kuangkuang.service.UserService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
    private Context context;
    private List<Friend> friends;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private UserService UserService = retrofitFactory.setRetrofit().create(UserService.class);
    OkHttpClient client = new  OkHttpClient();
    private List<Integer> list = new ArrayList<>();

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
    public AddFriendAdapter(Context context, List<Friend> groups){
        this.context = context;
        this.friends = groups;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddFriendAdapter.ViewHolder holder = new AddFriendAdapter.ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.addfriend,null);
            holder.friend_name = convertView.findViewById(R.id.friend_name);
            holder.friend_avatar = convertView.findViewById(R.id.friend_avatar);
            holder.checkBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }else {
            holder = (AddFriendAdapter.ViewHolder) convertView.getTag();
        }
        Friend friend=friends.get(position);
        holder.friend_name.setText(friend.getFriendName());
        holder.friend_avatar.setImageResource(R.drawable.avatar_default);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            list.add(Integer.valueOf(friend.getUser2Id())); // 更新选中状态
        });
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Context context = view.getContext();
//        Intent intent = new Intent(context,  FriendInfoActivity.class);
//        intent.putExtra("userName", friends.get(position).getFriendName());
//        intent.putExtra("userId", friends.get(position).getUser2Id());
//        context.startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    public List<Integer> getSelectedUserIds() {
        return list;
    }

    public final class ViewHolder{
        public CheckBox checkBox;
        public ImageView friend_avatar;
        public TextView friend_name;


    }
}
