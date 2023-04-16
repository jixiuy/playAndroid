package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来存Banner的一些信息
 */
public class DataBeen {
    private static final List<DataBeen> testData = new ArrayList<>();
    public final String imageRes;
    public final String textRes;
    public final String url;

    public String getImageRes() {
        return imageRes;
    }

    public String getUrl(){
        return url;

    }


    public DataBeen(String imageRes,String textRes,String url) {
        this.imageRes = imageRes;
        this.textRes = textRes;
        this.url = url;
    }

}
