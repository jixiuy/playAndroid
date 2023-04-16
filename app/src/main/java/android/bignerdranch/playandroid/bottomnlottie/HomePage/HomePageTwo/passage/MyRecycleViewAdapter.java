package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.passage;

import android.app.Activity;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

/**
 * 首页文章的适配器
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private static final String TAG = "MyRecycleViewAdapter";
    final boolean[] isCollected ;
    final Context mContext;

    MyTopPassageBean topPassageBean;
    final Activity mActivity;


    ViewHolder mHolder;

    public MyRecycleViewAdapter(Context context, MyTopPassageBean passageBean, Activity activity) {
        mContext = context;
        topPassageBean = passageBean;
        this.mActivity = activity;
        isCollected = new boolean[passageBean.getData().size()];

    }

    public void setDate(MyTopPassageBean topPassageBean) {
        this.topPassageBean = topPassageBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.top_passage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"YangRegular.ttf");
        viewHolder.mAuthor.setTypeface(typeface);
        viewHolder.mSuperChapterName.setTypeface(typeface);
        viewHolder.mTitle.setTypeface(typeface);
        viewHolder.mChapterName.setTypeface(typeface);
        viewHolder.mNiceShareDate.setTypeface(typeface);

        viewHolder.view.setOnClickListener(view12 -> {
            Intent intent = new Intent(mContext, DisplayActivity.class);
            intent.putExtra("url",topPassageBean.getData().get(viewHolder.getAdapterPosition()).getLink());
            mContext.startActivity(intent);

        });

        viewHolder.lottieAnimationView.setOnClickListener(view1 -> {

            if(isCollected[viewHolder.getAdapterPosition()]){
                unCollect(viewHolder);
                viewHolder.lottieAnimationView.setAnimation(R.raw.collect_black);
                viewHolder.lottieAnimationView.setProgress((float) 0.25);
                isCollected[viewHolder.getAdapterPosition()] = false;
            }else {
                viewHolder.lottieAnimationView.setAnimation(R.raw.collect_yellow);
                viewHolder.lottieAnimationView.playAnimation();
                collect(viewHolder);
                isCollected[viewHolder.getAdapterPosition()] = true;
            }

        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        mHolder = viewHolder;
        if(topPassageBean.getData().get(position).getCollect()){
            viewHolder.lottieAnimationView.setAnimation(R.raw.collect_yellow);
            viewHolder.lottieAnimationView.playAnimation();
        }else {
            viewHolder.lottieAnimationView.setAnimation(R.raw.collect_black);
            viewHolder.lottieAnimationView.setProgress((float) 0.22);
        }
        isCollected[position] = topPassageBean.getData().get(position).getCollect();
        viewHolder.mAuthor.setText("作者:"+topPassageBean.getData().get(position).getAuthor());
        viewHolder.mSuperChapterName.setText(topPassageBean.getData().get(position).getSuperChapterName());
        viewHolder.mTitle.setText(topPassageBean.getData().get(position).getTitle());
        viewHolder.mChapterName.setText(topPassageBean.getData().get(position).getChapterName());
        viewHolder.mNiceShareDate.setText(topPassageBean.getData().get(position).getNiceShareDate());

    }

    @Override
    public int getItemCount() {
        return topPassageBean.getData().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mAuthor;
        private final TextView mSuperChapterName;
        private final TextView mTitle;
        private final TextView mChapterName;
        private final TextView mNiceShareDate;
        private final LottieAnimationView lottieAnimationView;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mSuperChapterName = itemView.findViewById(R.id.superChapterName);
            mTitle = itemView.findViewById(R.id.title);
            mChapterName = itemView.findViewById(R.id.chapterName);
            mNiceShareDate = itemView.findViewById(R.id.niceShareDate);
            lottieAnimationView = itemView.findViewById(R.id.lottie);
            view = itemView;
        }
    }
    public void unCollect(ViewHolder viewHolder) {
        SharedPreferences preferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpUtil.unCollectPostRequestWithOkhttp(topPassageBean.getData().get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                Log.d(TAG, "finish: "+topPassageBean.getData().get(viewHolder.getAdapterPosition()).getId().toString());
                mActivity.runOnUiThread(() -> ToastUtil.showToast("取消收藏成功",mContext, Toast.LENGTH_SHORT));

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);
    }

    public void collect(ViewHolder viewHolder){
        SharedPreferences preferences = mContext.getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpUtil.collectInPostRequestWithOkhttp(topPassageBean.getData().get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                Log.d(TAG, "finish: "+topPassageBean.getData().get(viewHolder.getAdapterPosition()).getId().toString());
                mActivity.runOnUiThread(() -> ToastUtil.showToast("收藏成功",mContext, Toast.LENGTH_SHORT));

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);
    }

}
