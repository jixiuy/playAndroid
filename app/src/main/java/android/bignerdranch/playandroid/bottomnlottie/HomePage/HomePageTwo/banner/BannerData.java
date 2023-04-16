package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.banner;

import java.util.List;

/**
 * Banner的数据Bean
 */
public class BannerData {

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
         * imagePath
         */
        private String imagePath;
        /**
         * title
         */
        private String title;
        /**
         * url
         */
        private String url;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
