package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result;

import android.app.Activity;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

/**
 * RecycleView的适配器
 */
public class MySearchedPassageAdapter extends RecyclerView.Adapter<MySearchedPassageAdapter.ViewHolder> {

    final Context context;
    List<PassageBean.DataBean.DatasBean> beans;
    String key;
    MySearchedPassageAdapter mMySearchedPassageAdapter;
    boolean[] isCollected ;
    final Activity mActivity;

    //刷新数据和适配器视图
    public void setData(List<PassageBean.DataBean.DatasBean> beans,String key){
        this.beans = beans;
        this.key =key;
        isCollected = new boolean[beans.size()];
    }



    public MySearchedPassageAdapter(Context context,List<PassageBean.DataBean.DatasBean> beans,String key,Activity activity) {
        this.context = context;
        this.beans = beans;
        this.key = key;
        this.mActivity = activity;
        isCollected = new boolean[beans.size()];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.passage_rl,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.lottie.setOnClickListener(view12 -> {
            if(isCollected[holder.getAdapterPosition()]){
                unCollect(holder);
                holder.lottie.setAnimation(R.raw.collect_black);
                holder.lottie.setProgress((float) 0.25);
                isCollected[holder.getAdapterPosition()] = false;
            }else {
                holder.lottie.setAnimation(R.raw.collect_yellow);
                holder.lottie.playAnimation();
                collect(holder);
                isCollected[holder.getAdapterPosition()] = true;
            }
        });
        holder.triangle.setImageResource(R.drawable.sanjiaoxing_3);
        holder.category.setImageResource(R.drawable.fenlei);
        holder.view.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, DisplayActivity.class);
            intent.putExtra("url",beans.get(holder.getAdapterPosition()).getLink());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
        return holder;
    }

    private static final String TAG = "MySearchedPassageAdapte";
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        isCollected[position] = beans.get(position).getCollect();

        if(beans.get(position).getCollect()){
            holder.lottie.setAnimation(R.raw.collect_yellow);
            holder.lottie.playAnimation();
        }else {
            holder.lottie.setAnimation(R.raw.collect_black);
            holder.lottie.setProgress((float) 0.22);
        }
        
        if(beans.get(position).getAuthor().equals("")){
            holder.authorOr.setText(beans.get(position).getShareUser());
            holder.niceShareTime.setText(beans.get(position).getNiceShareDate());
        }else {
            holder.authorOr.setText(beans.get(position).getAuthor());
            holder.niceShareTime.setText(beans.get(position).getNiceDate());
        }

        holder.chapterName.setText(beans.get(position).getChapterName());
        holder.superChapterName.setText(beans.get(position).getSuperChapterName());

        String title = dealWithDate(beans.get(position).getTitle());
        holder.title.setText(title);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"YangRegular.ttf");
        holder.authorOr.setTypeface(typeface);
        holder.superChapterName.setTypeface(typeface);
        holder.title.setTypeface(typeface);
        holder.chapterName.setTypeface(typeface);
        holder.niceShareTime.setTypeface(typeface);
    }

    private String dealWithDate(String title) {
        String titleDeal = title;
        while(titleDeal.contains("<em class='highlight'>")||titleDeal.contains("</em>")){
            titleDeal = titleDeal.replace("<em class='highlight'>","");
            titleDeal = titleDeal.replace("</em>","");
        }
        return titleDeal;
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final View view;
        final TextView authorOr;
        final TextView chapterName;
        final TextView title;
        final TextView superChapterName;
        final TextView niceShareTime;
        final LottieAnimationView lottie;
        final ImageView category;
        final ImageView triangle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            authorOr = itemView.findViewById(R.id.author);
            chapterName = itemView.findViewById(R.id.chapterName);
            title = itemView.findViewById(R.id.title);
            superChapterName = itemView.findViewById(R.id.superChapterName);
            niceShareTime = itemView.findViewById(R.id.niceShareDate);
            lottie = itemView.findViewById(R.id.lottie_rv);
            category = itemView.findViewById(R.id.categery);
            triangle = itemView.findViewById(R.id.triangle);
        }
    }

    public void unCollect(ViewHolder viewHolder) {
        SharedPreferences preferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpUtil.unCollectPostRequestWithOkhttp(beans.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {

                mActivity.runOnUiThread(() -> ToastUtil.showToast("取消收藏",context, Toast.LENGTH_SHORT));

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
        OkHttpUtil.collectInPostRequestWithOkhttp(beans.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {

                mActivity.runOnUiThread(() -> ToastUtil.showToast("收藏成功",context, Toast.LENGTH_SHORT));

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);
    }
}
