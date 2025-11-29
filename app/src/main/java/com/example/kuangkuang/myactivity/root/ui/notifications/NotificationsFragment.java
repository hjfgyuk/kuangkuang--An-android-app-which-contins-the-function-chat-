package com.example.kuangkuang.myactivity.root.ui.notifications;

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

import com.example.kuangkuang.R;
import com.example.kuangkuang.adapter.NotifyAdapter;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.databinding.FragmentNotificationsBinding;
import com.example.kuangkuang.entity.Notify;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.service.NotifyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

private FragmentNotificationsBinding binding;
private BaseRetrofitFactory factory = new BaseRetrofitFactory();
private NotifyService notifyService = factory.setRetrofit().create(NotifyService.class);
private NotifyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

    binding = FragmentNotificationsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    Call<Result<List<Notify>>> resultCall = notifyService.getNotifies((int) BaseContext.getCurrentId());
    resultCall.enqueue(new Callback<Result<List<Notify>>>() {
        @Override
        public void onResponse(Call<Result<List<Notify>>> call, Response<Result<List<Notify>>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Notify> notifies = response.body().getData();
                adapter = new NotifyAdapter(getActivity(), notifies);
                ListView listView = binding.notifyList;
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((AdapterView.OnItemClickListener) adapter) ;
                listView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener) adapter);
            } else {
                Log.e("NotificationsFragment", "Failed to fetch data: " + response.message());
            }
        }
        @Override
        public void onFailure(Call<Result<List<Notify>>> call, Throwable t) {
            Log.e("NotificationsFragment","API call failed"+t.getMessage());
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