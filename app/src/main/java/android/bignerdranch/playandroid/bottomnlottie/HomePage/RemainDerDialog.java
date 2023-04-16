package android.bignerdranch.playandroid.bottomnlottie.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.example.readapp.R;

/**
 * 用WorkManager定时打开的活动
 * 当用户使用了1个小时，会弹出提醒用户注意眼睛（这里只设置了30s）
 */
public class RemainDerDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remain_der_dialog);
        CardView cardView = findViewById(R.id.know);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


}