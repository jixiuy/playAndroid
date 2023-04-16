package android.bignerdranch.playandroid.bottomnlottie.HomePage;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Jetpack组件WorkManager
 * 用来定时操作
 */
public class SimpleWorker extends Worker {

    private Context mContext;

    public SimpleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        Intent intent = new Intent(mContext,RemainDerDialog.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        return Result.success();
    }
}
