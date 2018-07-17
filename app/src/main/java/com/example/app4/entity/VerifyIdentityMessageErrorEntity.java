package com.example.app4.entity;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class VerifyIdentityMessageErrorEntity {

    /**
     * code : 30006
     * message : 身份验证失败
     * content : {"sfzherror":"1","telerror":"1","xherror":"0"}
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
         * sfzherror : 1
         * telerror : 1
         * xherror : 0
         */

        private String sfzherror;
        private String telerror;
        private String xherror;

        public String getSfzherror() {
            return sfzherror;
        }

        public void setSfzherror(String sfzherror) {
            this.sfzherror = sfzherror;
        }

        public String getTelerror() {
            return telerror;
        }

        public void setTelerror(String telerror) {
            this.telerror = telerror;
        }

        public String getXherror() {
            return xherror;
        }

        public void setXherror(String xherror) {
            this.xherror = xherror;
        }
    }
}
