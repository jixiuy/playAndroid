package android.bignerdranch.playandroid.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：退出所有的activity
 */
public class ActivityCollector {
    public static final List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

}
