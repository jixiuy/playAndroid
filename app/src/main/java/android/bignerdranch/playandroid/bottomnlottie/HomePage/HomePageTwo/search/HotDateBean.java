package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import java.util.List;

/**
 * 热搜词的Bean类
 */
public class HotDateBean {

    /**
     * data
     */
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name
         */
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
