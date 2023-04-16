package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 这个是历史搜索功能，搜索的内容加到了数据库
 */
public class ItemBean extends LitePalSupport {
    String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
