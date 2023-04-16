package android.bignerdranch.playandroid.bottomnlottie.HomePage.CollectFragment;

import android.app.Activity;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏界面RecycleView的适配器
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {

    final Context mContext;
    static final List<MyCollectBean.DataBean.DatasBean> list = new ArrayList<>();
    final Activity mActivity;
    int position;


    public CollectAdapter(Context context, FragmentActivity activity,MyCollectBean bean) {
        list.clear();
        list.addAll(bean.getData().getDatas());
        this.mContext = context;
        mActivity = activity;
    }

    public void setList(MyCollectBean bean){
        list.addAll(bean.getData().getDatas());
    }

    public void notifyList(MyCollectBean bean){
        list.clear();
        list.addAll(bean.getData().getDatas());
    }

    public List<MyCollectBean.DataBean.DatasBean> getList(){
        return list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.my_collect_son_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(view12 -> {
            Intent intent = new Intent(mContext, DisplayActivity.class);
            intent.putExtra("url",list.get(holder.getAdapterPosition()).getLink());
            mContext.startActivity(intent);
        });


        holder.lottie.setAnimation(R.raw.collect_yellow);
        holder.lottie.setProgress((float) 0.22);

        holder.lottie.setOnClickListener(view1 -> {
            Intent intent = new Intent(mActivity,MyCollectDialogActivity.class);
            position = holder.getAdapterPosition();
            intent.putExtra("title",list.get(position).getTitle());
            mActivity.startActivity(intent);
        });


        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());
        holder.category.setText(list.get(position).getChapterName());
        holder.date.setText(list.get(position).getNiceDate());

        if(list.get(position).getDesc().equals("")){
            holder.detail.setVisibility(View.GONE);
        }else holder.detail.setText(list.get(position).getDesc());

    }

    private static final String TAG = "CollectAdapter";

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView date;
        final TextView category;
        final View viewClick;
        final TextView detail;
        final LottieAnimationView lottie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_collect);
            date = itemView.findViewById(R.id.date_collect);
            category = itemView.findViewById(R.id.category_collect);
            detail = itemView.findViewById(R.id.detail);
            viewClick = itemView;
            lottie = itemView.findViewById(R.id.collect_lottie);
        }
    }


}

