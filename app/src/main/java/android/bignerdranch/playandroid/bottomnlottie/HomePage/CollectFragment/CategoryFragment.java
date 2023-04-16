package android.bignerdranch.playandroid.bottomnlottie.HomePage.CollectFragment;

import android.bignerdranch.playandroid.util.LogUtil;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * 收藏页面的碎片
 */
public class CategoryFragment extends Fragment implements OkHttpCallbackListener{

    private CategoryFragment mCategoryFragment;

    int page;
    int pageTotal;


    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){

                mAdapter = new CollectAdapter(getContext(),getActivity(),mBean);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_entrace_anim);
                mRecyclerView.setLayoutAnimation(controller);
                mRecyclerView.setAdapter(mAdapter);
                pageTotal = mBean.getData().getPageCount();
                page++;
                mRecyclerView.setVisibility(View.VISIBLE);
                mTitle.setVisibility(View.VISIBLE);
                mRabbit.setVisibility(View.GONE);
                mDrawable.stop();
            }
            if (message.what == 2){
                mAdapter.setList(mBean);
                mAdapter.notifyItemInserted(mAdapter.getItemCount());
            }
            if (message.what == 3) {
                mAdapter.notifyList(mBean);
                mAdapter.notifyItemRemoved(mPosition);
                mAdapter.notifyItemRangeChanged(mPosition,mBean.getData().getSize());

            }

            return false;
        }
    });
    private SharedPreferences mSp;
    private CollectAdapter mAdapter;

    private MyCollectBean mBean;
    private AnimationDrawable mDrawable;
    private View mView;
    private RecyclerView mRecyclerView;
    private ImageView mRabbit;
    private TextView mTitle;
    private int mPosition;



    boolean isCreated = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("hello", "onCreateView: 5");
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_category,container,false);
        iniView();

        mRecyclerView.setVisibility(View.GONE);
        mDrawable=  (AnimationDrawable) mRabbit.getDrawable();
        mDrawable.start();


        mTitle.setVisibility(View.GONE);
        page = 0;
        Log.d(TAG, "onCreateView: ");
        getCollectPassage();
        initRefresh();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyCollectDialogActivity.isDO()==1){
            deletePassage();
            MyCollectDialogActivity.setA(0);
        }
    }

    private void deletePassage() {
        mPosition = mAdapter.position;
        unCollect(mPosition);
        Log.d(TAG, "deletePassage: "+mPosition);
    }
    public void unCollect(int position) {
        SharedPreferences preferences = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        String account = preferences.getString("account",null);
        String password = preferences.getString("password",null);
        List<MyCollectBean.DataBean.DatasBean> list = mAdapter.getList();

        OkHttpUtil.unCollectPostRequestWithOkhttp(list.get(position).getId().toString(),list.get(position).getOriginId().toString(), new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                getActivity().runOnUiThread(() -> {
                    ToastUtil.showToast("取消收藏",getContext(), Toast.LENGTH_SHORT);
                    update();
                    Log.d(TAG, "run: "+position);
                });

            }

            @Override
            public void onError(Exception e) {

            }
        },"loginUserName="+account,"loginUserPassword="+password);

    }

    private void update() {
        page=0;
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/lg/collect/list/" + page + "/json", new OkHttpCallbackListener() {
                    @Override
                    public void finish(String response) {
                        Log.d(TAG, "finish: "+response);
                        page++;
                        mBean = new Gson().fromJson(response,MyCollectBean.class);
                        Log.d(TAG, "Bean更新了");
                        Message message = new Message();
                        message.what = 3;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }
                , mSp.getString("account", ""), mSp.getString("password", ""));
    }

    private void iniView() {
        mRecyclerView = mView.findViewById(R.id.collect_recycleView);
        mRabbit = mView.findViewById(R.id.rabbit_collect_1);
        mTitle = mView.findViewById(R.id.collect_title);
    }

    private void initRefresh() {
        RefreshLayout collectRefresh = mView.findViewById(R.id.collect_refresh);
        collectRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        collectRefresh.setOnLoadMoreListener(refreshLayout -> {
            Log.d(TAG, page+"");
            if(page<pageTotal){
                sendRequestGainCollect();
            }


            collectRefresh.finishLoadMore(1800);
        });

    }

    private void sendRequestGainCollect() {
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/lg/collect/list/" + page + "/json", new OkHttpCallbackListener() {
                    @Override
                    public void finish(String response) {
                        page++;
                        mBean = new Gson().fromJson(response,MyCollectBean.class);
                        Message message = new Message();
                        message.what =  2;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                }
                , mSp.getString("account", ""), mSp.getString("password", ""));

    }

    private void getCollectPassage() {
        mSp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        OkHttpUtil.sendGetRequestWithOkHttp("https://www.wanandroid.com/lg/collect/list/"+page+"/json",this
        ,mSp.getString("account",""), mSp.getString("password",""));
    }



    @Override
    public void finish(String response) {
        mBean = new Gson().fromJson(response,MyCollectBean.class);
        Message message = new Message();
        message.what = 1;
        mHandler.sendMessage(message);
    }


    @Override
    public void onError(Exception e) {
        LogUtil.d(TAG,"onError");
    }


    private static final String TAG = "CategoryFragment";


}