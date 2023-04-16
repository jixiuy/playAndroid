package android.bignerdranch.playandroid.bottomnlottie.HomePage;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.coolindicator.sdk.CoolIndicator;
import com.just.agentweb.AgentWebUtils;
import com.just.agentweb.BaseIndicatorView;

/**
 * agentWeb里面打开的时候那个彩色加载的加载栏
 * 封装好的代码也当成工具类来用
 */
public class CoolIndicatorLayout extends BaseIndicatorView {
    final CoolIndicator mCoolIndicator;

    public CoolIndicatorLayout(Context context) {
        this(context,null);
    }

    public CoolIndicatorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public CoolIndicatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCoolIndicator = CoolIndicator.create((Activity) context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCoolIndicator.setProgressDrawable(context.getResources().getDrawable(com.coolindicator.sdk.R.drawable.default_drawable_indicator, context.getTheme()));
        } else {
            mCoolIndicator.setProgressDrawable(context.getResources().getDrawable(com.coolindicator.sdk.R.drawable.default_drawable_indicator));
        }
        this.addView(mCoolIndicator,offerLayoutParams());
    }

    @Override
    public void show() {
        this.setVisibility(VISIBLE);
        mCoolIndicator.start();
    }

    @Override
    public void hide() {
        mCoolIndicator.complete();
    }

    @Override
    public void setProgress(int newProgress) {

    }

    @Override
    public LayoutParams offerLayoutParams() {
        return new LayoutParams(-1, AgentWebUtils.dp2px(getContext(),3));
    }
}
