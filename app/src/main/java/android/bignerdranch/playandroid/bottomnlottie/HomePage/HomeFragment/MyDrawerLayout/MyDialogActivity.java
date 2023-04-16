package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import androidx.appcompat.app.AlertDialog;

import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyLiquid.LiquidActivity;
import android.bignerdranch.playandroid.util.ActivityCollector;
import android.bignerdranch.playandroid.util.BaseActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

/**
 * 点退出的时候有两种选择，退出玩安卓还是退出登录
 * 前者是利用活动的管理的基类退出所有的活动
 * 后者还是退出所有的活动，但是会跳转到登录界面
 */
public class MyDialogActivity extends BaseActivity {

    private View mView;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_dialog);
        mView = findViewById(R.id.my_dialog_activity);
        getWindow().setGravity(Gravity.BOTTOM);
        initMySnackBar();
    }

    public void initMySnackBar(){
        mSnackbar = Snackbar.make(mView,"我是SnackBar",Snackbar.LENGTH_INDEFINITE);
        //修改默认背景
        mSnackbar.getView().setBackgroundColor(getResources().getColor(R.color.white));
        //得到源布局
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        View mySnackView = LayoutInflater.from(this).inflate(R.layout.my_snack_bar,null);
        TextView exitAccount = mySnackView.findViewById(R.id.exit_account_tv);
        exitAccount.setText("退出玩安卓登录");
        exitAccount.setTextColor(0xff333333);
        exitAccount.setOnClickListener(view -> showAlertDialog());
        TextView exit = mySnackView.findViewById(R.id.exit);
        exit.setOnClickListener(view -> {

            Intent intent = new Intent(MyDialogActivity.this, LiquidActivity.class);
            startActivity(intent);
        });
        layout.addView(mySnackView,0);

        mSnackbar.show();

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyDialogActivity.this,R.style.MyAlertButton);
        builder.setTitle("");
        TextView textView = new TextView(this);
        textView.setText("确定退出当前账号吗？");
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(50,50,10,10);

        builder.setCustomTitle(textView);
        builder.setCancelable(false);
        builder.setPositiveButton("退出", (dialogInterface, i) -> {
            ActivityCollector.finishAll();
            Intent intent = new Intent(MyDialogActivity.this,SignIn.class);
            startActivity(intent);
            SharedPreferences.Editor userEdit = getSharedPreferences("user",MODE_PRIVATE).edit();
            userEdit.putString("sign_in","no");
            userEdit.putString("account","");
            userEdit.putString("password","");
            userEdit.commit();
        });
        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            mSnackbar.dismiss();
            finish();
        });
        builder.show();

    }

}