package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner;


import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Banner的适配器
 */
public class MyBannerAdapter extends BannerAdapter<DataBeen,MyBannerAdapter.BannerViewHolder> {

    final Context mContext;
    final List<DataBeen> datas;
    Intent intent;

    public MyBannerAdapter(List<DataBeen> datas, Context applicationContext) {
        super(datas);
        this.mContext = applicationContext;
        this.datas = datas;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.banner_item_view,parent,false);
        BannerViewHolder holder = new BannerViewHolder(view);
        intent = new Intent(mContext, DisplayActivity.class);
        holder.mImageView.setOnClickListener(view12 -> {
            intent.putExtra("url",datas.get(holder.getLayoutPosition()-1).getUrl());
            mContext.startActivity(intent);

        });
        holder.mTextView.setOnClickListener(view1 -> {
            intent.putExtra("url",datas.get(holder.getLayoutPosition()-1).getUrl());
            mContext.startActivity(intent);
        });
        return holder;
    }

    @Override
    public void onBindView(BannerViewHolder holder, DataBeen data, int position, int size) {

        Glide.with(holder.itemView).load(data.getImageRes()).placeholder(R.drawable.ch8).into(holder.mImageView);
        holder.mTextView.setText(data.textRes);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "华康少女字体.ttf");
        holder.mTextView.setTypeface(typeface);
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        final ImageView mImageView;
        final TextView mTextView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.head_portrait);
            mTextView = itemView.findViewById(R.id.liquid_exit);
        }
    }
}
