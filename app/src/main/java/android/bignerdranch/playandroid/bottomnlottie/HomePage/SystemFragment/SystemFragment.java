package android.bignerdranch.playandroid.bottomnlottie.HomePage.SystemFragment;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.OkhttpListener;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.SendRequestWthOkhttp;
import android.bignerdranch.playandroid.util.JudgeNetUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.readapp.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * VIewPager2第二个碎片，用来放二级分类的第一级
 */
public class SystemFragment extends Fragment {

    public final Handler mHandler = new Handler(message -> {

        if(message.what == 4){
            createTwoClass();
        }
        return false;
    });
    private View mView;
    private static final String TAG = "SystemFragment";
    private AnimationDrawable mDrawable;
    private ImageView mBilibiliLoading;
    private ImageView network;
    int time = 0;
    boolean isCreated = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_system,container,false);

        isCreated = JudgeNetUtil.isNetworkConnected(getContext());
        mBilibiliLoading = mView.findViewById(R.id.system_loading_anim);
        mBilibiliLoading.setVisibility(View.VISIBLE);
        mDrawable = (AnimationDrawable) mBilibiliLoading.getDrawable();
        mDrawable.start();

        SendRequestWthOkhttp.getWithOkhttp("https://www.wanandroid.com/tree/json", new OkhttpListener() {
            @Override
            public void onResponse(String responseDate) {
                mBean = new Gson().fromJson(responseDate,SystemBean.class);
                Message message = new Message();
                message.what = 4;
                mHandler.sendMessage(message);
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if(mDrawable.isRunning()&&time==1){
            Log.d(TAG, "onResume:1");
            isCreated = true;
            mBilibiliLoading.setVisibility(View.VISIBLE);
            mDrawable = (AnimationDrawable) mBilibiliLoading.getDrawable();
            mDrawable.start();

            SendRequestWthOkhttp.getWithOkhttp("https://www.wanandroid.com/tree/json", new OkhttpListener() {
                @Override
                public void onResponse(String responseDate) {
                    mBean = new Gson().fromJson(responseDate,SystemBean.class);
                    Message message = new Message();
                    message.what = 4;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onFail() {

                }
            });
        }
        time = 1;
    }

    ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.container = container;
        Log.d("hello", "onCreateView: 2");
        return mView;
    }

    private final List<String> mList = new ArrayList<>();
    static public SystemBean mBean;

    final List<String> mList2 = new ArrayList<>();
    private void createTwoClass() {
        for(int i = 0;i<mBean.getData().size();i++){
            mList.add(mBean.getData().get(i).getName());
        }

        for(int i = 0;i<mList.size();i++){
            if(mList.get(i).length()==4) {
                mList2.add(mList.get(i));

            }

        }
        RecyclerView recyclerView = mView.findViewById(R.id.system_recyclerview);
        SystemAdapter adapter = new SystemAdapter(getContext(),mList2,mBean);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mDrawable.stop();
        mBilibiliLoading.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);
        recyclerView.scheduleLayoutAnimation();
        //重新执行layoutAnimation操作
    }


}
