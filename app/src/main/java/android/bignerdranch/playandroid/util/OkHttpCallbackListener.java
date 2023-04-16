package android.bignerdranch.playandroid.util;

/**
 * 网络请求回调接口
 */
public interface OkHttpCallbackListener {
    void finish(String response);
    void onError(Exception e);
}
