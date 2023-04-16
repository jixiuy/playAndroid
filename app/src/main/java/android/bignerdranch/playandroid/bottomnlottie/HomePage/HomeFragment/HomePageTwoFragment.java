package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment;

import android.annotation.SuppressLint;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.PoetBeen;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner.BannerData;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner.DataBeen;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner.MyBannerAdapter;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.passage.MyRecycleViewAdapter;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.passage.MyTopPassageBean;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.passage.PassageBean;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.bignerdranch.playandroid.util.JudgeNetUtil;
import android.bignerdranch.playandroid.util.MyCookieJar;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePageTwoFragment extends Fragment implements OkHttpCallbackListener{
    //Banner
    final List<DataBeen> mDataBeans =new ArrayList<>();
    List<BannerData.DataBean> bannerDates;
    private Banner mBanner;
    private static final String TAG = "HomePageFragment";
    View view;
    boolean isBannerIndicatorCreated = false;
    ImageView network;

    TextView tv;
    TextView tvRefresh;

    final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){


                DataBeen dataBeen1 = new DataBeen(bannerDates.get(0).getImagePath(),bannerDates.get(0).getTitle(),bannerDates.get(0).getUrl());
                DataBeen dataBeen2 = new DataBeen(bannerDates.get(1).getImagePath(),bannerDates.get(1).getTitle(),bannerDates.get(1).getUrl());
                DataBeen dataBeen3 = new DataBeen(bannerDates.get(2).getImagePath(),bannerDates.get(2).getTitle(),bannerDates.get(2).getUrl());

                mDataBeans.add(dataBeen1);
                mDataBeans.add(dataBeen2);
                mDataBeans.add(dataBeen3);

                if(!isBannerIndicatorCreated){
                    mBanner.addBannerLifecycleObserver(getActivity())
                            .setAdapter(new MyBannerAdapter(mDataBeans,getContext()))
//                            .setIndicator(new CircleIndicator(getContext()))
//                            .setIndicatorGravity(IndicatorConfig.Direction.CENTER)
                            .setBannerRound2(100)
                            .setBannerGalleryMZ(50)
                            .setPageTransformer(new ZoomOutPageTransformer())
                            .start();
                    isBannerIndicatorCreated = true;
                    Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.view_enter);
                    mBanner.startAnimation(animation);
                }



            }
            if(message.what == 2){
                mTextView.setText(message.obj.toString());
            }

            if(message.what == 3){
                initRecycleView();
            }

            if(message.what == 4){
                initTopRecycleView();
            }

            if(message.what==5){
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(myAdapter);
            }
            if (message.what == 6) {

                adapter.setDate(mTopBeanResume);
                adapter.notifyDataSetChanged();
            }
            if (message.what == 7) {
                myAdapter.notifyDataSetChanged();
            }

            return false;
        }
    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isBannerIndicatorCreated = false;
    }

    MyRecycleViewAdapter adapter;
    private MyTopPassageBean mTopBeanResume;

    private void initTopRecycleView() {
        adapter = new MyRecycleViewAdapter(getContext(), mTopBean,getActivity());
        mTopPassageRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTopPassageRecycleView.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(BaseActivity.mContext,R.anim.view_enter);
        mTopPassageRecycleView.setAnimation(animation);
        Message message = new Message();
        message.what = 5;
        handler.sendMessage(message);
    }

    private MyTopPassageBean mTopBean;

    private void refreshAll() {

        mBanner = view.findViewById(R.id.banner);
        mTopPassageRecycleView = view.findViewById(R.id.recycle_view_top_passage);
        recyclerView = view.findViewById(R.id.recycleView);

        gainBannerData();
        createTopPassage();
        sendRequestWithOkhttp();
        initRecycleView();
        initSmartRefreshLayout();
        createSmartRefreshLayout();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("hello", "onCreateView: 1");
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_page_two,container,false);
        network = view.findViewById(R.id.home_page_network);

        tvRefresh = view.findViewById(R.id.home_page_network_refresh);
        tvRefresh.setOnClickListener(view -> {
            if(JudgeNetUtil.isNetworkConnected(getContext())){
                network.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);

                tvRefresh.setVisibility(View.GONE);
                refreshAll();

            }else {
                tvRefresh.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                network.setVisibility(View.VISIBLE);

                refreshAll();
            }
        });
        tv = view.findViewById(R.id.home_page_network_tv);
        if(JudgeNetUtil.isNetworkConnected(getContext())){
            tvRefresh.setVisibility(View.GONE);
            network.setVisibility(View.GONE);

            tv.setVisibility(View.GONE);
            refreshAll();

        }else {
            tv.setVisibility(View.VISIBLE);
            network.setVisibility(View.VISIBLE);

            refreshAll();
        }


        mBanner.start();

        return view;
    }
    //banner
    private void gainBannerData() {

        String address = "https://www.wanandroid.com/banner/json";
        OkHttpUtil.sendGetRequestWithOkHttp(address, new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                Gson gson = new Gson();
                BannerData dates = gson.fromJson(response,BannerData.class);
                bannerDates = dates.getData();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();

            }
        });

    }


    public int page = 0;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    boolean flag = false;
    public void initRecycleView() {

        if (!flag) {
            flag=true;
            myAdapter = new MyAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.view_enter);
            recyclerView.setAnimation(animation);
            recyclerView.setVisibility(View.GONE);
        }else {
            myAdapter.notifyItemInserted(myAdapter.getItemCount());
        }
    }

    private final List<PassageBean.DataBean.DatasBean> mLimitedBeans = new ArrayList<>();
    public int i =0;

    boolean isCreated = false;
    public void parseDateWithGson(String date) {

        Gson gson = new Gson();
        PassageBean passageBean = gson.fromJson(date, PassageBean.class);
        if (!isCreated) {
            isCollected = new boolean[passageBean.getData().getTotal()];
            isCreated = true;
        }

        mLimitedBeans.addAll(passageBean.getData().getDatas());

        Message message = new Message();
        message.what = 7;
        handler.sendMessage(message);
    }

    private RecyclerView mTopPassageRecycleView;

    private void createTopPassage() {

        SharedPreferences pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/article/top/json", this
                ,pref.getString("account",null),pref.getString("password",null));
    }

    public void sendRequestWithOkhttp() {


        String url = "https://www.wanandroid.com/article/list/" + page + "/json";
        SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
        Request request = new Request.Builder()
                .addHeader("Cookie","loginUserName="+account)
                .addHeader("Cookie","loginUserPassword="+password)
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String date = response.body().string();

                parseDateWithGson(date);
            }
        });
    }



    //下拉刷新
    SmartRefreshLayout refreshLayout;
    private TextView mTextView;

    //上拉加载
    private final int itemCount = 0;

    //下拉刷新
    //下拉刷新
    private void createSmartRefreshLayout() {


        refreshLayout = view.findViewById(R.id.refreshLayout);
        ConstraintLayout constraintLayout = refreshLayout.findViewById(R.id.header);
        ConstraintLayout classicsHeader = constraintLayout.findViewById(R.id.header);
        ClassicsFooter classicsFooter = constraintLayout.findViewById(R.id.footer);
        LottieAnimationView lottieAnimationView = constraintLayout.findViewById(R.id.lottieAnimationView);
        mTextView = constraintLayout.findViewById(R.id.text_view);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"盐叶之庭.ttf");
        mTextView.setTypeface(typeface);

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            lottieAnimationView.setAnimation(R.raw.book);
            sendRequestWithOkhttp("https://v1.hitokoto.cn/");
            lottieAnimationView.playAnimation();

            Handler handler = new Handler();
            Runnable runnable = () -> {
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setProgress(0);
                mTextView.setText("");
            };
            handler.postDelayed(runnable,3000);
            refreshLayout.finishRefresh(3000);

        });
        refreshLayout.finishRefresh();
    }


    private LottieAnimationView mLottieAnimationView;
    private void initSmartRefreshLayout() {
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        mLottieAnimationView = view.findViewById(R.id.loading);
        mLottieAnimationView.setAnimation(R.raw.loading);
        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            mLottieAnimationView.playAnimation();

            page++;
            sendRequestWithOkhttp();

            refreshLayout1.finishLoadMore(1750);


        });
    }


    private void sendRequestWithOkhttp(String date){
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(date)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseDate = response.body().string();

                        showResponse(responseDate);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();

            }
        }).start();
    }
    PoetBeen poetBeen = new PoetBeen();


    private void showResponse(String responseDate) {

        Gson gson = new Gson();
        poetBeen = gson.fromJson(responseDate,PoetBeen.class);

        Message message = new Message();
        message.obj = poetBeen.getHitokoto();
        message.what = 2;
        handler.sendMessage(message);

    }

    @Override
    public void finish(String response) {

        mTopBean = new Gson().fromJson(response, MyTopPassageBean.class);
        Message message = new Message();
        message.what = 4;
        handler.sendMessage(message);

    }

    @Override
    public void onError(Exception e) {

    }


    boolean isFirst = false;
    @Override
    public void onResume() {
        super.onResume();

        if(isFirst){
            SharedPreferences pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/article/top/json", new OkHttpCallbackListener() {
                        @Override
                        public void finish(String response) {
                            mTopBeanResume = new Gson().fromJson(response,MyTopPassageBean.class);
                            Message message = new Message();
                            message.what = 6;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    }
                    , pref.getString("account", null), pref.getString("password", null));
            page = 0;
            mLimitedBeans.clear();
            String url = "https://www.wanandroid.com/article/list/" + page + "/json";
            SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            String account = preferences.getString("account",null);
            String password = preferences.getString("password",null);
            OkHttpClient client = new OkHttpClient.Builder().cookieJar(new MyCookieJar()).build();
            Request request = new Request.Builder()
                    .addHeader("Cookie","loginUserName="+account)
                    .addHeader("Cookie","loginUserPassword="+password)
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String date = response.body().string();

                    parseDateWithGson(date);
                }
            });
            gainBannerData();

        }
        isFirst = true;
    }

    String title = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    boolean[] isCollected ;
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        ViewHolder mHolder;


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.passage, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.title.setMaxEms(15);
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"YangRegular.ttf");
            viewHolder.author.setTypeface(typeface);
            viewHolder.superCategory.setTypeface(typeface);
            viewHolder.title.setTypeface(typeface);
            viewHolder.category.setTypeface(typeface);
            viewHolder.date.setTypeface(typeface);

            viewHolder.view.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), DisplayActivity.class);
                intent.putExtra("url",mLimitedBeans.get(viewHolder.getAdapterPosition()).getLink());
                getActivity().startActivity(intent);
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            mHolder = holder;
            isCollected[position] = mLimitedBeans.get(position).getCollect();
            if(mLimitedBeans.get(position).getCollect()){
                holder.lottieAnimationView.setAnimation(R.raw.collect_yellow);
                holder.lottieAnimationView.playAnimation();
            }else {
                holder.lottieAnimationView.setAnimation(R.raw.collect_black);
                holder.lottieAnimationView.setProgress((float) 0.22);
            }

            holder.title.setText(mLimitedBeans.get(position).getTitle());
            holder.superCategory.setText(mLimitedBeans.get(position).getSuperChapterName());
            holder.category.setText(mLimitedBeans.get(position).getChapterName());
            if (mLimitedBeans.get(position).getAuthor().equals("")) {
                holder.date.setText(mLimitedBeans.get(position).getNiceShareDate());
                holder.author.setText("分享人：" + mLimitedBeans.get(position).getShareUser());
            } else {
                holder.date.setText(mLimitedBeans.get(position).getNiceDate());
                holder.author.setText("作者：" + mLimitedBeans.get(position).getAuthor());
            }

            holder.lottieAnimationView.setOnClickListener(view -> {

                if(isCollected[position]){
                    holder.lottieAnimationView.setAnimation(R.raw.collect_black);
                    holder.lottieAnimationView.setProgress((float) 0.25);
                    unCollect(holder);
                    isCollected[position] = false;
                }else {


                    holder.lottieAnimationView.setAnimation(R.raw.collect_yellow);
                    holder.lottieAnimationView.playAnimation();

                    collect(holder);
                    isCollected[position] = true;
                }

            });

        }

        @Override
        public int getItemCount() {

            return mLimitedBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView author;
            final TextView superCategory;
            final TextView category;
            final TextView date;
            final TextView title;
            final ImageView mImageView1;
            final ImageView mImageView2;
            final LottieAnimationView lottieAnimationView;
            final View view;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                author = itemView.findViewById(R.id.author);
                superCategory = itemView.findViewById(R.id.superChapterName);
                category = itemView.findViewById(R.id.chapterName);
                date = itemView.findViewById(R.id.niceShareDate);
                title = itemView.findViewById(R.id.title);
                lottieAnimationView = itemView.findViewById(R.id.lottie_rv_passage);
                mImageView1 = itemView.findViewById(R.id.categery);
                mImageView2 = itemView.findViewById(R.id.sanjiaoxing);
                view = itemView;
            }
        }
        public void unCollect(ViewHolder viewHolder) {
            SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            String account = preferences.getString("account",null);
            String password = preferences.getString("password",null);
            OkHttpUtil.unCollectPostRequestWithOkhttp(mLimitedBeans.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
                @Override
                public void finish(String response) {
                    getActivity().runOnUiThread(() -> ToastUtil.showToast("取消收藏成功",getContext(), Toast.LENGTH_SHORT));

                }

                @Override
                public void onError(Exception e) {

                }
            },"loginUserName="+account,"loginUserPassword="+password);
        }

        public void collect(ViewHolder viewHolder){
            SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            String account = preferences.getString("account",null);
            String password = preferences.getString("password",null);
            OkHttpUtil.collectInPostRequestWithOkhttp(mLimitedBeans.get(viewHolder.getAdapterPosition()).getId().toString(), new OkHttpCallbackListener() {
                @Override
                public void finish(String response) {
                    getActivity().runOnUiThread(() -> ToastUtil.showToast("收藏成功",getActivity(), Toast.LENGTH_SHORT));

                }

                @Override
                public void onError(Exception e) {

                }
            },"loginUserName="+account,"loginUserPassword="+password);
        }
    }

}
