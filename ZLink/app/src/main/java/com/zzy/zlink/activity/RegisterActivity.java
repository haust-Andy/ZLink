package com.zzy.zlink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzy.zlink.R;
import com.zzy.zlink.api.AccountInterface;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends MyActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        register();
    }

    private void register() {
        EditText register_number_et = findViewById(R.id.register_number_et);
        EditText register_password_et = findViewById(R.id.register_password_et);
        EditText register_confirm_password_et = findViewById(R.id.register_confirm_password_et);
        EditText register_email_et = findViewById(R.id.register_email_et);
        Button confirm_btn = findViewById(R.id.confirm_btn);
        Button cancel_btn = findViewById(R.id.cancel_btn);
        EditText register_name_et = findViewById(R.id.register_name_et);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String register_number = register_number_et.getText().toString();
                String register_password = register_password_et.getText().toString();
                String register_confirm_password = register_confirm_password_et.getText().toString();
                String register_email = register_email_et.getText().toString();
                String register_name = register_name_et.getText().toString();
                if (register_number.equals(""))
                    Toast.makeText(RegisterActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                else if (register_password.equals("") || !register_password.equals(register_confirm_password))
                    Toast.makeText(RegisterActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                else {
                    User user = new User();
                    user.setAccountName(register_name);
                    user.setAccountNumber(register_number);
                    user.setAccountEmail(register_email);
                    user.setAccountPassword(register_password);
                    Log.d("TAG", "onClick: " + user);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl) //设置网络请求的Url地址
                            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    // 创建 网络请求接口 的实例
                    AccountInterface request = retrofit.create(AccountInterface.class);
                    //对 发送请求 进行封装
                    Call<ResultInfo> userCall = request.registerAccount(user);
                    userCall.enqueue(new Callback<ResultInfo>() {
                        //请求成功时回调
                        @Override
                        public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                            if (response.body().code == 200) {
                                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }else if(response.body().code==401) {
                                Toast.makeText(RegisterActivity.this, "账号已存在！", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResultInfo> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "未知原因，注册失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }
}