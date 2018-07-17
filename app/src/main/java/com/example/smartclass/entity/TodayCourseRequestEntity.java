package com.example.smartclass.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11 0011.
 */

public class TodayCourseRequestEntity {


    /**
     * content : {"ks":15,"kclb":[{"JSXM":"夏文利","STATE":"1","KCBH":"(2017-2018-2)-02000019-021005-1","ZKS":60,"JSMC":"3318","SKSJ":"13:30-15:05","JSSJ":6,"KSSJ":"5","KCMC":"应用文写作","NUM":39,"YR":"9月26日"},{"JSXM":"李翱","STATE":"1","KCBH":"(2017-2018-2)-16601109-2017800310-1","ZKS":60,"JSMC":"2402","SKSJ":"8:30-10:05","JSSJ":2,"KSSJ":"1","KCMC":"电商运营技术","NUM":248,"YR":"9月26日"},{"JSXM":"李翱","STATE":"1","KCBH":"(2017-2018-2)-16601109-2017800310-1","ZKS":60,"JSMC":"数据库应用与开发实训室(1502-1)","SKSJ":"10:25-12:00","JSSJ":4,"KSSJ":"3","KCMC":"电商运营技术","NUM":248,"YR":"9月26日"}]}
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
         * ks : 15
         * kclb : [{"JSXM":"夏文利","STATE":"1","KCBH":"(2017-2018-2)-02000019-021005-1","ZKS":60,"JSMC":"3318","SKSJ":"13:30-15:05","JSSJ":6,"KSSJ":"5","KCMC":"应用文写作","NUM":39,"YR":"9月26日"},{"JSXM":"李翱","STATE":"1","KCBH":"(2017-2018-2)-16601109-2017800310-1","ZKS":60,"JSMC":"2402","SKSJ":"8:30-10:05","JSSJ":2,"KSSJ":"1","KCMC":"电商运营技术","NUM":248,"YR":"9月26日"},{"JSXM":"李翱","STATE":"1","KCBH":"(2017-2018-2)-16601109-2017800310-1","ZKS":60,"JSMC":"数据库应用与开发实训室(1502-1)","SKSJ":"10:25-12:00","JSSJ":4,"KSSJ":"3","KCMC":"电商运营技术","NUM":248,"YR":"9月26日"}]
         */

        private int ks;
        private List<KclbBean> kclb;

        public int getKs() {
            return ks;
        }

        public void setKs(int ks) {
            this.ks = ks;
        }

        public List<KclbBean> getKclb() {
            return kclb;
        }

        public void setKclb(List<KclbBean> kclb) {
            this.kclb = kclb;
        }

        public static class KclbBean implements Serializable{
            public KclbBean(String JSXM, String STATE, String KCBH, int ZKS, String JSMC, String SKSJ, int JSSJ, String KSSJ, String KCMC, int NUM, String YR,int YJX) {
                this.JSXM = JSXM;
                this.STATE = STATE;
                this.KCBH = KCBH;
                this.ZKS = ZKS;
                this.JSMC = JSMC;
                this.SKSJ = SKSJ;
                this.JSSJ = JSSJ;
                this.KSSJ = KSSJ;
                this.KCMC = KCMC;
                this.NUM = NUM;
                this.YR = YR;
                this.YJX = YJX;
            }

            /**
             * JSXM : 夏文利
             * STATE : 1
             * KCBH : (2017-2018-2)-02000019-021005-1
             * ZKS : 60
             * JSMC : 3318
             * SKSJ : 13:30-15:05
             * JSSJ : 6
             * KSSJ : 5
             * KCMC : 应用文写作
             * NUM : 39
             * YR : 9月26日
             */



            private String JSXM;
            private String STATE;
            private String KCBH;
            private int ZKS;
            private String JSMC;
            private String SKSJ;
            private int JSSJ;
            private String KSSJ;
            private String KCMC;
            private int NUM;
            private String YR;
            private int YJX;

            public int getYJX() {
                return YJX;
            }

            public void setYJX(int YJX) {
                this.YJX = YJX;
            }

            public String getJSXM() {
                return JSXM;
            }

            public void setJSXM(String JSXM) {
                this.JSXM = JSXM;
            }

            public String getSTATE() {
                return STATE;
            }

            public void setSTATE(String STATE) {
                this.STATE = STATE;
            }

            public String getKCBH() {
                return KCBH;
            }

            public void setKCBH(String KCBH) {
                this.KCBH = KCBH;
            }

            public int getZKS() {
                return ZKS;
            }

            public void setZKS(int ZKS) {
                this.ZKS = ZKS;
            }

            public String getJSMC() {
                return JSMC;
            }

            public void setJSMC(String JSMC) {
                this.JSMC = JSMC;
            }

            public String getSKSJ() {
                return SKSJ;
            }

            public void setSKSJ(String SKSJ) {
                this.SKSJ = SKSJ;
            }

            public int getJSSJ() {
                return JSSJ;
            }

            public void setJSSJ(int JSSJ) {
                this.JSSJ = JSSJ;
            }

            public String getKSSJ() {
                return KSSJ;
            }

            public void setKSSJ(String KSSJ) {
                this.KSSJ = KSSJ;
            }

            public String getKCMC() {
                return KCMC;
            }

            public void setKCMC(String KCMC) {
                this.KCMC = KCMC;
            }

            public int getNUM() {
                return NUM;
            }

            public void setNUM(int NUM) {
                this.NUM = NUM;
            }

            public String getYR() {
                return YR;
            }

            public void setYR(String YR) {
                this.YR = YR;
            }
        }
    }
}
