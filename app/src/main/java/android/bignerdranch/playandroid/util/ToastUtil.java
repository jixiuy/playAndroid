package android.bignerdranch.playandroid.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 用来弹出Toast的工具类
 */
public class ToastUtil {
    static Toast mToast;
    static public void showToast(String content, Context context,int duration){
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,content,duration);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }

    static public void showToastBottom(String content, Context context,int duration){
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,content,duration);
        mToast.show();
    }
}
