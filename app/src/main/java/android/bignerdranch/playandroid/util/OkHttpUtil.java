package android.bignerdranch.playandroid.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装了不同的发起网络请求的功能
 */
public class OkHttpUtil {
    public static void unCollectPostRequestWithOkhttp(String id,OkHttpCallbackListener listener,String account ,String password){
        URL url;
        try {
            url = new URL("https://www.wanandroid.com/lg/uncollect_originId/"+id+"/json");
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .addHeader("Cookie",account)
                    .addHeader("Cookie",password)
                    .url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseDate = response.body().string();
                    listener.finish(responseDate);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void unCollectPostRequestWithOkhttp(String id,String originId,OkHttpCallbackListener listener,String account ,String password){
        URL url;
        try {
            url = new URL("https://www.wanandroid.com/lg/uncollect/"+id+"/json");
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
            RequestBody requestBody = new FormBody.Builder()
                    .add("originId",originId)
                    .build();
            Request request = new Request.Builder()
                    .addHeader("Cookie",account)
                    .addHeader("Cookie",password)
                    .url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseDate = response.body().string();
                    listener.finish(responseDate);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void collectInPostRequestWithOkhttp(String id,OkHttpCallbackListener listener,String account,String password){
        URL url;
        try {
            url = new URL("https://www.wanandroid.com/lg/collect/"+id+"/json");
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
            RequestBody requestBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .addHeader("Cookie",account)
                    .addHeader("Cookie",password)
                    .url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseDate = response.body().string();
                    listener.finish(responseDate);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public static void collectOutPostRequestWithOkhttp(final String address,String title,String author,String link,OkHttpCallbackListener listener){
        URL url;
        try {
            url = new URL(address);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("title",title)
                    .add("author",author)
                    .add("link",link)
                    .build();
            Request request = new Request.Builder()
                    .url(url).post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    listener.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String responseDate = response.body().string();
                    listener.finish(responseDate);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void sendGetRequestWithOkHttp(final String address, final OkHttpCallbackListener listener) {
            try {
                URL url = new URL(address);
                OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        listener.finish(response.body().string());
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
                Log.d("hello", "连接失败 ");
            }

    }

    public static void sendGetRequestWithOkHttp(final String address, final OkHttpCallbackListener listener,String account,String password) {
        new Thread(() -> {

            try {
                URL url = new URL(address);
                OkHttpClient client =  new OkHttpClient.Builder()
                        .cookieJar(new MyCookieJar()).build();

                Request request = new Request.Builder()
                        .addHeader("Cookie","loginUserName="+account)
                        .addHeader("Cookie","loginUserPassword="+password)
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        listener.finish(response.body().string());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }


        }).start();
    }

    public static void sendSignInRequestWithOkhttp(final String address,final OkHttpCallbackListener listener,String username,String password){
        new Thread(() -> {
            try {
                URL url = new URL(address);
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password",password)
                        .build();
                Request request = new Request.Builder()
                        .url(url).post(requestBody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseDate = response.body().string();
                        listener.finish(responseDate);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendSignInRequestWithOkhttp(final String address,final OkHttpCallbackListener listener,String username,String password,String repeatPassword){
        new Thread(() -> {
            try {
                URL url = new URL(address);
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password",password)
                        .add("repassword",repeatPassword)
                        .build();
                Request request = new Request.Builder()
                        .url(url).post(requestBody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseDate = response.body().string();
                        listener.finish(responseDate);
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }


}
