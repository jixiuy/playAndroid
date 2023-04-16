package android.bignerdranch.playandroid.util;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Cookie的管理，在添加收藏功能的时候有用
 */
public class MyCookieJar implements CookieJar {

    public final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
        List<Cookie> cookies = cookieStore.get(httpUrl.host());
        return cookies != null?cookies : new ArrayList<>();
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
        cookieStore.put(httpUrl.host(),list);
    }
}
