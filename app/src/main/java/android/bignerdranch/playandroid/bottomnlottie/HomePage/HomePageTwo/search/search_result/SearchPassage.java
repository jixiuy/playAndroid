package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result;

import static org.litepal.LitePalApplication.getContext;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.readapp.R;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的文章展示页面，可以阅读，这边没写收藏，外面写了很多了，不大想继续填了
 */
public class SearchPassage extends BaseActivity implements View.OnClickListener{
    EditText searchET;
    TextView loadingTv;
    private LottieAnimationView mLottieNormal;
    private LottieAnimationView mLottieRefresh;
    ImageView back;

    View mLayout;
    TextView search;
    boolean isLoadingLaterRefresh = false;

    String key;
    private static final String TAG = "SearchPassage";

    int pageTotal = 0;
    int currentPage ;
    int count;

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            if (message.what == 1) {
                beans.addAll(newBeans);
                mAdapter.setData(beans,key);
                mAdapter.notifyItemRangeChanged(count,beans.size());
            }
            return false;
        }
    });
    private MySearchedPassageAdapter mAdapter;
    private RecyclerView mMRecyclerView;
    private RefreshLayout mRefreshLayout;
    private AnimationDrawable mAnim;
    private ImageView mMonkeyAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searched_passage);
        currentPage = 0;

        initSmartRefreshLayout();
        initView();
        getDateFromLastActivity();
        postKeyGetPassage();

    }
    private void initSmartRefreshLayout() {
        mRefreshLayout = findViewById(R.id.refreshLayout);

        //帧动画显示猴子
        mMonkeyAnim = findViewById(R.id.rabbit_collect);
        mAnim = new AnimationDrawable();
        mAnim.addFrame(getResources().getDrawable(R.drawable.drawable_loading1),80);
        mAnim.addFrame(getResources().getDrawable(R.drawable.drawable_loading2),80);
        mAnim.addFrame(getResources().getDrawable(R.drawable.drawable_loading3),80);
        mAnim.addFrame(getResources().getDrawable(R.drawable.drawable_loading4),80);
        mAnim.setOneShot(false);
        mMonkeyAnim.setImageDrawable(mAnim);
        mAnim.start();


        mLottieNormal = findViewById(R.id.normal_loading_lottie);
        mLottieRefresh = findViewById(R.id.refresh_lottie);

        mLottieRefresh.setAnimation(R.raw.refresh);
        mLottieNormal.setAnimation(R.raw.loadingaaaaaa);

        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            Log.d(TAG, "onRefresh: ");
            mRefreshLayout.finishRefresh(2000);
            mLottieRefresh.playAnimation();
            mHandler.postDelayed(() -> mLottieRefresh.cancelAnimation(),2000);
        });


        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            mLottieNormal.playAnimation();
            mLottieNormal.setRepeatCount(2);
            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            currentPage++;
            Log.d(TAG, "onLoadMore: "+currentPage+" "+pageTotal);
            if(pageTotal>=currentPage){
                postKeyGetPassage();
            }else {
                mLottieNormal.setVisibility(View.GONE);
                loadingTv.setText("数据已经全部加载完成！");
                isLoadingLaterRefresh = true;
            }


        });
    }

    private void createRecycleView() {
        mMRecyclerView = findViewById(R.id.recycleView);
        mAdapter = new MySearchedPassageAdapter(getApplicationContext(),beans,key,SearchPassage.this);
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.hot_anim));
        mMRecyclerView.setLayoutAnimation(controller);

        mMRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //在设置资源前的一瞬间关闭过度动画.
        mAnim.stop();
        mMonkeyAnim.setVisibility(View.GONE);
        mMRecyclerView.setVisibility(View.VISIBLE);
        mMRecyclerView.setAdapter(mAdapter);

    }


    List<PassageBean.DataBean.DatasBean> beans = new ArrayList<>();
    List<PassageBean.DataBean.DatasBean> newBeans = new ArrayList<>();
    private void postKeyGetPassage() {
        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        //如果是刚进入该页面
        if(currentPage == 0){
            SendRequestWthOkhttp.postWithOkhttp("https://www.wanandroid.com/article/query/0/json", key, new OkhttpListener() {
                @Override
                public void onResponse(String responseDate) {
                    beans = new Gson().fromJson(responseDate,PassageBean.class).getData().getDatas();
                    count = beans.size();
                    pageTotal =  new Gson().fromJson(responseDate,PassageBean.class).getData().getPageCount();
                    //如果没有该关键词的数据-->界面设计
                    Log.d(TAG, "onResponse: "+responseDate);
                    runOnUiThread(() -> createRecycleView());

                }

                @Override
                public void onFail() {
                    Log.d(TAG, "onFail: ");
                }
            },"loginUserName="+account,"loginUserPassword="+password);

        }else if(currentPage != 0 && currentPage<=pageTotal){
            String newUrl = "https://www.wanandroid.com/article/query/"+currentPage+"/json";
            SendRequestWthOkhttp.postWithOkhttp(newUrl, key, new OkhttpListener() {
                @Override
                public void onResponse(String responseDate) {
                    //在主线程中处理返回结果
                    Log.d(TAG, "onResponse: "+responseDate);
                    dealWithResult(responseDate);
                }

                @Override
                public void onFail() {
                    Log.d(TAG, "onFail: ");
                }
            },"loginUserName="+account,"loginUserPassword="+password);

        }

    }


    private void dealWithResult(String responseDate) {
        newBeans = new Gson().fromJson(responseDate,PassageBean.class).getData().getDatas();
        count+=newBeans.size();
        Message message = new Message();
        message.what = 1;
        mHandler.sendMessage(message);

    }

    private void getDateFromLastActivity() {
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        Log.d(TAG, key);
        searchET.setText(key);
    }

    private void initView() {

        loadingTv = findViewById(R.id.loading_tv);
        mLayout = findViewById(R.id.include);
        search = mLayout.findViewById(R.id.search_button);
        back = mLayout.findViewById(R.id.back);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        searchET = findViewById(R.id.search_editText);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_button:{
                search();
            }break;
            case R.id.back:{
                SearchPassage.this.finish();
            }break;
        }
    }

    public void search(){
        String newKey = searchET.getText().toString();

        //如果和之前一样就不用网络请求
        if(newKey.equals(key)){
            mMRecyclerView.scrollToPosition(0);
            mMonkeyAnim.setVisibility(View.VISIBLE);
            mMRecyclerView.setVisibility(View.GONE);
            mAnim.start();
            mHandler.postDelayed(() -> {
                mAnim.stop();
                mMonkeyAnim.setVisibility(View.GONE);
                mMRecyclerView.setVisibility(View.VISIBLE);

            },200);
        }else {
            beans.clear();
            key = newKey;
            currentPage = 0;
            pageTotal = 0;
            mMonkeyAnim.setVisibility(View.VISIBLE);
            mMRecyclerView.setVisibility(View.GONE);
            mAnim.start();

            if(isLoadingLaterRefresh){
                isLoadingLaterRefresh = false;
                mLottieNormal.setVisibility(View.VISIBLE);
                loadingTv.setText("正在加载中...");
            }
            postKeyGetPassage();
        }

    }

}