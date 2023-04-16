package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import android.app.ActionBar;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.bean.ItemBean;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.SearchPassage;
import android.bignerdranch.playandroid.util.JudgeNetUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readapp.R;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 整个项目最傻的地方
 * 写了一大堆我也不知道在做什么的RecycleView签到RecycleView的东西，刚开始写的第一个功能，有点手生，后面就不会这样了
 */
public class SearchFragment extends Fragment implements ResponseListener {
    private static final String TAG = "hello";
    OftenUsedWebsiteBean mOftenUsedWebsiteBean;
    private HotDateBean mHotDateBean;
    final List<String> yuan_ma = new ArrayList<>();
    final List<String> yuan_ma_link = new ArrayList<>();
    final List<String> guan_fang = new ArrayList<>();
    final List<String> guan_fang_link = new ArrayList<>();
    final List<String> cang_ku = new ArrayList<>();
    final List<String> cang_ku_link = new ArrayList<>();
    final List<String> bo_ke = new ArrayList<>();
    final List<String> bo_ke_link = new ArrayList<>();
    final List<String> ji_shu_zhan = new ArrayList<>();
    final List<String> ji_shu_zhan_link = new ArrayList<>();
    final List<String> gong_ju = new ArrayList<>();
    final List<String> gong_ju_link = new ArrayList<>();
    final List<String> mian_shi = new ArrayList<>();
    final List<String> mian_shi_link = new ArrayList<>();
    final List<String> git = new ArrayList<>();
    final List<String> git_link = new ArrayList<>();
    final List<String> kotlin = new ArrayList<>();
    final List<String> kotlin_link = new ArrayList<>();
    final List<String> xiang_mu = new ArrayList<>();
    final List<String> xiang_mu_link = new ArrayList<>();
    final List<String> she_ji = new ArrayList<>();
    final List<String> she_ji_link = new ArrayList<>();
    final List<String> jian_li = new ArrayList<>();
    final List<String> jian_li_link = new ArrayList<>();
    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 1) {
                RecyclerView hotRecyclerView = mView.findViewById(R.id.hot_vocabulary);
                MyHotVocabularyAdapter myHotVocabularyAdapter = new MyHotVocabularyAdapter();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.hot_anim));
                hotRecyclerView.setLayoutAnimation(controller);
                hotRecyclerView.setLayoutManager(gridLayoutManager);
                hotRecyclerView.setAdapter(myHotVocabularyAdapter);
            }
            if(message.what == 2){
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("源码")){
                        yuan_ma.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        yuan_ma_link.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("官方")){
                        guan_fang.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        guan_fang_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("仓库")){
                        cang_ku.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        cang_ku_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("博客")){
                        bo_ke.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        bo_ke_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("技术站")){
                        ji_shu_zhan.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        ji_shu_zhan_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("工具")){
                        gong_ju.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                       gong_ju_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("面试")){
                        mian_shi.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        mian_shi_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("Git")){
                        git.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        git_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("Kotlin")){
                        kotlin.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        kotlin_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("项目")){
                        xiang_mu.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        xiang_mu_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("设计")){
                        she_ji.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        she_ji_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                for (int j = 0 ;j < mOftenUsedWebsiteBean.getData().size() ;j++){
                    if(mOftenUsedWebsiteBean.getData().get(j).getCategory().equals("简历")){
                        jian_li.add(mOftenUsedWebsiteBean.getData().get(j).getName());
                        jian_li_link.add(mOftenUsedWebsiteBean.getData().get(j).getLink());
                    }
                }
                RecyclerView recyclerView = getActivity().findViewById(R.id.recycle_parent);
                MyAdapter2 myAdapter = new MyAdapter2();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.hot_anim));
                recyclerView.setLayoutAnimation(controller);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(myAdapter);
            }
            return false;
        }
    });
    private View mView;
    LayoutAnimationController controller;
    private MyAdapter mMyAdapter;
    boolean isCreated = false;
    final List<String> list = new ArrayList<>();
    private final List<String> mDate  = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mDeleteAll;
    private TextView mHistorySearch;
    private View mView1;
    @Override
    public void response(Response response) {
        try {
            String responseDate = response.body().string();
            mOftenUsedWebsiteBean = new Gson().fromJson(responseDate, OftenUsedWebsiteBean.class);
            Message message = new Message();
            message.what = 2;
            mHandler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void fail() {
        Log.d(TAG, "连接失败");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(!isHave &&time==1){
            netWork.setVisibility(View.GONE);
            onMyCreate();
        }
        time  = 1;
    }
    int time = 0;
    boolean isHave  = false;
    ImageView netWork;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        netWork = mView.findViewById(R.id.search_netWork);
        if(JudgeNetUtil.isNetworkConnected(getContext())){
            isHave = true;
            netWork.setVisibility(View.GONE);
        }else {
            isHave = false;
            netWork.setVisibility(View.VISIBLE);
        }
        onMyCreate();
        return mView;
    }
    private void onMyCreate() {
        SendRequestWithOkhttpUtl.sendRequestWithOkhttp(this,"https://www.wanandroid.com/friend/json");
        ImageView delete = mView.findViewById(R.id.imageView4);
        EditText search = mView.findViewById(R.id.search_editText);
        delete.setOnClickListener(view -> search.setText(""));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() != 0){
                    delete.setVisibility(View.VISIBLE);
                }else {
                    delete.setVisibility(View.INVISIBLE);
                }
            }
        });
        ImageView back = mView.findViewById(R.id.back);
        back.setOnClickListener(view -> getActivity().finish());
        mDeleteAll = mView.findViewById(R.id.delete);
        mHistorySearch = mView.findViewById(R.id.history_search);
        mView1 = mView.findViewById(R.id.view);
        mDeleteAll.setOnClickListener(view -> {
            MyDialog myDialog = new MyDialog(getActivity());
            myDialog.setsCancel("确定", view12 -> {
                LitePal.deleteAll(ItemBean.class);
                list.clear();
                updateDate();
                myDialog.dismiss();
            }).setsConfirm("取消", view1 -> myDialog.dismiss()).show();
        });
        if(!isCreated){
            isCreated = true;
            LitePal.getDatabase();
            mRecyclerView = mView.findViewById(R.id.history_list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mMyAdapter = new MyAdapter(mDate,getActivity().getApplicationContext());
            mRecyclerView.setAdapter(mMyAdapter);
            updateDate();
        }
        List<ItemBean> beans= LitePal.findAll(ItemBean.class);
        for(ItemBean a:beans){
            list.add(a.getItem());
        }
        TextView searchTextView = mView.findViewById(R.id.search_button);
        searchTextView.setOnClickListener(view -> {
            String content = search.getText().toString();
            Intent intent = new Intent(getActivity(), SearchPassage.class);
            intent.putExtra("key",search.getText().toString());
            getActivity().startActivity(intent);
            if(content.equals("")) return;
            else{
                List<ItemBean> itemBean = LitePal.findAll(ItemBean.class);
                boolean flag = false;
                Log.d(TAG, itemBean.size()+"itemBean.size()");
                for(ItemBean a:itemBean){
                    if(search.getText().toString().equals(a.getItem())){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    Toast.makeText(getActivity(),"该值已经添加过了",Toast.LENGTH_SHORT).show();
                }else {
                    ItemBean item = new ItemBean();
                    item.setItem(content);
                    item.save();
                    list.add(content);
                }
                mDeleteAll.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mHistorySearch.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                updateDate();
            }
        });
        updateDate();
        sendRequestWithOkhttp();
    }
    public void search(String hot){
        Intent intent = new Intent(getActivity(), SearchPassage.class);
        intent.putExtra("key",hot);
        getActivity().startActivity(intent);
        List<ItemBean> itemBean = LitePal.findAll(ItemBean.class);
        boolean flag = false;
        for(ItemBean a:itemBean){
            if(hot.equals(a.getItem())){
                flag = true;
                break;
            }
        }
        if(flag){
            return;
            }else {
            ItemBean item = new ItemBean();
            item.setItem(hot);
            item.save();
            list.add(hot);
            }
            mDeleteAll.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mHistorySearch.setVisibility(View.VISIBLE);
            updateDate();
    }
    public void updateDate(){
        mDate.clear();
        if(list.size()<10){
            mDate.addAll(list);
        }else {
            for(int i = list.size()-10;i<list.size();i++){
                mDate.add(list.get(i));
            }
        }
        getActivity().runOnUiThread(() -> mMyAdapter.notifyDataSetChanged());
        if(mDate.size() == 0){
            mDeleteAll.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mHistorySearch.setVisibility(View.GONE);
            mView1.setVisibility(View.GONE);
        }else {
            mDeleteAll.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mHistorySearch.setVisibility(View.VISIBLE);
            mView1.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        final List<String> mStringList;
        final Context mContext;
        public MyAdapter(List<String> list, Context applicationContext) {
            this.mContext = applicationContext;
            this.mStringList  = list;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.view.setOnClickListener(view1 -> search(mStringList.get(viewHolder.getAdapterPosition())));
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextView.setText(mStringList.get(position));
        }
        @Override
        public int getItemCount() {
            return mStringList.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mTextView;
            final View view;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.hot_vocabulary_textview);
                view = itemView;
            }
        }
    }
    class MyHotVocabularyAdapter extends RecyclerView.Adapter<MyHotVocabularyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_view_hot, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.view.setOnClickListener(view1 -> search(mHotDateBean.getData().get(viewHolder.getAdapterPosition()).getName()));
            return  viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextView.setText(mHotDateBean.getData().get(position).getName());
        }
        @Override
        public int getItemCount() {
            return 9;
        }
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mTextView;
            final View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.hot_vocabulary_textview);
                view = itemView;
            }
        }
    }
    private void sendRequestWithOkhttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://www.wanandroid.com//hotkey/json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                mHotDateBean = new Gson().fromJson(responseDate, HotDateBean.class);
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        });
    }
    class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder>{
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_first,parent,false);
            int columnWidth;
            columnWidth = (int) (parent.getMeasuredWidth()/1.5f);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(columnWidth,RecyclerView.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(position == 0){
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                MyTopAdapter myTopAdapter = new MyTopAdapter(yuan_ma,yuan_ma_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);
                holder.mTextViewTop.setText("源码");
                holder.mTextViewBottom.setText("官方");
                holder.topRecyclerView.setLayoutAnimation(controller);
                MyBottomAdapter bottomAdapter = new MyBottomAdapter(guan_fang,guan_fang_link);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }else if(position == 1){
                holder.mTextViewTop.setText("仓库");
                holder.mTextViewBottom.setText("博客");
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.topRecyclerView.setLayoutAnimation(controller);
                MyTopAdapter myTopAdapter = new MyTopAdapter(cang_ku,cang_ku_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);
                MyBottomAdapter bottomAdapter = new MyBottomAdapter(bo_ke,bo_ke_link);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }
            else if(position == 2){
                holder.mTextViewTop.setText("技术站");
                holder.mTextViewBottom.setText("工具");
                holder.topRecyclerView.setLayoutAnimation(controller);
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                MyTopAdapter myTopAdapter = new MyTopAdapter(ji_shu_zhan,ji_shu_zhan_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);

                MyBottomAdapter bottomAdapter = new MyBottomAdapter(gong_ju,gong_ju_link);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }
            else if(position == 3){
                holder.mTextViewTop.setText("面试");
                holder.mTextViewBottom.setText("Git");
                holder.topRecyclerView.setLayoutAnimation(controller);
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                MyTopAdapter myTopAdapter = new MyTopAdapter(mian_shi,mian_shi_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                MyBottomAdapter bottomAdapter = new MyBottomAdapter(git,git_link);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }
            else if(position == 4){
                holder.mTextViewTop.setText("Kotlin");
                holder.mTextViewBottom.setText("项目");
                holder.topRecyclerView.setLayoutAnimation(controller);
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                MyTopAdapter myTopAdapter = new MyTopAdapter(kotlin,kotlin_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                MyBottomAdapter bottomAdapter = new MyBottomAdapter(xiang_mu,xiang_mu_link);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }
            else if(position == 5){
                holder.mTextViewTop.setText("设计");
                holder.mTextViewBottom.setText("简历");
                holder.topRecyclerView.setLayoutAnimation(controller);
                holder.topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                MyTopAdapter myTopAdapter = new MyTopAdapter(she_ji,she_ji_link);
                holder.topRecyclerView.setAdapter(myTopAdapter);
                holder.bottomRecyclerView.setLayoutAnimation(controller);
                MyBottomAdapter bottomAdapter = new MyBottomAdapter(jian_li,jian_li_link);
                holder.bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                holder.bottomRecyclerView.setAdapter(bottomAdapter);
            }
        }
        @Override
        public int getItemCount() {
            return 6;
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            final RecyclerView topRecyclerView;
            final RecyclerView bottomRecyclerView;
            final TextView mTextViewTop;
            final TextView mTextViewBottom;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewTop = itemView.findViewById(R.id.top_title);
                mTextViewBottom = itemView.findViewById(R.id.bottom_title);
                topRecyclerView = itemView.findViewById(R.id.top);
                bottomRecyclerView = itemView.findViewById(R.id.bottom);
            }
        }
    }
    class MyBottomAdapter extends RecyclerView.Adapter<MyBottomAdapter.ViewHolder>{
        final List<String> content;
        final List<String> link;
        public MyBottomAdapter(List<String> content,List<String> link){
            this.content = content;
            this.link = link;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.often_used_list,parent,false);
            ViewHolder holder = new ViewHolder(view);
            holder.mView.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), DisplayActivity.class);
                intent.putExtra("url",link.get(holder.getAdapterPosition()));
                getActivity().startActivity(intent);
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextViewContent.setText(content.get(position));
            holder.mTextViewPosition.setText(position+1+"");
        }
        @Override
        public int getItemCount() {
            return content.size();
        }
        private class ViewHolder extends RecyclerView.ViewHolder{
            final TextView mTextViewContent ;
            final TextView mTextViewPosition ;
            final View mView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewContent = itemView.findViewById(R.id.liquid_exit);
                mTextViewPosition = itemView.findViewById(R.id.textView2);
                mView = itemView;
            }
        }
    }
    class MyTopAdapter extends RecyclerView.Adapter<MyTopAdapter.ViewHolder>{
        final List<String> content;
        final List<String> link;
        public MyTopAdapter(List<String> content, List<String> link){
            this.content = content;
            this.link = link;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view  = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.often_used_list,parent,false);
            ViewHolder holder = new ViewHolder(view);
            holder.view.setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), DisplayActivity.class);
                intent.putExtra("url",link.get(holder.getAdapterPosition()));
                getActivity().startActivity(intent);
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mTextViewContent.setText(content.get(position));
            holder.mTextViewPosition.setText(position+1+"");
        }
        @Override
        public int getItemCount() {

            return content.size();
        }
        private class ViewHolder extends RecyclerView.ViewHolder{
            final TextView mTextViewContent ;
            final TextView mTextViewPosition ;
            final View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextViewContent = itemView.findViewById(R.id.liquid_exit);
                mTextViewPosition = itemView.findViewById(R.id.textView2);
                view = itemView;
            }
        }
    }
}