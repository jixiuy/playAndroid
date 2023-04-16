package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import com.example.readapp.R;
import android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.HomePageFragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 弹出一个Activity（Dialog）
 * 用来让用户选择是从相册中选头像还是拍照选头像
 */
public class ChooseHead extends AppCompatActivity implements View.OnClickListener{

    TextView cancel;
    TextView takePicture;
    TextView chooseFromAlbum;
    ImageView picture;
    LinearLayout mLinearLayout;
    ConstraintLayout headView;
    ImageView headProtrait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_head);
        cancel = findViewById(R.id.head_cancel);
        takePicture = findViewById(R.id.head_take_photo);
        chooseFromAlbum = findViewById(R.id.head_choose_from_album);
        cancel.setOnClickListener(this);
        takePicture.setOnClickListener(this);
        chooseFromAlbum.setOnClickListener(this);
        headProtrait = HomePageFragment.getmHeadPortrait();
        picture = HomePageFragment.getPicture();
        mLinearLayout = HomePageFragment.getLinearLayout();
        headView = HomePageFragment.getHeadView();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initWindow();
    }

    private void initWindow() {
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity =  Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 500;

        //重新设置窗体的位置和大小
        getWindowManager().updateViewLayout(view,lp);
        //设置圆角
        getWindow().setBackgroundDrawableResource(R.drawable.round_orner);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_cancel:{
                finish();
            }break;
            case R.id.head_take_photo:{
                openCamera(this);

            }break;
            case R.id.head_choose_from_album:{
                openAlbum();

            }break;
        }
    }

    //点击头像打开相册和照相机
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            }
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,3);
    }

    public void openCamera(Activity activity) {
        //获取系统版本
        int currentAPIVersion = Build.VERSION.SDK_INT;
        //激活相机意图
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断储存卡是否可以用
        if (hasSdCard()) {
            //设置时间的格式
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            //当前时间按格式展示
            String filename = timeStampFormat.format(new Date());

            File tempFile = new File(Environment.getExternalStorageDirectory(),filename+".jpg");
            //使用共享文件
            //利用内容提供器
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA,tempFile.getAbsolutePath());
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(ChooseHead.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            activity.startActivityForResult(intent,1);
        }


    }


    Uri imageUri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Intent intent1 = new Intent("com.android.camera.action.CROP");
            intent1.setDataAndType(imageUri,"image/*");
            intent1.putExtra("scale",true);
            intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent1,2);

        }
        if (requestCode==2){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                String imagePath = imageUri.toString();
                SharedPreferences shp  = getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor pref = getSharedPreferences("head",MODE_PRIVATE).edit();
                pref.putString("imagePath",imagePath);
                pref.putString("id1","1");
                UserBean userBean = new UserBean();
                userBean.setId1("1");
                userBean.setImagePath(imagePath);
                userBean.updateAll("account = ?",shp.getString("account",null));
                Log.d(TAG, "onActivityResult: 1");
                pref.apply();
                Glide.with(mLinearLayout).load(bitmap).circleCrop().into(picture);
                Glide.with(headView).load(bitmap).circleCrop().into(headProtrait);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        if(requestCode==3){
            Log.d(TAG, "onActivityResult: "+data);
            handleImageOnKiteKat(data);
        }
    }
    String imagePath = null;
    private void handleImageOnKiteKat(Intent data) {
        //对包装的Uri进行解析
        Uri uri = data.getData();

        //判断是否是Document类型

        if (DocumentsContract.isDocumentUri(this, uri)) {
            //用Document Id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            //判断authority 是否是media 类型
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //继续解析
                String id = docId.split(":")[1];
                //凭借成数据库的行
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.d(TAG, "displayPath: "+imagePath);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme()))
            {
                imagePath = getImagePath(uri,null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayPath();


        }
    }

    private static final String TAG = "MyNameIs";
    private void displayPath() {
        if (imagePath != null) {

            //手机存储卡中的图片
            SharedPreferences.Editor pref = getSharedPreferences("head",MODE_PRIVATE).edit();
            pref.putString("imagePath",imagePath);
            pref.putString("id1","2");
            Log.d(TAG, "displayPath: 2");
            UserBean userBean = new UserBean();
            userBean.setId1("2");
            userBean.setImagePath(imagePath);
            SharedPreferences shp  = getSharedPreferences("user",MODE_PRIVATE);

            userBean.updateAll("account = ?",shp.getString("account",null));
            pref.apply();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Glide.with(mLinearLayout).load(bitmap).circleCrop().into(picture);
            Glide.with(headView).load(bitmap).circleCrop().into(headProtrait);
            finish();
        }
        else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath (Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static boolean hasSdCard () {
        //获取sd卡状态，和可以用的状态比较
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}