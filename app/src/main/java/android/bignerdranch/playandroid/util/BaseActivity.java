package android.bignerdranch.playandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * 所有活动的基类
 * 和 ActivityCollector 一起配合退出全部的活动
 */
public class BaseActivity extends AppCompatActivity {

    public static Context mContext;

    //获取天气接口里面要的TOKEN，不过没用了
    public static final String TOKEN = "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";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用来知道当前活动的名字
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        mContext = getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
