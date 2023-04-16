package android.bignerdranch.playandroid.bottomnlottie.HomePage.RemoteVIew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.DisplayActivity;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.OkhttpListener;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.PassageBean;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.SearchPassage;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.search_result.SendRequestWthOkhttp;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知栏，其实他是一个悬浮式的Dialog，通知栏只是用来打开它
 * 启动模式中的第四种，打开后会创建一个另外的返回栈
 */
public class DialogActivity extends BaseActivity implements OkhttpListener {

    ImageView back;
    ImageView search;
    EditText et;
    RecyclerView rv;
    ImageView monkey;
    AnimationDrawable monkeyDrawable;
    TextView tv;

    private static final String TAG = "DialogActivity";
    final List<PassageBean.DataBean.DatasBean> mBeanList = new ArrayList<>();

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){
                MyRemoteRvAdapter adapter = new MyRemoteRvAdapter();
                LayoutAnimationController loadLayoutAnimation = AnimationUtils.loadLayoutAnimation(DialogActivity.this, R.anim.layout_entrace_anim);
                rv.setLayoutManager(new LinearLayoutManager(DialogActivity.this));
                rv.setLayoutAnimation(loadLayoutAnimation);
                monkeyDrawable.stop();
                rv.setVisibility(View.VISIBLE);
                monkey.setVisibility(View.GONE);
                rv.setAdapter(adapter);
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog2);
        initView();

        monkey.setVisibility(View.GONE);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                if(TextUtils.isEmpty(value)){
                    search.setVisibility(View.VISIBLE);
                    initWindow();
                    Log.d(TAG, "afterTextChanged: ");
                }else {
                    key = editable.toString();
                    search.setVisibility(View.GONE);
                    initWindow2();
                    rv.setVisibility(View.GONE);
                    monkey.setVisibility(View.VISIBLE);
                    monkeyDrawable = (AnimationDrawable) monkey.getDrawable();
                    monkeyDrawable.start();
                    gainDate();
                    Log.d(TAG, "afterTextChanged: 2");
                }
            }
        });
        et.setOnEditorActionListener((textView, i, keyEvent) -> {
            search.setVisibility(View.VISIBLE);
            return false;
        });

        back.setOnClickListener(view -> finish());

        tv.setOnClickListener(view -> {
            Intent intent = new Intent(DialogActivity.this, SearchPassage.class);
            intent.putExtra("key",et.getText().toString());
            startActivity(intent);
            finish();
        });
    }

    private void initView() {
        back = findViewById(R.id.remote_back);
        search =findViewById(R.id.remote_search);
        et = findViewById(R.id.remote_et);
        rv = findViewById(R.id.remote_rv);
        monkey = findViewById(R.id.remote_anim);
        tv = findViewById(R.id.remote_button);
    }

    String key;
    public void gainDate(){
        SendRequestWthOkhttp.postWithOkhttp("https://www.wanandroid.com/article/query/"+0+"/json",key,this);
    }

    @Override
    public void  onAttachedToWindow() {
        super.onAttachedToWindow();
        initWindow();
    }

    private void initWindow() {
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity =  Gravity.TOP;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.y = 0;
        lp.x = 0;
        lp.height = 280;

        //重新设置窗体的位置和大小
        getWindowManager().updateViewLayout(view,lp);
        //设置圆角
        getWindow().setBackgroundDrawableResource(R.drawable.round_orner);
    }
    private void initWindow2() {
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity =  Gravity.TOP;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.y = 0;
        lp.x = 0;

        //重新设置窗体的位置和大小
        getWindowManager().updateViewLayout(view,lp);
        //设置圆角
        getWindow().setBackgroundDrawableResource(R.drawable.round_orner);
    }


    PassageBean mBean;
    @Override
    public void onResponse(String responseDate) {
        mBeanList.clear();
        mBean = new Gson().fromJson(responseDate,PassageBean.class);
        mBeanList.addAll(mBean.getData().getDatas());
        Message message = new Message();
        message.what = 1;
        mHandler.sendMessage(message);

    }

    @Override
    public void onFail() {

    }


    class MyRemoteRvAdapter extends RecyclerView.Adapter<MyRemoteRvAdapter.ViewHolder>{

        public MyRemoteRvAdapter() {

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DialogActivity.this).inflate(R.layout.remote_rv_item,parent, false);
            ViewHolder holder = new ViewHolder(view);
            holder.title.setTextSize(16);
            holder.author.setTextSize(14);
            holder.date.setTextSize(14);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String title = dealWithDate(mBeanList.get(position).getTitle());
            holder.title.setText(title);

            if(mBeanList.get(position).getAuthor().equals("")){
                holder.author.setText(mBeanList.get(position).getShareUser());

            }else {
                holder.author.setText(mBeanList.get(position).getAuthor());

            }
            holder.date.setText(mBeanList.get(position).getNiceDate());

            holder.view.setOnClickListener(view -> {
                Intent intent = new Intent(DialogActivity.this, DisplayActivity.class);
                intent.putExtra("url",mBeanList.get(holder.getAdapterPosition()).getLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return mBeanList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            final TextView title;
            final TextView author;
            final TextView date;
            final View view;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.remote_title);
                author = itemView.findViewById(R.id.remote_author);
                date = itemView.findViewById(R.id.remote_date);
                view = itemView;
            }
        }
    }

    private String dealWithDate(String title) {
        String titleDeal = title;
        while(titleDeal.contains("<em class='highlight'>")||titleDeal.contains("</em>")){
            titleDeal = titleDeal.replace("<em class='highlight'>","");
            titleDeal = titleDeal.replace("</em>","");
        }
        return titleDeal;
    }
}