package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment;


import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout.ChooseHead;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout.MyDialogActivity;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout.UserBean;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.ProjectFragment.ProjectFragment;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.SystemFragment.SystemFragment;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.SearchActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.readapp.R;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是底部导航栏首页的第一个碎片
 * 在这个碎片里面加入了ViewPager2管理3个碎片
 */
public class HomePageFragment extends Fragment {
    View view;

    static public ImageView mHeadPortrait;
    static public ImageView mDLHead;
    static public ConstraintLayout headView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("hello", "onCreateView: 4");
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_page,container,false);
        createToolBar();
        jumpSearch();
        createTabLayout();

        return view;
    }


    public static ConstraintLayout getHeadView() {
        return headView;
    }





    //顶部导航栏
    final private String[] tabs = {"首页","体系","项目"};
    private final List<View> mTabFragmentList = new ArrayList<>();
    boolean tab_created = false;


    List<String> mList2 = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;


    private final List<String> mList = new ArrayList<>();


    private View mSystemView;

    public int i =0;



    private void jumpSearch() {
        CardView cardView = view.findViewById(R.id.toolbar_card_view);
        cardView.setOnClickListener(view -> {
            Intent intent  = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }

    static public ImageView getmHeadPortrait() {
        return mHeadPortrait;
    }

    static public ImageView getPicture(){
        return mDLHead;
    }

    static LinearLayout myTitle;

    static public LinearLayout getLinearLayout() {
        return myTitle;
    }

    private static final String TAG = "MyNameIs";
    private void createToolBar() {
        myTitle = view.findViewById(R.id.my_title);
        mHeadPortrait = myTitle.findViewById(R.id.head_portrait);

        headView = view.findViewById(R.id.headView);
        mDLHead = headView.findViewById(R.id.drawerLayout_headPortrait);

        ImageView search = myTitle.findViewById(R.id.search);

        List<UserBean> beans = LitePal.findAll(UserBean.class);

        String imagePath = null;
        String id1 = null;
        SharedPreferences pref = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        for (UserBean bean : beans) {
            Log.d(TAG, "createToolBar: ");
            Log.d(TAG, bean.getAccount()+"hello"+pref.getString("account",null));
            if(bean.getAccount().equals(pref.getString("account",null))){

                imagePath = bean.getImagePath();
                id1 = bean.getId1();
                break;
            }
        }


        Log.d(TAG, imagePath+id1);
        LinearLayout headDrawerLayout = view.findViewById(R.id.drawerLayout);
        if(imagePath == null){
            Glide.with(myTitle).load(R.drawable.ic_launcher).circleCrop().into(mHeadPortrait);
            Glide.with(myTitle).load(R.drawable.ic_launcher).circleCrop().into(mDLHead);
        }else{
            Bitmap bitmap;
            if(id1.equals("1")){
                Log.d(TAG, "createToolBar: 1");
                bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(Uri.parse(imagePath)));
                    Glide.with(myTitle).load(bitmap).circleCrop().into(mHeadPortrait);
                    Glide.with(myTitle).load(bitmap).circleCrop().into(mDLHead);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(id1.equals("2")){
                Log.d(TAG, "createToolBar: 2");
                bitmap = BitmapFactory.decodeFile(imagePath);
                Glide.with(myTitle).load(bitmap).circleCrop().into(mHeadPortrait);
                Glide.with(myTitle).load(bitmap).circleCrop().into(mDLHead);
            }else {

                Glide.with(headDrawerLayout).load(R.drawable.head_portrait)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(mDLHead);
                Glide.with(myTitle)
                        .load(R.drawable.head_portrait)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(mHeadPortrait);
            }
        }
        Glide.with(myTitle).load(R.drawable.research)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(search);


        Toolbar mToolbar = myTitle.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        DrawerLayout drawerLayout = view.findViewById(R.id.my_drawLayout);


        mDLHead.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), ChooseHead.class);
            getActivity().startActivity(intent);
        });

        mHeadPortrait.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));


        CardView exit = view.findViewById(R.id.exit);
        createSnackBar(exit);

        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userName = sp.getString("account","但是米线很好吃");
        TextView accountName = headView.findViewById(R.id.account_name);
        accountName.setText(userName);
    }

    private void createSnackBar(CardView exit) {
        exit.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyDialogActivity.class);
            getContext().startActivity(intent);
        });
    }



    //顶部导航栏
    private void createTabLayout() {
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager2 = view.findViewById(R.id.view_pager2);
        for(String a:tabs){
            mTabLayout.addTab(mTabLayout.newTab().setText(a));
        }

        HomePageTwoFragment homePageTwoFragment = new HomePageTwoFragment();
        SystemFragment systemFragment = new SystemFragment();
        ProjectFragment projectFragment = new ProjectFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(homePageTwoFragment);
        fragments.add(systemFragment);
        fragments.add(projectFragment);

        ViewPager2Adapter adapter = new ViewPager2Adapter(getChildFragmentManager(),getLifecycle(),fragments);
        //关闭预加载

        mViewPager2.setAdapter(adapter);
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







}