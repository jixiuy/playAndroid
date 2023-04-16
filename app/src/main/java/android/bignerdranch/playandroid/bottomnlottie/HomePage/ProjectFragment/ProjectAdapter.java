package android.bignerdranch.playandroid.bottomnlottie.HomePage.ProjectFragment;

import com.example.readapp.R;
import com.example.readapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 项目那里侧边栏的适配器
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    final List<ProjectBean.DataBean> list;
    final Context context;
    final KeyDateCallback mKeyDateCallback;
    private static final String TAG = "ProjectAdapter";
    public ProjectAdapter(List<ProjectBean.DataBean> list, Context context,KeyDateCallback callback) {
        this.list = list;
        this.context = context;
        this.mKeyDateCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_son_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);


        holder.view.setOnClickListener(view1 -> mKeyDateCallback.getPassageId(list.get(holder.getAdapterPosition()).getId()));

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        final View view;
        final TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mTextView = itemView.findViewById(R.id.pro_draw_tv);
        }
    }

}
