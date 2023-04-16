package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyDrawerLayout;

import java.util.List;

/**
 * 登录的时候如果失败，玩安卓会给我们返回的数据Bean
 */
public class SignUpErrorBean {


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
         * admin
         */
        private Boolean admin;
        /**
         * chapterTops
         */
        private List<?> chapterTops;
        /**
         * coinCount
         */
        private Integer coinCount;
        /**
         * collectIds
         */
        private List<?> collectIds;
        /**
         * email
         */
        private String email;
        /**
         * icon
         */
        private String icon;
        /**
         * id
         */
        private Integer id;
        /**
         * nickname
         */
        private String nickname;
        /**
         * password
         */
        private String password;
        /**
         * publicName
         */
        private String publicName;
        /**
         * token
         */
        private String token;
        /**
         * type
         */
        private Integer type;
        /**
         * username
         */
        private String username;

        public Boolean getAdmin() {
            return admin;
        }

        public void setAdmin(Boolean admin) {
            this.admin = admin;
        }

        public List<?> getChapterTops() {
            return chapterTops;
        }

        public void setChapterTops(List<?> chapterTops) {
            this.chapterTops = chapterTops;
        }

        public Integer getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(Integer coinCount) {
            this.coinCount = coinCount;
        }

        public List<?> getCollectIds() {
            return collectIds;
        }

        public void setCollectIds(List<?> collectIds) {
            this.collectIds = collectIds;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPublicName() {
            return publicName;
        }

        public void setPublicName(String publicName) {
            this.publicName = publicName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
