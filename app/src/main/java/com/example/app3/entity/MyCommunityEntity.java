package com.example.app3.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class MyCommunityEntity {

    /**
     * message : [{"HYBH":"11509990135","HYZT":"1","FACEADDRESS":null,"YHBZ":"卢秀华","YHBH":"11509990629"},{"HYBH":"11509990136","HYZT":"1","FACEADDRESS":null,"YHBZ":"周宇","YHBH":"11509990629"},{"HYBH":"11509990206","HYZT":"1","FACEADDRESS":null,"YHBZ":"郑杰","YHBH":"11509990629"},{"HYBH":"11509990225","HYZT":"1","FACEADDRESS":null,"YHBZ":"谢先明","YHBH":"11509990629"},{"HYBH":"11509990306","HYZT":"1","FACEADDRESS":null,"YHBZ":"罗媛婧","YHBH":"11509990629"},{"HYBH":"11509990332","HYZT":"1","FACEADDRESS":null,"YHBZ":"王红梅","YHBH":"11509990629"},{"HYBH":"11509990333","HYZT":"1","FACEADDRESS":null,"YHBZ":"司荣","YHBH":"11509990629"},{"HYBH":"11509990336","HYZT":"1","FACEADDRESS":null,"YHBZ":"安珊珊","YHBH":"11509990629"},{"HYBH":"11509990401","HYZT":"1","FACEADDRESS":null,"YHBZ":"张鑫","YHBH":"11509990629"},{"HYBH":"11509990402","HYZT":"1","FACEADDRESS":null,"YHBZ":"杨卓","YHBH":"11509990629"},{"HYBH":"11509990406","HYZT":"1","FACEADDRESS":null,"YHBZ":"刘越","YHBH":"11509990629"},{"HYBH":"11509990419","HYZT":"1","FACEADDRESS":null,"YHBZ":"唐桥宇","YHBH":"11509990629"},{"HYBH":"11509990522","HYZT":"1","FACEADDRESS":null,"YHBZ":"卢永辉","YHBH":"11509990629"},{"HYBH":"11509990629","HYZT":"1","FACEADDRESS":"http://192.168.1.198:8081/UIA/file/img/20171018/smail/20171018155552304.jpg","YHBZ":"王伶俐","YHBH":"11509990629"},{"HYBH":"11509990720","HYZT":"1","FACEADDRESS":null,"YHBZ":"郭丽鑫","YHBH":"11509990629"},{"HYBH":"11509990728","HYZT":"1","FACEADDRESS":null,"YHBZ":"王爽","YHBH":"11509990629"},{"HYBH":"11509990902","HYZT":"1","FACEADDRESS":null,"YHBZ":"冯凯","YHBH":"11509990629"},{"HYBH":"11509990912","HYZT":"1","FACEADDRESS":null,"YHBZ":"吴雪娜","YHBH":"11509990629"},{"HYBH":"11509990932","HYZT":"1","FACEADDRESS":null,"YHBZ":"廖晗","YHBH":"11509990629"},{"HYBH":"11509990938","HYZT":"1","FACEADDRESS":null,"YHBZ":"郭路","YHBH":"11509990629"},{"HYBH":"11509991005","HYZT":"1","FACEADDRESS":null,"YHBZ":"李东泽","YHBH":"11509990629"},{"HYBH":"11509991007","HYZT":"1","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160920/smail/20160920093301893.png","YHBZ":"潘家军","YHBH":"11509990629"},{"HYBH":"11509991013","HYZT":"1","FACEADDRESS":null,"YHBZ":"杨枭雄","YHBH":"11509990629"},{"HYBH":"11509991022","HYZT":"1","FACEADDRESS":null,"YHBZ":"隆永祥","YHBH":"11509990629"},{"HYBH":"11509991024","HYZT":"1","FACEADDRESS":null,"YHBZ":"彭杰","YHBH":"11509990629"},{"HYBH":"11509991028","HYZT":"1","FACEADDRESS":null,"YHBZ":"董浩","YHBH":"11509990629"},{"HYBH":"11509991035","HYZT":"1","FACEADDRESS":null,"YHBZ":"欧林南","YHBH":"11509990629"},{"HYBH":"11509991113","HYZT":"1","FACEADDRESS":null,"YHBZ":"陈韬行","YHBH":"11509990629"},{"HYBH":"11509991134","HYZT":"1","FACEADDRESS":null,"YHBZ":"魏勇","YHBH":"11509990629"},{"HYBH":"11509991233","HYZT":"1","FACEADDRESS":null,"YHBZ":"谢逸伦","YHBH":"11509990629"},{"HYBH":"11509991320","HYZT":"1","FACEADDRESS":null,"YHBZ":"朱小松","YHBH":"11509990629"},{"HYBH":"11509991330","HYZT":"1","FACEADDRESS":null,"YHBZ":"黄利","YHBH":"11509990629"},{"HYBH":"11509991428","HYZT":"1","FACEADDRESS":null,"YHBZ":"田雪颖","YHBH":"11509990629"},{"HYBH":"11509991437","HYZT":"1","FACEADDRESS":null,"YHBZ":"白霖睿","YHBH":"11509990629"},{"HYBH":"11509991438","HYZT":"1","FACEADDRESS":null,"YHBZ":"喻星汶","YHBH":"11509990629"}]
     * boolean : true
     */

    @SerializedName("boolean")
    private boolean booleanX;
    private List<MessageBean> message;

    public boolean isBooleanX() {
        return booleanX;
    }

    public void setBooleanX(boolean booleanX) {
        this.booleanX = booleanX;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * HYBH : 11509990135
         * HYZT : 1
         * FACEADDRESS : null
         * YHBZ : 卢秀华
         * YHBH : 11509990629
         */

        private String HYBH;
        private String HYZT;
        private Object FACEADDRESS;
        private String YHBZ;
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

        public String getYHBZ() {
            return YHBZ;
        }

        public void setYHBZ(String YHBZ) {
            this.YHBZ = YHBZ;
        }

        public String getYHBH() {
            return YHBH;
        }

        public void setYHBH(String YHBH) {
            this.YHBH = YHBH;
        }
    }
}
