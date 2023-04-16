package android.bignerdranch.playandroid.bottomnlottie.HomePage.MineFragment

import com.example.readapp.R

import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout.UserBean
import android.bignerdranch.playandroid.bottomnlottie.HomePage.Homepage
import android.bignerdranch.playandroid.util.ToastUtil
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import org.litepal.LitePal
import java.io.FileNotFoundException

/**
 * 这个是首页活动的第三个碎片我的，就一个皮，这时候已经功能写的7788不知道写什么了
 * 把第一行代码第三版很快的学过一遍，还是没有新功能可以添加，只用jetpack的WorkManager加了一个定时提醒的功能。以后的项目在用新的组件吧
 */
class MineFragment : Fragment() {

    lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("hello", "onCreateView: 6")
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        imageView = view.findViewById(R.id.touxinag)

        Log.d(TAG, "onCreateView: ")
        //切换头像
        addHeadProtrait()
        view.setOnClickListener{
            ToastUtil.showToast("功能待开发",context,Toast.LENGTH_SHORT)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    private fun addHeadProtrait() {
        val beans = LitePal.findAll(UserBean::class.java)
        var imagePath: String? = null
        var id: String? = null
        val pref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        for (bean in beans) {
            if(bean.account.equals(pref?.getString("account",null))){
                imagePath = bean.imagePath
                id = bean.id1
                break
            }
        }
        if (imagePath == null) {
            Glide.with(this).load(R.drawable.ic_launcher).circleCrop().into(imageView);
        }else{
            var bitmap:Bitmap
            if(id.equals("1")){

                try {
                    bitmap = BitmapFactory.decodeStream(getActivity()?.getContentResolver()?.openInputStream(
                        Uri.parse(imagePath)));
                    Glide.with(this).load(bitmap).circleCrop().into(imageView)
                } catch (e:FileNotFoundException) {
                    e.printStackTrace()
                }
            }else if(id.equals("2")){
                bitmap = BitmapFactory.decodeFile(imagePath);
                Glide.with(this).load(bitmap).circleCrop().into(imageView);
            }else {
                Glide.with(this).load(R.drawable.head_portrait)
                    .centerCrop()
                    .into(imageView);
            }
        }
    }

    private val TAG = "MineFragment"

}