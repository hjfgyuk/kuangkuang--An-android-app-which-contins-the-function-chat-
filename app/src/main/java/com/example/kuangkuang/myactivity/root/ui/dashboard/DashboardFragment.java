package com.example.kuangkuang.myactivity.root.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kuangkuang.adapter.FriendAdapter;
import com.example.kuangkuang.adapter.GroupAdapter;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.databinding.FragmentDashboardBinding;
import com.example.kuangkuang.databinding.FragmentHomeBinding;
import com.example.kuangkuang.entity.Friend;
import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.entity.User;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.UserService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class DashboardFragment extends Fragment {
    private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
    private UserService userService = retrofitFactory.setRetrofit().create(UserService.class);
    private OkHttpClient client = new  OkHttpClient();
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

    binding = FragmentDashboardBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
        Log.d("userid",String.valueOf(BaseContext.getCurrentId()));
        Call<Result<List<Friend>>> userCall = userService.getByUserId(Math.toIntExact(BaseContext.getCurrentId()));
        userCall.enqueue(new retrofit2.Callback<Result<List<Friend>>>() {
            @Override
            public void onResponse(Call<Result<List<Friend>>> call, retrofit2.Response<Result<List<Friend>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Friend> friends = response.body().getData();
                    FriendAdapter adapter = new FriendAdapter(getActivity(), friends);
                    ListView listView = binding.lvFriend;
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
                    listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) adapter);
                } else {
                    // 处理响应不成功的情况，可以通过 Log 打印错误信息
                    Log.e("HomeFragment", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Result<List<Friend>>> call, Throwable t) {
                // 网络请求失败
                Log.e("HomeFragment", "API call failed: " + t.getMessage());
            }
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}