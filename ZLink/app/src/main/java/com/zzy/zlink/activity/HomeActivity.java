package com.zzy.zlink.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.zzy.zlink.R;
import com.zzy.zlink.adapter.FragAdapter;
import com.zzy.zlink.api.ConversationInterface;
import com.zzy.zlink.api.FriendInterface;
import com.zzy.zlink.bean.ResultAllConversation;
import com.zzy.zlink.bean.ResultFriends;
import com.zzy.zlink.fragment.DynamicFragment;
import com.zzy.zlink.fragment.FriendFragment;
import com.zzy.zlink.fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends MyActivity {
    private GestureDetector gestureDetector;
    ResultAllConversation resultAllConversation;
    ResultFriends resultFriends;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        init();
    }

    private void initData() {
        TextView account_name = findViewById(R.id.account_name_tv);
        ImageView head_img = findViewById(R.id.head_img);
        SharedPreferences userData = this.getSharedPreferences("userData", Context.MODE_PRIVATE);

        account_name.setText(userData.getString("accountName", ""));
//        String head_str = userData.getString("accountHead", "");
//        if (head_str != null) {
//
//            byte[] decode = Base64.decode(head_str, Base64.DEFAULT);
//            String s = decode.toString();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//            head_img.setImageBitmap(bitmap);
//        }
        initConversation();
        initFriend();
    }

    private void initConversation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ConversationInterface request = retrofit.create(ConversationInterface.class);
        SharedPreferences userData = HomeActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        String token=userData.getString("accountToken", "");
        Call<ResultAllConversation> userCall = request.getAllConversation(userData.getString("accountToken", ""));
        userCall.enqueue(new Callback<ResultAllConversation>() {

            @Override
            public void onResponse(Call<ResultAllConversation> call, Response<ResultAllConversation> response) {
                resultAllConversation = response.body();
                replaceFragment(new MessageFragment(resultAllConversation.data));
            }

            @Override
            public void onFailure(Call<ResultAllConversation> call, Throwable t) {
            }
        });
    }

    private void initFriend() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        FriendInterface request = retrofit.create(FriendInterface.class);
        SharedPreferences userData = HomeActivity.this.getSharedPreferences("userData", Context.MODE_PRIVATE);
        Call<ResultFriends> userCall = request.getFriend(userData.getString("accountToken", ""));
        userCall.enqueue(new Callback<ResultFriends>() {
            @Override
            public void onResponse(Call<ResultFriends> call, Response<ResultFriends> response) {
                resultFriends = response.body();
            }

            @Override
            public void onFailure(Call<ResultFriends> call, Throwable t) {
            }
        });
    }

    private void init() {
        gestureDetector = new GestureDetector(HomeActivity.this, onGestureListener);
        replaceFragment(new FriendFragment());
        RelativeLayout home_relativeLayout = findViewById(R.id.home_relativeLayout);
        LinearLayout message_liner = findViewById(R.id.message_liner);
        LinearLayout friend_liner = findViewById(R.id.friend_liner);
        LinearLayout dynamic_liner = findViewById(R.id.dynamic_liner);
        TextView home_text_tv = findViewById(R.id.home_text_tv);

        ImageView head_img = findViewById(R.id.head_img);
        ImageView search_img = findViewById(R.id.search_img);

        ImageView message_img = findViewById(R.id.message_img);
        ImageView friend_img = findViewById(R.id.friend_img);
        ImageView dynamic_img = findViewById(R.id.dynamic_img);


        message_liner.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new MessageFragment(resultAllConversation.data));
                home_text_tv.setText("消息");
                message_img.setImageResource(R.drawable.ic_message_c);
                friend_img.setImageResource(R.drawable.ic_friend);
                dynamic_img.setImageResource(R.drawable.ic_dynamic);
            }
        });
        friend_liner.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new FriendFragment(resultFriends.data));
                home_text_tv.setText("好友");
                message_img.setImageResource(R.drawable.ic_message);
                friend_img.setImageResource(R.drawable.ic_friend_c);
                dynamic_img.setImageResource(R.drawable.ic_dynamic);
            }
        });
        dynamic_liner.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                replaceFragment(new DynamicFragment());
                home_text_tv.setText("动态");
                message_img.setImageResource(R.drawable.ic_message);
                friend_img.setImageResource(R.drawable.ic_friend);
                dynamic_img.setImageResource(R.drawable.ic_dynamic_c);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PersonalInfoActivity.class));
            }
        };
        head_img.setOnClickListener(listener);
        home_text_tv.setOnClickListener(listener);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchFriendActivity.class));
            }
        });

    }

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();
                    if (x > 0)
                        startActivity(new Intent(HomeActivity.this, PersonalInfoActivity.class));
                    return true;
                }
            };

    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    //主界面替换
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.replace(R.id.home_main_fragment, fragment);
        transaction.commit();
    }

    //返回键返回桌面
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
    }


}
