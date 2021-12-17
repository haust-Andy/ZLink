package com.zzy.zlink.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.zzy.zlink.MyApplication;
import com.zzy.zlink.R;
import com.zzy.zlink.api.AccountInterface;
import com.zzy.zlink.bean.ResultUserInfo;
import com.zzy.zlink.bean.User;
import com.zzy.zlink.client.JWebSocketClient;
import com.zzy.zlink.sqlite.DatabaseHelper;

import org.java_websocket.client.WebSocketClient;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends MyActivity {
    MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getApplication();
        setContentView(R.layout.activity_login);
        init();


    }

    void init() {
        Button login_btn = findViewById(R.id.login_btn);
        Button register_btn = findViewById(R.id.register_btn);
        EditText account_number_edit = findViewById(R.id.account_number_edit);
        EditText account_password_edit = findViewById(R.id.account_password_edit);
        CheckBox save_password_cb = findViewById(R.id.save_password_cb);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setAccountNumber(account_number_edit.getText().toString());
                user.setAccountPassword(account_password_edit.getText().toString());
                loginVerification(user);
                if (save_password_cb.isChecked()) {
                    DatabaseHelper mStudentBaseHelper;
                    SQLiteDatabase mSqLiteDatabase;
                    mStudentBaseHelper = new DatabaseHelper(LoginActivity.this);
                    mSqLiteDatabase = mStudentBaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //插入数据
                    values.put("number", account_number_edit.getText().toString());
                    values.put("password", account_password_edit.getText().toString());
                    mSqLiteDatabase.insert("account_password_tb", null, values);
                    mSqLiteDatabase.close();
                }
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        initSavedData();

    }

    void loginVerification(User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        AccountInterface request = retrofit.create(AccountInterface.class);
        //对 发送请求 进行封装
        Call<ResultUserInfo> userCall = request.verifyLogin(user);
        userCall.enqueue(new Callback<ResultUserInfo>() {
            // Token
            //请求成功时回调
            @Override
            public void onResponse(Call<ResultUserInfo> call, Response<ResultUserInfo> response) {
                if (response.body().code == 200) {
                    User resUser = response.body().data;

                    SharedPreferences.Editor userDataEditor = LoginActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE).edit();
                    userDataEditor.putString("accountToken", resUser.getAccountToken()).apply();
                    userDataEditor.putString("accountNumber", resUser.getAccountNumber()).apply();
                    String accountName = resUser.getAccountName();
                    userDataEditor.putString("accountName", accountName).apply();
                    userDataEditor.putString("accountEmail", resUser.getAccountEmail()).apply();
//                    userDataEditor.putString("accountHead", Arrays.toString(resUser.getAccountHead())).apply();
                    userDataEditor.putString("accountHead", (resUser.getAccountHead())).apply();
                    Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();


                    myApplication.setWebSocketClient(accountName);
                    socketConnect();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultUserInfo> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initSavedData() {
        EditText account_number_edit = findViewById(R.id.account_number_edit);
        EditText account_password_edit = findViewById(R.id.account_password_edit);
        DatabaseHelper mStudentBaseHelper;
        SQLiteDatabase mSqLiteDatabase;
        mStudentBaseHelper = new DatabaseHelper(LoginActivity.this);
        mSqLiteDatabase = mStudentBaseHelper.getWritableDatabase();
        String[] strings = {"number", "password"};
        Cursor query = mSqLiteDatabase.query("account_password_tb", strings, null, null, null, null, null);
        if (query.moveToLast()) {
            query.getString(0);
            account_number_edit.setText(query.getString(0));
            account_password_edit.setText(query.getString(1));
        }
    }

    private void socketConnect() {
        try {
            WebSocketClient client = myApplication.webSocketClient;
            client.connectBlocking();
            Log.d("TAG", "socketConnect: 成功");
        } catch (InterruptedException e) {
            Log.d("TAG", "socketConnect: 失败");
            e.printStackTrace();
        }
    }

    private void closeConnect() {
        MyApplication myApplication = (MyApplication) getApplication();
        WebSocketClient client = myApplication.webSocketClient;
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

}
