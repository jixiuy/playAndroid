package android.bignerdranch.playandroid.bottomnlottie.HomePage.ProjectFragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目里面RecyclerView的适配器
 */
public class ProjectPassageAdapter extends RecyclerView.Adapter<ProjectPassageAdapter.ViewHolder> {

    final List<ProjectPassageBean.DataBean.DatasBean> mBeanList = new ArrayList<>();
    ProjectPassageBean passageBean;
    final Context context;
    final Activity mActivity;
    final boolean[] isCollected ;
    private static final String TAG = "ProjectPassageAdapter";

    ProjectPassageAdapter mAdapter;
    final MyProReceiver mReceiver;
    class MyProReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("我取消收藏了")){
                update(intent.getStringExtra("title"));
            }
        }

    }
    ViewHolder mHolder;
    private void update(String title) {
        int position = 0;
        String date2 = title.substring(0,8);

        for (int i  = 0 ;i<mBeanList.size();i++) {
            String date = mBeanList.get(i).getTitle().substring(0,8);

            if(date.equals(date2)){
                break;
            }
            position++;
        }
        isCollected[position] = false;

        mHolder.lottieAnimationView.setAnimation(R.raw.collect_black);
        mHolder.lottieAnimationView.setProgress((float) 0.25);
        mAdapter.notifyItemChanged(position);

    }



    public void setAdapter(ProjectPassageAdapter adapter) {
        mAdapter = adapter;
    }

    public ProjectPassageAdapter(ProjectPassageBean passageBean, Context context, Activity activity) {
        this.passageBean = passageBean;
        this.context = context;
        this.mActivity = activity;

        mBeanList.addAll(passageBean.getData().getDatas());
        isCollected = new boolean[passageBean.getData().getTotal()];
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter();
        filter.addAction("我取消收藏了");
        mReceiver=  new MyProReceiver();
        manager.registerReceiver(mReceiver,filter);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imitate_csdn_son_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.lottieAnimationView.setOnClickListener(view12 -> {

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

        viewHolder.view.setOnClickListener(view1 -> {
            Intent intent = new Intent(mActivity, DisplayActivity.class);
            intent.putExtra("url",mBeanList.get(viewHolder.getAdapterPosition()).getLink());
            mActivity.startActivity(intent);
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mHolder = holder;
        Log.d(TAG, "onBindViewHolder: "+isCollected[position]);
        if(mBeanList.get(position).getCollect()){
            holder.lottieAnimationView.setAnimation(R.raw.collect_yellow);
            holder.lottieAnimationView.playAnimation();
        }else {
            holder.lottieAnimationView.setAnimation(R.raw.collect_black);
            holder.lottieAnimationView.setProgress((float) 0.22);
        }
        isCollected[position] = mBeanList.get(position).getCollect();
        holder.passage.setText(mBeanList.get(position).getDesc());
        holder.author.setText(mBeanList.get(position).getAuthor());
        holder.date.setText(mBeanList.get(position).getNiceDate());
        holder.title.setText(mBeanList.get(position).getTitle());
        //在http协议下的图片很难加载不出来
        String url = mBeanList.get(position).getEnvelopePic();
        if(url.contains("https")){
        }else{
            url = url.replace("http","https");
        }
        Glide.with(context).load(url)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageView);
        //让其在直接失败的时候通过跳过缓存方式重试一次，没解决

        Log.d(TAG, "onBindViewHolder: "+url);

    }

    public void unCollect(ViewHolder viewHolder) {
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpUtil.unCollectPostRequestWithOkhttp( mBeanList.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                Log.d(TAG, "finish: "+mBeanList.get(viewHolder.getAdapterPosition()).getId().toString());
                mActivity.runOnUiThread(() -> ToastUtil.showToast("取消收藏成功",context, Toast.LENGTH_SHORT));

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);
    }

    public void collect(ViewHolder viewHolder){
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpUtil.collectInPostRequestWithOkhttp(mBeanList.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                Log.d(TAG, "finish: "+mBeanList.get(viewHolder.getAdapterPosition()).getId().toString());
                mActivity.runOnUiThread(() -> ToastUtil.showToast("收藏成功",context, Toast.LENGTH_SHORT));

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public void initDate(ProjectPassageBean passageBean) {
        mBeanList.clear();
        mBeanList.addAll(passageBean.getData().getDatas());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView title;
        final TextView date;
        final TextView passage;
        final TextView author;
        final LottieAnimationView lottieAnimationView;
        final ImageView imageView;
        final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_project);
            date = itemView.findViewById(R.id.date_project);
            passage = itemView.findViewById(R.id.passage_project);
            author = itemView.findViewById(R.id.author_project);
            lottieAnimationView = itemView.findViewById(R.id.project_lottie);
            imageView =itemView.findViewById(R.id.picture);
            view = itemView;
        }
    }

    public void updateDate(ProjectPassageBean passageBean){
        this.passageBean = passageBean;
        mBeanList.addAll(passageBean.getData().getDatas());

    }


}
