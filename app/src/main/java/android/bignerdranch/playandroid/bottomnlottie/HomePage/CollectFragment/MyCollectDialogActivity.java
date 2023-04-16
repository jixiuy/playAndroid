package android.bignerdranch.playandroid.bottomnlottie.HomePage.CollectFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.readapp.R;
import com.example.readapp.databinding.ActivityMyCollectDialogBinding;

import android.bignerdranch.playandroid.util.MyApplication;
import android.os.Bundle;
import android.view.View;

/**
 * 取消收藏时候打开的Dialog提醒
 */
public class MyCollectDialogActivity extends AppCompatActivity {
    ActivityMyCollectDialogBinding binding;

    static int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_collect_dialog);
        a = 0;

        binding.collectCardview.setOnClickListener(view -> {
            MyApplication.title = getIntent().getStringExtra("title");
            MyApplication.isCollectedHomePageTwo = true;
            MyApplication.isTopPassage = true;
            finish();
            a = 1;
        });

    }

    static int isDO(){
        return a;
    }
    static void setA(int b){
        a = b;
    }
}