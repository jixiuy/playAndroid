package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import androidx.fragment.app.FragmentManager;
import com.example.readapp.R;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * 搜索的第一个布局，里面有很多的信息如历史搜索，热搜词，推荐网站，都放在了下面加载的碎片里
 */
public class SearchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        creaateFragment();
    }
    private void creaateFragment() {
        SearchFragment searchFragment = new SearchFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_contain, searchFragment).commit();
    }

}