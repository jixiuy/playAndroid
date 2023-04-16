package android.bignerdranch.playandroid.bottomnlottie.HomePage.SystemFragment;

import android.annotation.SuppressLint;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.ResponseListener;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.SendRequestWithOkhttpUtl;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.PassageBean;
import android.bignerdranch.playandroid.util.JudgeNetUtil;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.readapp.R;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 点击二级分类第一级打开的文章页面
 */
public class SystemPassageFragment extends Fragment {

    View view;
    final Integer twoClassId;
    int pageCount;
    int currentPage;
    MyRVAdapter adapter;
    List<PassageBean.DataBean.DatasBean> mList = new ArrayList<>();
    ImageView network;

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){
                //二级分类的文章
                RecyclerView recyclerView = view.findViewById(R.id.system_passage_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.system_passage_anim));
                recyclerView.setLayoutAnimation(controller);
                adapter = new MyRVAdapter(mList);
                recyclerView.setAdapter(adapter);
            }
            if(message.what == 2){
                adapter.notifyDataSetChanged();
            }

            return false;
        }
    });

    public SystemPassageFragment(Integer twoClassId) {
        this.twoClassId = twoClassId;

    }

    private void initDate(Integer twoClassId) {
        SendRequestWithOkhttpUtl.sendRequestWithOkhttp(new ResponseListener() {
            @Override
            public void response(Response response) {
                String temp = null;
                try {
                    temp = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PassageBean bean = new Gson().fromJson(temp,PassageBean.class);
                pageCount = bean.getData().getPageCount();
                currentPage = bean.getData().getCurPage();
                mList = bean.getData().getDatas();
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }

            @Override
            public void fail() {

            }
        },"https://www.wanandroid.com/article/list/0/json?cid="+ twoClassId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (time == 1 && !isCreated) {
            network.setVisibility(View.GONE);
            initDate(twoClassId);
            initSmartRefreshLayout();
        }
        time = 1;
    }

    int time = 0;
    boolean isCreated = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_system_passage, container, false);
        network = view.findViewById(R.id.two_class_network);
        if(JudgeNetUtil.isNetworkConnected(getContext())){
            network.setVisibility(View.GONE);
            isCreated = true;
        }else {
            network.setVisibility(View.VISIBLE);
            isCreated = false;
        }

        initDate(twoClassId);
        initSmartRefreshLayout();


        return view;
    }

    private void initSmartRefreshLayout() {
        SmartRefreshLayout refreshLayout1 = view.findViewById(R.id.system_smartRefreshLayout);
        refreshLayout1.setRefreshHeader(new MaterialHeader(getContext()));
        refreshLayout1.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout1.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh(2000));
        refreshLayout1.setOnLoadMoreListener(refreshLayout -> {
              currentPage++;
              refreshLayout.finishLoadMore(2000);
              if(currentPage<=pageCount){

                  SendRequestWithOkhttpUtl.sendRequestWithOkhttp(new ResponseListener() {
                      @Override
                      public void response(Response response) {
                          PassageBean bean = null;
                          try {
                              bean = new Gson().fromJson(response.body().string(), PassageBean.class);
                          } catch (IOException e) {
                              e.printStackTrace();
                          }
                          mList.addAll(bean.getData().getDatas());
                          Message message = new Message();
                          message.what = 2;
                          mHandler.sendMessage(message);
                      }

                      @Override
                      public void fail() {

                      }
                  },"https://www.wanandroid.com/article/list/+"+currentPage+"/json?cid="+ twoClassId);
              }else {
                  Message message = new Message();
                  message.what = 3;
                  mHandler.sendMessage(message);
              }
        });
    }


    class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder>{
        final List<PassageBean.DataBean.DatasBean> list;

        public MyRVAdapter(List<PassageBean.DataBean.DatasBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.passage_rl,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "YangRegular.ttf");
            holder.title.setTypeface(typeface);
            holder.title.setText(list.get(position).getTitle());

            holder.superCategory.setText(list.get(position).getSuperChapterName());
            holder.category.setText(list.get(position).getChapterName());
            if (list.get(position).getAuthor().equals("")) {
                holder.date.setText(list.get(position).getNiceShareDate());
                holder.author.setText("分享人：" + list.get(position).getShareUser());
            } else {
                holder.date.setText(list.get(position).getNiceDate());
                holder.author.setText("作者：" + list.get(position).getAuthor());
            }
            holder.mView.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), DisplayActivity.class);
                intent.putExtra("url",list.get(holder.getAdapterPosition()).getLink());
                getActivity().startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return list.size();}

        class ViewHolder extends RecyclerView.ViewHolder{
            final TextView author;
            final TextView superCategory;
            final TextView category;
            final TextView date;
            final TextView title;
            final ImageView mImageView1;
            final ImageView mImageView2;
            final LottieAnimationView lottieAnimationView;
            final View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                author = itemView.findViewById(R.id.author);
                superCategory = itemView.findViewById(R.id.superChapterName);
                category = itemView.findViewById(R.id.chapterName);
                date = itemView.findViewById(R.id.niceShareDate);
                title = itemView.findViewById(R.id.title);
                lottieAnimationView = itemView.findViewById(R.id.lottie);
                mImageView1 = itemView.findViewById(R.id.categery);
                mImageView2 = itemView.findViewById(R.id.sanjiaoxing);
                mView = itemView;
            }
        }
    }

}