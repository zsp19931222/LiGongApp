package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class GetCodeEntity {


    /**
     * code : 40001
     * message : 成功
     * content : {"czsj":30,"yzm":"8690"}
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
         * czsj : 30
         * yzm : 8690
         */

        private int czsj;
        private String yzm;

        public int getCzsj() {
            return czsj;
        }

        public void setCzsj(int czsj) {
            this.czsj = czsj;
        }

        public String getYzm() {
            return yzm;
        }

        public void setYzm(String yzm) {
            this.yzm = yzm;
        }
    }
}
