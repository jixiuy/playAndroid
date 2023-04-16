package android.bignerdranch.playandroid.bottomnlottie.HomePage.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.Homepage;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.readapp.R;

/**
 * 桌面小组件
 */
public class MyWidget extends AppWidgetProvider {
    private static final String TAG = "MyWidget";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: ");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        Intent intent = new Intent(context,Homepage.class);
        intent.setAction("jump");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_IMMUTABLE);
        remoteView.setOnClickPendingIntent(R.id.my_widget,pendingIntent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}