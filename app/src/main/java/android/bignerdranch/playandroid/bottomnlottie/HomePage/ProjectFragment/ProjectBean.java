package android.bignerdranch.playandroid.bottomnlottie.HomePage.ProjectFragment;

import java.util.List;

/**
 * 项目旁边一级标题的Bean类
 */
public class ProjectBean {

    /**
     * data
     */
    private List<DataBean> data;
    /**
     * errorCode
     */
    private Integer errorCode;
    /**
     * errorMsg
     */
    private String errorMsg;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * author
         */
        private String author;
        /**
         * children
         */
        private List<?> children;
        /**
         * courseId
         */
        private Integer courseId;
        /**
         * cover
         */
        private String cover;
        /**
         * desc
         */
        private String desc;
        /**
         * id
         */
        private Integer id;
        /**
         * lisense
         */
        private String lisense;
        /**
         * lisenseLink
         */
        private String lisenseLink;
        /**
         * name
         */
        private String name;
        /**
         * order
         */
        private Integer order;
        /**
         * parentChapterId
         */
        private Integer parentChapterId;
        /**
         * userControlSetTop
         */
        private Boolean userControlSetTop;
        /**
         * visible
         */
        private Integer visible;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLisense() {
            return lisense;
        }

        public void setLisense(String lisense) {
            this.lisense = lisense;
        }

        public String getLisenseLink() {
            return lisenseLink;
        }

        public void setLisenseLink(String lisenseLink) {
            this.lisenseLink = lisenseLink;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Integer getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(Integer parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public Boolean getUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(Boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public Integer getVisible() {
            return visible;
        }

        public void setVisible(Integer visible) {
            this.visible = visible;
        }
    }
}
