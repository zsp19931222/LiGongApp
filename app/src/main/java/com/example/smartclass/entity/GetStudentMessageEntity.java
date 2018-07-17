package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/2/3 0003.
 */

public class GetStudentMessageEntity  {


    /**
     * content : {"fdydh":"18100875661","tx":"","xh":"1731602336","fdy":"李明","lxdh":"18223506353","xm":"张显偲","bjmc":"网络173","xbmc":"男","xymc":"信息工程学院","zymc":"计算机网络技术"}
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
         * fdydh : 18100875661
         * tx :
         * xh : 1731602336
         * fdy : 李明
         * lxdh : 18223506353
         * xm : 张显偲
         * bjmc : 网络173
         * xbmc : 男
         * xymc : 信息工程学院
         * zymc : 计算机网络技术
         */

        private String fdydh;
        private String tx;
        private String xh;
        private String fdy;
        private String lxdh;
        private String xm;
        private String bjmc;
        private String xbmc;
        private String xymc;
        private String zymc;

        public String getFdydh() {
            return fdydh;
        }

        public void setFdydh(String fdydh) {
            this.fdydh = fdydh;
        }

        public String getTx() {
            return tx;
        }

        public void setTx(String tx) {
            this.tx = tx;
        }

        public String getXh() {
            return xh;
        }

        public void setXh(String xh) {
            this.xh = xh;
        }

        public String getFdy() {
            return fdy;
        }

        public void setFdy(String fdy) {
            this.fdy = fdy;
        }

        public String getLxdh() {
            return lxdh;
        }

        public void setLxdh(String lxdh) {
            this.lxdh = lxdh;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getBjmc() {
            return bjmc;
        }

        public void setBjmc(String bjmc) {
            this.bjmc = bjmc;
        }

        public String getXbmc() {
            return xbmc;
        }

        public void setXbmc(String xbmc) {
            this.xbmc = xbmc;
        }

        public String getXymc() {
            return xymc;
        }

        public void setXymc(String xymc) {
            this.xymc = xymc;
        }

        public String getZymc() {
            return zymc;
        }

        public void setZymc(String zymc) {
            this.zymc = zymc;
        }
    }
}
