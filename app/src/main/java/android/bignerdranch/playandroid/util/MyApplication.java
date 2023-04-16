package android.bignerdranch.playandroid.util;

import android.app.Application;

import org.litepal.LitePal;

/**
 * 用来在项目里随时获得上下文
 */
public class MyApplication extends Application {


    public static String title;
    public static boolean isCollectedHomePageTwo = false;
    public static boolean isTopPassage = false;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getApplicationContext());
        
    }
}
