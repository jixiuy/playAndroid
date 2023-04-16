package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 顾名思义VIewPager2的适配器
 */
public class ViewPager2Adapter extends FragmentStateAdapter {
    final List<Fragment> mList ;
    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,List<Fragment> mList ) {
        super(fragmentManager, lifecycle);
        this.mList = mList;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
