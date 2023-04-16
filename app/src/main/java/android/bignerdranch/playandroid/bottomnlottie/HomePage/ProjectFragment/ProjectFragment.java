package android.bignerdranch.playandroid.bottomnlottie.HomePage.ProjectFragment;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.PoetBeen;
import android.bignerdranch.playandroid.util.JudgeNetUtil;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.readapp.R;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ViewPager2第三页---项目
 */
public class ProjectFragment extends Fragment implements View.OnClickListener, OkHttpCallbackListener, KeyDateCallback {
    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout mDrawerLayout;
    private View mView;
    private static final String TAG = "ProjectFragment";
    RecyclerView recyclerView ;

    final List<ProjectBean.DataBean> mProjectNameBeanList = new ArrayList<>();

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 2){
                initPassage();
            }
            if(message.what == 3){

                mTextView.setText(message.obj.toString());
            }
            if (message.what == 4){
                mPassageAdapter.updateDate(passageBean);
                mPassageAdapter.notifyItemInserted(mPassageAdapter.getItemCount());
            }
            return false;
        }
    });
    private ImageView mBiliLoading;
    private AnimationDrawable mDrawable;


    ImageView netWork;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("hello", "onCreateView: 3");
        mView = inflater.inflate(R.layout.fragment_project, container, false);
        netWork = mView.findViewById(R.id.project_network);
        loadingCloud = mView.findViewById(R.id.loading_cloud);
        cloudAnimation = (AnimationDrawable) loadingCloud.getDrawable();
        if(JudgeNetUtil.isNetworkConnected(getContext()))
        {
            netWork.setVisibility(View.GONE);
            cloudAnimation.start();
            isDisplay = true;
        }
        else {
            loadingCloud.setVisibility(View.GONE);
            netWork.setVisibility(View.VISIBLE);
            isDisplay = false;
        }



        mDrawerLayout = mView.findViewById(R.id.drawLayout);
        mBiliLoading = mView.findViewById(R.id.bilibili_loading_two);
        mBiliLoading.setVisibility(View.VISIBLE);
        mDrawable = (AnimationDrawable) mBiliLoading.getDrawable();
        mDrawable.start();

        initView();
        initDate();
        initFloatActionButton();
        gainPassageDate();
        initSmartRefreshLayout();
        createSmartRefreshLayout();
        Log.d(TAG, "onCreateView: ");

        return mView;
    }

    ImageView loadingCloud;
    AnimationDrawable cloudAnimation;
    boolean isDisplay = false;
    int time = 0;
    @Override
    public void onResume() {
        super.onResume();
        isSuccessRequest = false;
        if(!isDisplay &&time ==1){
            isSuccessRequest = false;
            netWork.setVisibility(View.GONE);
            isDisplay = true;
            mDrawerLayout = mView.findViewById(R.id.drawLayout);
            mBiliLoading = mView.findViewById(R.id.bilibili_loading_two);
            mBiliLoading.setVisibility(View.VISIBLE);
            mDrawable = (AnimationDrawable) mBiliLoading.getDrawable();
            mDrawable.start();

            mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {
                    if(JudgeNetUtil.isNetworkConnected(getContext())) netWork.setVisibility(View.GONE);
                    else netWork.setVisibility(View.VISIBLE);
                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });

            isSuccessRequest = false;
            initView();
            initDate();
            initFloatActionButton();
            gainPassageDate();
            initSmartRefreshLayout();
            createSmartRefreshLayout();
            Log.d(TAG, "onCreateView: ");
        }time = 1;
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    private void initDate() {
        keyId = 294;
        page = 1;
    }

    private ProjectPassageBean passageBean;
    private ProjectPassageAdapter mPassageAdapter;
    private RecyclerView mPassageRecycleView;



    private void initPassage() {

        mPassageAdapter = new ProjectPassageAdapter(passageBean,getContext(),getActivity());
        mPassageAdapter.setAdapter(mPassageAdapter);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.project_passage_recycleview);
        mPassageRecycleView.setLayoutAnimation(controller);
        mPassageRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDrawable.stop();
        mBiliLoading.setVisibility(View.GONE);
        mPassageRecycleView.setAdapter(mPassageAdapter);

    }


    Integer keyId;
    int page;
    public void gainPassageDate() {
        SharedPreferences pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/project/list/"+page+"/json?cid="+keyId, new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                passageBean = new Gson().fromJson(response,ProjectPassageBean.class);
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
             
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError: ");
            }
        } ,pref.getString("account",null),pref.getString("password",null));
    }



    private void initFloatActionButton() {

        mFloatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.jia));
        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#E7D7BE"));
        mFloatingActionButton.setImageTintList(colorStateList);

    }

    private void initView() {
        mFloatingActionButton = mView.findViewById(R.id.floatButton);
        mFloatingActionButton.setOnClickListener(this);

        recyclerView = mView.findViewById(R.id.projectRecycleView);
        mPassageRecycleView = mView.findViewById(R.id.project_passage_recyclerview);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floatButton) {
            mDrawerLayout.openDrawer(GravityCompat.END);
            if (!isSuccessRequest) {
                Log.d(TAG, "onClick: ");
                isSuccessRequest = true;
                OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/project/tree/json", this);
            }
        }
    }

    boolean isSuccessRequest = false;

    @Override
    public void finish(String response) {
        ProjectBean nameBean = new Gson().fromJson(response, ProjectBean.class);
        simplifySomeDate(nameBean);
        getActivity().runOnUiThread(() -> {
            ProjectAdapter adapter = new ProjectAdapter(mProjectNameBeanList,getContext(),ProjectFragment.this);
            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(),R.anim.layout_entrace_anim);
            recyclerView.setLayoutAnimation(controller);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            loadingCloud.setVisibility(View.GONE);
            cloudAnimation.stop();
            recyclerView.setAdapter(adapter);

        });
    }




    @Override
    public void onError(Exception e) {
        Log.d(TAG, "onError: ");
    }

    private void simplifySomeDate(ProjectBean oldBean){
        for(int i = 0 ;i<oldBean.getData().size();i++){
            if(oldBean.getData().get(i).getName().length() == 3||oldBean.getData().get(i).getName().length() == 5||
                    oldBean.getData().get(i).getName().length() == 4||oldBean.getData().get(i).getName().length() == 6)
                mProjectNameBeanList.add(oldBean.getData().get(i));
        }
    }


    @Override
    public void getPassageId(Integer id) {
        Log.d(TAG, "getPassageId: " + id);
        keyId = id;
        updatePassage();

    }


    private void updatePassage() {

        page = 1;
        SharedPreferences pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/project/list/"+page+"/json?cid="+keyId, new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                passageBean = new Gson().fromJson(response,ProjectPassageBean.class);

                getActivity().runOnUiThread(() -> {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                    mPassageAdapter.initDate(passageBean);
                    mPassageAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(Exception e) {

            }
        } ,pref.getString("account",null),pref.getString("password",null));
    }

    //下拉刷新
    SmartRefreshLayout refreshLayout;
    private TextView mTextView;

    //上拉加载
    private final int itemCount = 0;
    TextView loadingTextView;

    private void createSmartRefreshLayout() {


        refreshLayout = mView.findViewById(R.id.refreshLayout);
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

            updatePassage();
        });
        refreshLayout.finishRefresh();
    }


    private LottieAnimationView mLottieAnimationView;
    private void initSmartRefreshLayout() {
        RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
        mLottieAnimationView = mView.findViewById(R.id.loading);
        mLottieAnimationView.setAnimation(R.raw.loading);
        loadingTextView = mView.findViewById(R.id.text_loading);
        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            mLottieAnimationView.playAnimation();

            Log.d(TAG, ""+page);
            if(page<passageBean.getData().getPageCount()){
                page++;
                OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/project/list/"+page+"/json?cid="+keyId, new OkHttpCallbackListener() {
                    @Override
                    public void finish(String response) {
                        Log.d(TAG, "finish: ");
                        passageBean = new Gson().fromJson(response,ProjectPassageBean.class);
                        page = passageBean.getData().getCurPage();
                        Message message = new Message();
                        message.what = 4;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }else{
                loadingTextView.setText("加载完成");
            }
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
        message.what = 3;
        mHandler.sendMessage(message);

    }




}