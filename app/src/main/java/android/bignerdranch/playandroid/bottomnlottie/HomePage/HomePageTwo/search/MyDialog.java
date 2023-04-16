package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import android.app.Dialog;
import com.example.readapp.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * 清空历史搜索记录的时候弹出dialogActivity
 */
public class MyDialog extends Dialog implements View.OnClickListener {
    private String sConfirm, sCancel;
    private View.OnClickListener cancelListener, confirmListener;
    public MyDialog setsConfirm(String sConfirm, View.OnClickListener listener) {
        this.sConfirm = sConfirm;
        this.confirmListener = listener;
        return this;
    }
    public MyDialog setsCancel(String sCancel, View.OnClickListener listener) {
        this.sCancel = sCancel;
        this.cancelListener = listener;
        return this;
    }
    public MyDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog_layout);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        WindowManager windowManager = getWindow().getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        Point size = new Point();
        defaultDisplay.getSize(size);
        layoutParams.width = (int) ((size.x)*0.8);        //设置为屏幕的0.8倍宽度
        getWindow().setAttributes(layoutParams);
        TextView cancel = findViewById(R.id.cancel);
        TextView confirm = findViewById(R.id.confirm);
        if (!TextUtils.isEmpty(sCancel)) {
            cancel.setText(sCancel);
        }
        if (!TextUtils.isEmpty(sConfirm)) {
            confirm.setText(sConfirm);
        }
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm:
                if(confirmListener != null){
                    confirmListener.onClick(view);
                }
                break;
            case R.id.cancel:
                if(cancelListener != null){
                    cancelListener.onClick(view);
                }
                break;
        }
    }
}

