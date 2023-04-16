package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.readapp.R;
import com.example.readapp.databinding.ActivitySurfaceBinding;

import android.bignerdranch.playandroid.bottomnlottie.HomePage.Homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

/**
 * 打开我的app的第一个页面，里面有很多动画，旋转啥的
 */
public class SurfaceActivity extends AppCompatActivity {

    ActivitySurfaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_surface);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"华康少女字体.ttf");
        binding.myAppName.setTypeface(typeface);

        binding.myBackground.animate().translationY(-2500).rotation(360).setDuration(1000).setStartDelay(4000);
        binding.one.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.two.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.three.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.four.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.five.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.myBackground.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.myLogo.animate().translationY(2500).setDuration(1000).setStartDelay(4000);
        binding.myAppName.animate().rotation(360).translationY(-2500).setDuration(1000).setStartDelay(4000);

        SharedPreferences shp = getSharedPreferences("user",MODE_PRIVATE);
        String singIn = shp.getString("sign_in", "no");
        new Handler().postDelayed(() -> {
            finish();
            if(singIn.equals("yes")){
                Intent intent = new Intent(SurfaceActivity.this, Homepage.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SurfaceActivity.this,SignIn.class);
                startActivity(intent);
            }

        },4700);


    }
}