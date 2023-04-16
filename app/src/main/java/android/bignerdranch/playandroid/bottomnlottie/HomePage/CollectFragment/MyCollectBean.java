package android.bignerdranch.playandroid.bottomnlottie.HomePage.CollectFragment;

import java.util.List;

/**
 * 收藏文章的Bean类
 */
public class MyCollectBean {

    /**
     * data
     */
    private DataBean data;
    /**
     * errorCode
     */
    private Integer errorCode;
    /**
     * errorMsg
     */
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * curPage
         */
        private Integer curPage;
        /**
         * datas
         */
        private List<DatasBean> datas;
        /**
         * offset
         */
        private Integer offset;
        /**
         * over
         */
        private Boolean over;
        /**
         * pageCount
         */
        private Integer pageCount;
        /**
         * size
         */
        private Integer size;
        /**
         * total
         */
        private Integer total;

        public Integer getCurPage() {
            return curPage;
        }

        public void setCurPage(Integer curPage) {
            this.curPage = curPage;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Boolean getOver() {
            return over;
        }

        public void setOver(Boolean over) {
            this.over = over;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public static class DatasBean {
            /**
             * author
             */
            private String author;
            /**
             * chapterId
             */
            private Integer chapterId;
            /**
             * chapterName
             */
            private String chapterName;
            /**
             * courseId
             */
            private Integer courseId;
            /**
             * desc
             */
            private String desc;
            /**
             * envelopePic
             */
            private String envelopePic;
            /**
             * id
             */
            private Integer id;
            /**
             * link
             */
            private String link;
            /**
             * niceDate
             */
            private String niceDate;
            /**
             * origin
             */
            private String origin;
            /**
             * originId
             */
            private Integer originId;
            /**
             * publishTime
             */
            private Long publishTime;
            /**
             * title
             */
            private String title;
            /**
             * userId
             */
            private Integer userId;
            /**
             * visible
             */
            private Integer visible;
            /**
             * zan
             */
            private Integer zan;

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public Integer getChapterId() {
                return chapterId;
            }

            public void setChapterId(Integer chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public Integer getCourseId() {
                return courseId;
            }

            public void setCourseId(Integer courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public Integer getOriginId() {
                return originId;
            }

            public void setOriginId(Integer originId) {
                this.originId = originId;
            }

            public Long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(Long publishTime) {
                this.publishTime = publishTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getVisible() {
                return visible;
            }

            public void setVisible(Integer visible) {
                this.visible = visible;
            }

            public Integer getZan() {
                return zan;
            }

            public void setZan(Integer zan) {
                this.zan = zan;
            }
        }
    }
}
