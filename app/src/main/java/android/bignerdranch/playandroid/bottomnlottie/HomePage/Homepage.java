package android.bignerdranch.playandroid.bottomnlottie.HomePage;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.CollectFragment.CategoryFragment;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.HomePageFragment;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.RemoteVIew.DialogActivity;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.MineFragment.MineFragment;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.readapp.R;

import org.litepal.tablemanager.Connector;

import java.util.concurrent.TimeUnit;

/**
 * 最外层的活动界面叫Homepage
 * 这边设置了三个底部导航栏
 * 和用碎片管理器管理首页，收藏，我的三个碎片
 */
public class Homepage extends BaseActivity implements View.OnClickListener{

    //底部导航动画
    public LottieAnimationView tabHomePage;
    LottieAnimationView tabCategory;
    LottieAnimationView tabMy;
    public TextView homePage;
    TextView category;
    TextView my;
    public ConstraintLayout homePageLayout;
    ConstraintLayout categoryLayout;
    ConstraintLayout myLayout;

    //切换碎片
    public Fragment homePageFragment;
    Fragment categoryFragment;
    Fragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initView();
        sendMessage();

    }

    //界面疲劳提醒


    private void sendMessage() {


        //获取通知管理者
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //获取通知的构造者
        Notification.Builder builder = new Notification.Builder(this);

        //通知 android8.0 API26用新特性
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //通知渠道
            NotificationChannel channel1 = new NotificationChannel("TestId","channel1",NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setLightColor(Color.GREEN);
            //锁定屏幕时是否显示频道
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(channel1);
            builder.setChannelId("TestId");

        }

        //添加自定义通知视图
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.my_notification);
        //设置主要参数
        remoteViews.setTextViewText(R.id.tv_content,"玩安卓APP");
        remoteViews.setTextViewText(R.id.tv_time,"点击快速查询");

        builder.setContent(remoteViews);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        //点击自动取消
//        builder.setAutoCancel(true);

        //关闭通知
        Intent intent = new Intent(this, DialogActivity.class);
        PendingIntent pendingIntent;

        pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_IMMUTABLE);

        Intent intent1 = new Intent(this,Homepage.class);
        PendingIntent pendingIntent1;

        pendingIntent1 = PendingIntent.getActivity(this,0,intent1, PendingIntent.FLAG_IMMUTABLE);

        remoteViews.setOnClickPendingIntent(R.id.jump,pendingIntent1);
        builder.setContentIntent(pendingIntent);
        //更新通知 发送通知
        notificationManager.notify(1,builder.build());

        request = new OneTimeWorkRequest.Builder(SimpleWorker.class)
                .setInitialDelay(30, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(this).enqueue(request);
    }

    OneTimeWorkRequest request;

    boolean isFirst = false;
    @Override
    protected void onResume() {
        super.onResume();
        if(isFirst){
            WorkManager.getInstance(this).cancelAllWork();
        }
        isFirst = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance(this).cancelAllWork();
    }

    private void initView() {
        //底部导航动画
        tabHomePage = findViewById(R.id.tabHomePage);
        tabCategory = findViewById(R.id.tabCategory);
        tabMy = findViewById(R.id.tabMy);
        homePage = findViewById(R.id.homePage);
        category = findViewById(R.id.category);
        my = findViewById(R.id.my);
        homePageLayout = findViewById(R.id.homePageLayout);
        categoryLayout = findViewById(R.id.categoryLayout);
        myLayout = findViewById(R.id.myLayout);

        homePageLayout.setOnClickListener(this);
        categoryLayout.setOnClickListener(this);
        myLayout.setOnClickListener(this);
        tabHomePage.setOnClickListener(this);
        tabCategory.setOnClickListener(this);
        tabMy.setOnClickListener(this);
        homePage.setOnClickListener(this);
        category.setOnClickListener(this);
        my.setOnClickListener(this);

        tabHomePage.setAnimation(R.raw.home_pressed);
        tabCategory.setAnimation(R.raw.category_pressed);
        tabMy.setAnimation(R.raw.mine_pressed);
        tabHomePage.setSpeed(1.4f);
        tabCategory.setSpeed(1.4f);
        tabMy.setSpeed(1.4f);
        setSelected(R.id.homePage,"#FFC13B");
        setUnSelected(1);

        //切换碎片
        homePageFragment = new HomePageFragment();
        categoryFragment = new CategoryFragment();
        mineFragment = new MineFragment();
        changeFragment(homePageFragment);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //底部导航动画 + 切换碎片
            case R.id.homePage:
            case R.id.homePageLayout:
            case R.id.tabHomePage:{
                setSelected(R.id.homePage,"#FFC13B");
                setUnSelected(1);
                changeFragment(homePageFragment);

            }break;

            case R.id.category:
            case R.id.categoryLayout:
            case R.id.tabCategory:{
                setSelected(R.id.category,"#8A8BF2");
                setUnSelected(2);
                changeFragment(categoryFragment);
            }break;

            case R.id.my:
            case R.id.myLayout:
            case R.id.tabMy:{
                setSelected(R.id.my,"#00A0FF");
                setUnSelected(3);
                changeFragment(mineFragment);
            }break;



        }
    }

    //底部导航动画
    public void setSelected(int id,String colorRes){
        switch (id){
            case R.id.homePage:
            case R.id.homePageLayout:
            case R.id.tabHomePage:{
                tabHomePage.playAnimation();
                homePage.setTextColor(Color.parseColor(colorRes));
            }break;

            case R.id.category:
            case R.id.categoryLayout:
            case R.id.tabCategory:{
                tabCategory.playAnimation();
                category.setTextColor(Color.parseColor(colorRes));
            }break;

            case R.id.my:
            case R.id.myLayout:
            case R.id.tabMy:{
                tabMy.playAnimation();
                my.setTextColor(Color.parseColor(colorRes));
            }break;

        }
    }

    public void setUnSelected(int id){
        switch (id){
            case 1:{
                category.setTextColor(Color.BLACK);
                my.setTextColor(Color.BLACK);

                tabCategory.cancelAnimation();
                tabMy.cancelAnimation();
                tabCategory.setProgress(0);
                tabMy.setProgress(0);

            }break;
            case 2:{
                homePage.setTextColor(Color.BLACK);
                my.setTextColor(Color.BLACK);
                tabHomePage.cancelAnimation();
                tabHomePage.setProgress(0);
                tabMy.cancelAnimation();
                tabMy.setProgress(0);
            }break;
            case 3:{

                homePage.setTextColor(Color.BLACK);
                category.setTextColor(Color.BLACK);
                tabHomePage.cancelAnimation();
                tabHomePage.setProgress(0);
                tabCategory.cancelAnimation();
                tabCategory.setProgress(0);

            }break;
        }

    }

    //切换碎片
    public void changeFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.addToBackStack("back");
        transaction.commit();
    }

    //创建toolBar menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}