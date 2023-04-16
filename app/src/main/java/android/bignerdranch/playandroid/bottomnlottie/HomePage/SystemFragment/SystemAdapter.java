package android.bignerdranch.playandroid.bottomnlottie.HomePage.SystemFragment;

import android.annotation.SuppressLint;
import com.example.readapp.R;
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

import java.util.List;

/**
 * 文章RecycleView的适配器
 */
public class SystemAdapter extends RecyclerView.Adapter<SystemAdapter.ViewHolder> {
    final Context mContext;
    final List<String> mList;


    public SystemAdapter(Context context, List<String> list2, SystemBean bean) {
        this.mList = list2;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.system_fragment_son_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"YangRegular.ttf");
        holder.systemTextView.setTypeface(typeface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.systemTextView.setText(mList.get(position));
        if(position == 0){
            holder.systemImageView.setImageResource(R.drawable.ic_category_album);
        }
        else if(position == 1) {
            holder.systemImageView.setImageResource(R.drawable.ic_category_audio);
        } else if(position == 2){
            holder.systemImageView.setImageResource(R.drawable.ic_category_column);
        }else if(position == 3){
            holder.systemImageView.setImageResource(R.drawable.ic_category_game_center);
        }
        else if(position == 4){
            holder.systemImageView.setImageResource(R.drawable.ic_category_live);
        }else if(position == 5){
            holder.systemImageView.setImageResource(R.drawable.ic_category_mall);
        }else if(position == 6){
            holder.systemImageView.setImageResource(R.drawable.ic_category_promo);
        }else if(position == 7){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t1);
        }else if(position == 8){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t3);
        }else if(position == 9){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t4);
        }else if(position == 10){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t5);
        }else if(position == 11){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t11);
        }else if(position == 12){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t13);
        }else if(position == 13){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t36);
        }else if(position == 14){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t119);
        }else if(position == 15){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t160);
        }else if(position == 16){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t165);
        }else if(position == 17){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t167);
        }else if(position == 18){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t1);
        }else if(position == 19){
            holder.systemImageView.setImageResource(R.drawable.ic_category_live);
        }
        else if(position == 20){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t13);
        }else if(position == 21){
            holder.systemImageView.setImageResource(R.drawable.ic_category_t36);
        }

        holder.view.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,SystemTwoClass.class);
            intent.putExtra("name",mList.get(position));
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView systemImageView;
        final TextView systemTextView;
        final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            systemImageView = itemView.findViewById(R.id.system_imageView);
            systemTextView = itemView.findViewById(R.id.system_textView);
            view = itemView;
        }
    }
}
