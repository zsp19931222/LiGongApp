package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class TodayCourseIDEntity {


    /**
     * code : 40001
     * message : 成功
     * content : {"zhktid":"762bdce965784d238b90a56ed6d67ff4"}
     */

    private String code;
    private String message;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * zhktid : 762bdce965784d238b90a56ed6d67ff4
         */

        private String zhktid;

        public String getZhktid() {
            return zhktid;
        }

        public void setZhktid(String zhktid) {
            this.zhktid = zhktid;
        }
    }
}
