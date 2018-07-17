package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class ClassStateEntity {

    /**
     * content : {"kczt":"2"}
     * message : 成功
     * code : 40001
     */

    private ContentBean content;
    private String message;
    private String code;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ContentBean {
        /**
         * kczt : 2
         */

        private String kczt;

        public String getKczt() {
            return kczt;
        }

        public void setKczt(String kczt) {
            this.kczt = kczt;
        }
    }
}
