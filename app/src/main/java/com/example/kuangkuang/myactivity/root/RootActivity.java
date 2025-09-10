package com.example.kuangkuang.myactivity.root;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kuangkuang.R;
import com.example.kuangkuang.context.BaseContext;
import com.example.kuangkuang.entity.Result;
import com.example.kuangkuang.factory.BaseRetrofitFactory;
import com.example.kuangkuang.myactivity.BaseActivity;
import com.example.kuangkuang.myactivity.UserInfoActivity.UserInfoActivity;
import com.example.kuangkuang.service.UserService;
import com.example.kuangkuang.myactivity.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.kuangkuang.databinding.ActivityRootBinding;

import okhttp3.OkHttpClient;
import retrofit2.Call;

public class RootActivity extends BaseActivity {

private ActivityRootBinding binding;
private BaseRetrofitFactory retrofitFactory = new BaseRetrofitFactory();
private UserService userService = retrofitFactory.setRetrofit().create(UserService.class);
private OkHttpClient client = new  OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityRootBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
     setSupportActionBar(binding.toolbar);
     Toolbar toolbar = binding.toolbar;
     TextView textView = findViewById(R.id.toolbar_name);
     textView.setText(BaseContext.getCurrentUser().name);
     toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
         @Override
         public boolean onMenuItemClick(MenuItem item) {
             if(item.getItemId()==R.id.action_info){
                 //TODO 设置信息页面
                 Intent intent = new Intent(RootActivity.this, UserInfoActivity.class);
                 startActivity(intent);
             }
             else if (item.getItemId()==R.id.action_layout) {
                 Call<Result> response = userService.logout();
                 Intent logout = new Intent("LOGOUT");
                 sendBroadcast(logout);
                 Intent intent = new Intent(RootActivity.this, LoginActivity.class);
                 startActivity(intent);
             } else if (item.getItemId() == R.id.action_setting) {
                 Intent intent = new Intent(RootActivity.this, UserInfoActivity.class);
                 startActivity(intent);
             }
             return true;
         }
     });
        getSupportActionBar().setTitle("");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_root);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

}