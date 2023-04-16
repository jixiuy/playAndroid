package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import okhttp3.Response;

/**
 * 网络请求的回调
 */
public interface ResponseListener {
    void response(Response response);
    void fail();
}
