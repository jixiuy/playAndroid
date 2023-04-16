package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyLiquid;

import android.bignerdranch.playandroid.util.BaseActivity;
import android.os.Bundle;

import com.cuberto.liquid_swipe.LiquidPager;
import com.example.readapp.R;

/**
 * 退出的时候那个液态界面
 * 其实就是VIewPager的封装，适配器过时了，不过还能用
 */
public class LiquidActivity extends BaseActivity {

    LiquidPager pager;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquid);
        pager = findViewById(R.id.pager);
        mViewPager = new ViewPager(getSupportFragmentManager(),1);
        pager.setAdapter(mViewPager);

    }
}