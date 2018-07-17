package com.example.app4.entity;

/**
 * Created by Administrator on 2018/6/1 0001.
 */

public class OtherLoginEntity {


    /**
     * content : {"state":"0","password":"hfajkshfjkhaskjfhaskhfj","login_id":"111111"}
     * code : 40001
     * message : 成功
     */

    private ContentBean content;
    private String code;
    private String message;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ContentBean {
        /**
         * state : 0
         * password : hfajkshfjkhaskjfhaskhfj
         * login_id : 111111
         */

        private String state;
        private String password;
        private String login_id;

        public String getMob() {
            return mob;
        }

        public void setMob(String mob) {
            this.mob = mob;
        }

        private String mob;


        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLogin_id() {
            return login_id;
        }

        public void setLogin_id(String login_id) {
            this.login_id = login_id;
        }
    }
}
