package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.Homepage;

import android.bignerdranch.playandroid.util.LogUtil;
import android.bignerdranch.playandroid.util.OkHttpCallbackListener;
import android.bignerdranch.playandroid.util.OkHttpUtil;
import android.bignerdranch.playandroid.util.ToastUtil;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.readapp.databinding.SignInBinding;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.List;

/**
 * 注册界面
 */
public class SignIn extends AppCompatActivity implements View.OnClickListener{

    SignInBinding binding;
    private static final String TAG = "SignIn";
    private MyLogInBean mLogInBean;

    final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what == 1){
                if(mLogInBean.getErrorCode().equals(0)){
                    finish();
                    Intent intent = new Intent(SignIn.this, Homepage.class);
                    startActivity(intent);
                    SharedPreferences.Editor userEdit = getSharedPreferences("user",MODE_PRIVATE).edit();

                    List<UserBean> beans = LitePal.findAll(UserBean.class);
                    boolean isStore = false;
                    for (UserBean bean : beans) {
                        if(bean.getAccount().equals(mLogInBean.getData().getUsername())){
                            isStore = true;
                            break;
                        }
                    }
                    if(!isStore){
                        UserBean bean = new UserBean();
                        bean.setPassword(binding.passward.getText().toString());
                        bean.setAccount(mLogInBean.getData().getUsername());
                        bean.save();
                    }

                    userEdit.putString("password",binding.passward.getText().toString());
                    userEdit.putString("account",mLogInBean.getData().getUsername());
                    userEdit.putString("sign_in","yes");
                    userEdit.apply();

                }else {
                    ToastUtil.showToast("密码错误",SignIn.this, Toast.LENGTH_SHORT);
                    binding.logIn.setText("Log in");
                    binding.loginLoading.cancelAnimation();
                    binding.loginLoading.setVisibility(View.GONE);
                }
            }
            return false;
        }
    });

    boolean isHide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.sign_in);
        SharedPreferences sp = getSharedPreferences("user",MODE_PRIVATE);

        isHide = true;
        accountDealWith();


        initListener();


    }

    private void accountDealWith() {
        binding.inputHide.setOnClickListener(this);
    }

    private void initListener() {
        binding.setLifecycleOwner(this);
        binding.logIn.setOnClickListener(this);
        binding.logUp.setOnClickListener(this);
    }

    private void postSignInRequest() {
        binding.logIn.setText("Log in...");
        binding.loginLoading.setVisibility(View.VISIBLE);
        binding.loginLoading.setAnimation(R.raw.login_in_loading);
        binding.loginLoading.playAnimation();
        OkHttpUtil.sendSignInRequestWithOkhttp("https://www.wanandroid.com/user/login", new OkHttpCallbackListener() {
            @Override
            public void finish(String response) {
                mLogInBean = new Gson().fromJson(response,MyLogInBean.class);
                LogUtil.d(TAG,response);
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        },binding.account.getText().toString(),binding.passward.getText().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.log_in:{
                postSignInRequest();
            }break;
            case R.id.log_up:{
                Intent intent = new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }break;
            case R.id.input_hide:{

                if(isHide){
                    binding.inputHide.setImageDrawable(getResources().getDrawable(R.drawable.icon_input_show));
                    binding.passward.setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
                    isHide = false;
                }else {
                    isHide = true;
                    binding.inputHide.setImageDrawable(getResources().getDrawable(R.drawable.icon_input_hide));
                    binding.passward.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }break;
        }
    }
}