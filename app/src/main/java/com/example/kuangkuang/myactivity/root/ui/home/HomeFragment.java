package com.example.kuangkuang.myactivity.root.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kuangkuang.adapter.GroupAdapter;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.databinding.FragmentHomeBinding;
import com.example.kuangkuang.entity.Group;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.GroupService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;
private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
private GroupService groupService = retrofitFactory.setRetrofit().create(GroupService.class);
private OkHttpClient client = new  OkHttpClient();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
        Log.d("userid",String.valueOf(BaseContext.getCurrentId()));
        Call<Result<List<Group>>> groupCall = groupService.getByGroupId(Math.toIntExact(BaseContext.getCurrentId()));
        groupCall.enqueue(new retrofit2.Callback<Result<List<Group>>>() {
            @Override
            public void onResponse(Call<Result<List<Group>>> call, retrofit2.Response<Result<List<Group>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Group> groups = response.body().getData();
                    GroupAdapter adapter = new GroupAdapter(getActivity(), groups);
                    ListView listView = binding.lvGroup;
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
                    listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) adapter);
                } else {
                    // 处理响应不成功的情况，可以通过 Log 打印错误信息
                    Log.e("HomeFragment", "Failed to fetch data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Result<List<Group>>> call, Throwable t) {
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