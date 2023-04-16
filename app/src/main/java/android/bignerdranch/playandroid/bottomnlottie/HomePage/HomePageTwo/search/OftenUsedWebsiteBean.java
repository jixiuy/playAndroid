package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomePageTwo.search;

import java.util.List;

/**
 *常用网址的Bean类
 */
public class OftenUsedWebsiteBean {

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
         * category
         */
        private String category;
        /**
         * link
         */
        private String link;
        /**
         * name
         */
        private String name;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
