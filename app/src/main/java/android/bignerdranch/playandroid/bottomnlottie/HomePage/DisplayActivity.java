package android.bignerdranch.playandroid.bottomnlottie.HomePage;


import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.readapp.R;
import com.just.agentweb.AgentWeb;

/**
 * 被打开的浏览器
 * 用到了AgentWeb
 */
public class DisplayActivity extends BaseActivity {
    private AgentWeb mAgentWeb;
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        CoolIndicatorLayout coolIndicatorLayout = new CoolIndicatorLayout(this);
        LinearLayout linearLayout = findViewById(R.id.web);
        mAgentWeb = AgentWeb.with(this)

                .setAgentWebParent(linearLayout,new LinearLayout.LayoutParams(-1,-1))//-1代表父布局
                //.useDefaultIndicator()//默认进度条 可带颜色
                .setCustomIndicator(coolIndicatorLayout)
                .interceptUnkownUrl()
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .createAgentWeb()
                .ready()
                .go(url);

        mWebView = mAgentWeb.getWebCreator().getWebView();

        mAgentWeb.getWebCreator().getWebParentLayout().setBackgroundColor(Color.parseColor("#33333333"));

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mWebView.canGoBack()){
            mWebView.goBack();
            mWebView.removeAllViews();
        }else{
            super.onBackPressed();
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            mWebView.removeAllViews();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

}