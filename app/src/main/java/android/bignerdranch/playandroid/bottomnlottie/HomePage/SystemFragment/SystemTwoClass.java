package android.bignerdranch.playandroid.bottomnlottie.HomePage.SystemFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.readapp.R;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级分类的第二级，用来显示文章
 * 里面用tabLayout和VIewPager2的搭配来显示第二级的标题和文章
 */
public class SystemTwoClass extends BaseActivity {
    private static final String TAG = "SystemTwoClass";

    TabLayout mTabLayout;
    ViewPager2 mViewPager2;
    int count;
    private final List<Integer> mTwoClassId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_two_class);
        initView();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        createTabLayout(name);

        ImageView back = findViewById(R.id.back_one_class);
        back.setOnClickListener(view -> finish());



    }

    final List<Fragment> mFragments = new ArrayList<>();
    private void createViewPager2() {
        for(int i = 0;i<count ;i++){
            SystemPassageFragment fragment = new SystemPassageFragment(mTwoClassId.get(i));
            mFragments.add(fragment);
        }
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),getLifecycle());
        mViewPager2.setAdapter(adapter);
    }



    private void initView() {
        mTabLayout = findViewById(R.id.twoClassTabLayout);
        mViewPager2 = findViewById(R.id.viewPagerTwoClass);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.setScrollPosition(position,0,false);
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    }

    private void createTabLayout(String name) {
        Log.d(TAG, name);
        for (int i = 0; i < SystemFragment.mBean.getData().size(); i++) {

            if(SystemFragment.mBean.getData().get(i).getName().equals(name)){
                count = SystemFragment.mBean.getData().get(i).getChildren().size();

                for(int j = 0;j<SystemFragment.mBean.getData().get(i).getChildren().size();j++){
                    Log.d(TAG,j + "");
                    mTabLayout.addTab(mTabLayout.newTab().setText(SystemFragment.mBean.getData().get(i).getChildren().get(j).getName()));

                    mTwoClassId.add(SystemFragment.mBean.getData().get(i).getChildren().get(j).getId());
                    Log.d(TAG, SystemFragment.mBean.getData().get(i).getChildren().get(j).getName()+SystemFragment.mBean.getData().get(i).getChildren().get(j).getId());
                }
                createViewPager2();
                return;
            }
        }



    }

    class MyAdapter extends FragmentStateAdapter{

        public MyAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);

        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }
}