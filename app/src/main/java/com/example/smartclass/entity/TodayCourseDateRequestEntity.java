package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class TodayCourseDateRequestEntity {

    /**
     * code : 40001
     * message : 成功
     * content : {"maxZC":20,"xqj":4,"dqzc":20}
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
         * maxZC : 20
         * xqj : 4
         * dqzc : 20
         */

        private int maxZC;
        private int xqj;
        private int dqzc;

        public int getMaxZC() {
            return maxZC;
        }

        public void setMaxZC(int maxZC) {
            this.maxZC = maxZC;
        }

        public int getXqj() {
            return xqj;
        }

        public void setXqj(int xqj) {
            this.xqj = xqj;
        }

        public int getDqzc() {
            return dqzc;
        }

        public void setDqzc(int dqzc) {
            this.dqzc = dqzc;
        }
    }
}
