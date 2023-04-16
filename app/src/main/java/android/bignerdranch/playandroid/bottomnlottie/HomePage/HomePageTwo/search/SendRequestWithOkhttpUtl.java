package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用来发起网络请求，写的比较那个啥忘记了封装过网络请求的工具类，后来想起来了，就用用具类了
 */
public class SendRequestWithOkhttpUtl {



    public static void sendRequestWithOkhttp(ResponseListener responseListener, String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                responseListener.fail();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                responseListener.response(response);
            }
        });
    }
}
