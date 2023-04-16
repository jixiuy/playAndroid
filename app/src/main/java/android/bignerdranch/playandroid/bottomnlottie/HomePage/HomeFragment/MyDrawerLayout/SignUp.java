package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.Homepage;

import android.bignerdranch.playandroid.util.BaseActivity;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.readapp.databinding.ActivitySignUpBinding;
import com.google.gson.Gson;

/**
 * 登录界面
 */
public class SignUp extends BaseActivity implements View.OnClickListener, OkHttpCallbackListener {

    ActivitySignUpBinding binding;
    String response;

    final Handler mHandler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){
                if(mBean.getErrorCode().equals(-1))
                   ToastUtil.showToastBottom(mBean.getErrorMsg(),SignUp.this, Toast.LENGTH_SHORT);
                else{

                    UserBean bean = new UserBean();
                    bean.setPassword(mBean.getData().getUsername());
                    bean.setAccount(binding.signUpPassword.getText().toString());
                    bean.save();

                    ToastUtil.showToastBottom("注册成功",SignUp.this, Toast.LENGTH_SHORT);
                    SharedPreferences.Editor userEdit = getSharedPreferences("user",MODE_PRIVATE).edit();
                    userEdit.putString("account", mBean.getData().getUsername());
                    userEdit.putString("ok","ok");
                    userEdit.putString("password",binding.signUpPassword.getText().toString());
                    userEdit.apply();
                    Intent intent = new Intent(SignUp.this, Homepage.class);
                    finish();
                    startActivity(intent);
                }
            }
            return false;
        }
    });
    private SignUpErrorBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this);
        initListener();

    }

    private void initListener() {
        binding.backSignIn.setOnClickListener(this);
        binding.signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_sign_in:{
                finish();
            }break;
            case R.id.sign_up:{
                OkHttpUtil.sendSignInRequestWithOkhttp("https://www.wanandroid.com/user/register",this,
                        binding.textView8.getText().toString(),binding.signUpPassword.getText().toString(),binding.textView6.getText().toString());
            }break;
        }
    }

    @Override
    public void finish(String response) {
        Log.d(TAG, response);
        mBean = new Gson().fromJson(response,SignUpErrorBean.class);
        Message message =new Message();
        message.what = 1;
        mHandler.sendMessage(message);
        this.response = response;
    }

    @Override
    public void onError(Exception e) {
        Log.d(TAG, "onError:");
    }

    private static final String TAG = "SignUp";
}