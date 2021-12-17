package com.zzy.zlink.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzy.zlink.R;
import com.zzy.zlink.api.AccountInterface;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.ResultUserInfo;
import com.zzy.zlink.bean.User;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initData();
    }
    private void initData(){
        EditText account_number_edit = findViewById(R.id.account_number_edit);
        EditText account_name_edit = findViewById(R.id.account_name_edit);
        EditText account_email_edit = findViewById(R.id.account_email_edit);
        findViewById(R.id.back_img).setOnClickListener(finishClickListener);
        findViewById(R.id.cencel_btn).setOnClickListener(finishClickListener);
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        account_number_edit.setText(userData.getString("accountNumber",""));
        account_name_edit.setText(userData.getString("accountName",""));
        account_email_edit.setText(userData.getString("accountEmail",""));
        setClickEvent();
    }
    private void setClickEvent(){
        EditText account_name_edit = findViewById(R.id.account_name_edit);
        EditText account_email_edit = findViewById(R.id.account_email_edit);
        Button save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=account_name_edit.getText().toString();
                String email=account_email_edit.getText().toString();
                User user=new User();
                user.setAccountName(name);
                user.setAccountEmail(email);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl) //设置网络请求的Url地址
                        .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                AccountInterface request = retrofit.create(AccountInterface.class);
                //对 发送请求 进行封装
                SharedPreferences userData = UserInfoActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE);
                Call<ResultInfo> userCall = request.updateAccount(userData.getString("accountToken",""),user);
                userCall.enqueue(new Callback<ResultInfo>() {
                    @Override
                    public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {

                        if (response.body().code == 200) {
                            Toast.makeText(UserInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = UserInfoActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE).edit();
                            editor.putString("accountName",user.getAccountName()).apply();
                            editor.putString("accountEmail",user.getAccountEmail()).apply();
                            startActivity(new Intent(UserInfoActivity.this,HomeActivity.class));
                        } else {
                            Toast.makeText(UserInfoActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultInfo> call, Throwable t) {
                    }
                });
            }
        });
    }
}