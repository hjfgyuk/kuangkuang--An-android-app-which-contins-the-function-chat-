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
import com.example.kuangkuang.entity.Notify;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.message.MessageActivity;
import com.example.kuangkuang.myactivity.notify.NotifyActivity;
import com.example.kuangkuang.service.GroupService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyAdapter extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private Context context;
    private List<Notify> notifies;
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);
    OkHttpClient client = new  OkHttpClient();

    @Override
    public int getCount() {
        return notifies.size();
    }

    @Override
    public Object getItem(int position) {
        return notifies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public NotifyAdapter(Context context, List<Notify> notifies){
        this.context = context;
        this.notifies = notifies;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.notify_item,null);
            holder.notify_name = convertView.findViewById(R.id.notify_user);
            holder.notify_avatar = convertView.findViewById(R.id.notify_avatar);
            holder.notify_title = convertView.findViewById(R.id.notify_title);
            holder.notify_time = convertView.findViewById(R.id.notify_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Notify notify= notifies.get(position);
        holder.notify_name.setText(notify.getUserName());
        holder.notify_avatar.setImageResource(R.drawable.avatar_default);
        holder.notify_time.setText(notify.getTime());
        holder.notify_title.setText(notify.getTitle());
        return convertView;
    }
//TODO
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //进入通知页面
        Context context = view.getContext();
        Intent intent = new Intent(context, NotifyActivity.class);
        intent.putExtra("notify",notifies.get(position));
        context.startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return true;
    }

    public final class ViewHolder{
        public ImageView notify_avatar;
        public TextView notify_name;
        public TextView notify_title;
        public TextView notify_time;

    }

}
