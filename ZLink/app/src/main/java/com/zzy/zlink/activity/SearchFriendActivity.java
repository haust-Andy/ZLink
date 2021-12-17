package com.zzy.zlink.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzy.zlink.R;
import com.zzy.zlink.api.AccountInterface;
import com.zzy.zlink.api.FriendInterface;
import com.zzy.zlink.bean.ResultInfo;
import com.zzy.zlink.bean.ResultUserInfo;
import com.zzy.zlink.bean.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFriendActivity extends MyActivity {

    User friendUser=new User();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_friend);

        LinearLayout searched_user_liner = findViewById(R.id.searched_user_liner);
        searched_user_liner.setVisibility(View.INVISIBLE);
        ImageView back_img = findViewById(R.id.back_img);
        TextView search_tv = findViewById(R.id.search_tv);
        TextView friend_name_tv = findViewById(R.id.friend_name_tv);
        TextView friend_email_tv = findViewById(R.id.friend_email_tv);
        EditText search_number_et = findViewById(R.id.search_text_et);
        Button add_friend_btn=findViewById(R.id.add_friend_btn);
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchFriendActivity.this,HomeActivity.class));
            }
        });
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String searchText = search_number_et.getText().toString();
                if (!searchText.equals("")) {
                    Pattern pattern = Pattern.compile("[0-9]{1,}");
                    Matcher matcher = pattern.matcher((CharSequence) searchText);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl) //设置网络请求的Url地址
                            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    AccountInterface request = retrofit.create(AccountInterface.class);
                    String token = userData.getString("accountToken", "");

                    //搜索账号
                    if (matcher.matches())
                        friendUser.setAccountNumber(searchText);
                    else
                        friendUser.setAccountName(searchText);
                    Call<ResultUserInfo> userCall = request.searchUser(token, friendUser);
                    userCall.enqueue(new Callback<ResultUserInfo>() {
                        @Override
                        public void onResponse(Call<ResultUserInfo> call, Response<ResultUserInfo> response) {
                             friendUser = response.body().data;
                            if (friendUser != null) {
                                friend_name_tv.setText(friendUser.getAccountName());
                                friend_email_tv.setText(friendUser.getAccountEmail());
                                searched_user_liner.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SearchFriendActivity.this, "没有此用户！", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultUserInfo> call, Throwable t) {
                            Toast.makeText(SearchFriendActivity.this, "没有查到此用户！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        add_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String thisUserName=userData.getString("accountName","");
                if(thisUserName.equals(friendUser.getAccountName())){
                    Toast.makeText(SearchFriendActivity.this, "不能添加自己为好友！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl) //设置网络请求的Url地址
                        .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                FriendInterface request = retrofit.create(FriendInterface.class);
                String token = userData.getString("accountToken", "");


                Call<ResultInfo> userCall = request.addFriend(token, friendUser);
                userCall.enqueue(new Callback<ResultInfo>() {
                    @Override
                    public void onResponse(Call<ResultInfo> call, Response<ResultInfo> response) {
                        if(response.body().code==200)
                            Toast.makeText(SearchFriendActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                        else if(response.body().code==202)
                            Toast.makeText(SearchFriendActivity.this, "您已添加改用户为好友！", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SearchFriendActivity.this, "添加失败！111", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResultInfo> call, Throwable t) {
                        Toast.makeText(SearchFriendActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
