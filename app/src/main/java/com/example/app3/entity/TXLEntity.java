package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class TXLEntity {

    /**
     * content : [{"HYBH":"42206045","HYZT":"1","FACEADDRESS":null,"HYBZ":"何绍森","YHBH":"42206044"},{"HYBH":"42206045","HYZT":"1","FACEADDRESS":null,"HYBZ":"何绍森","YHBH":"42206044"}]
     * message : 成功
     * code : 40001
     */

    private String message;
    private String code;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * HYBH : 42206045
         * HYZT : 1
         * FACEADDRESS : null
         * HYBZ : 何绍森
         * YHBH : 42206044
         */

        private String HYBH;
        private String HYZT;
        private Object FACEADDRESS;
        private String HYBZ;
        private String YHBH;

        public String getHYBH() {
            return HYBH;
        }

        public void setHYBH(String HYBH) {
            this.HYBH = HYBH;
        }

        public String getHYZT() {
            return HYZT;
        }

        public void setHYZT(String HYZT) {
            this.HYZT = HYZT;
        }

        public Object getFACEADDRESS() {
            return FACEADDRESS;
        }

        public void setFACEADDRESS(Object FACEADDRESS) {
            this.FACEADDRESS = FACEADDRESS;
        }

        public String getHYBZ() {
            return HYBZ;
        }

        public void setHYBZ(String HYBZ) {
            this.HYBZ = HYBZ;
        }

        public String getYHBH() {
            return YHBH;
        }

        public void setYHBH(String YHBH) {
            this.YHBH = YHBH;
        }
    }
}
