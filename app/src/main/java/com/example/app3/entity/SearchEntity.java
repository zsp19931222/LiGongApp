package com.example.app3.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class SearchEntity {

    /**
     * content : {"message":[{"XB":"男","BIRTHDAY":null,"USERTYPE":"教师","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":1,"XM":"DelunWang（王德伦）","USERID":"19720004"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"经济与贸易学院","RN":2,"XM":"陈王松","USERID":"11502030120"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"教师","FACEADDRESS":null,"BMMC":"保卫处、保卫部、人武武装部","RN":3,"XM":"符王虎","USERID":"20060091"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"计算机科学与工程学院","RN":4,"XM":"高王星","USERID":"11603080220"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"机械工程学院","RN":5,"XM":"胡王武","USERID":"11412990201"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"经济与贸易学院","RN":6,"XM":"黄王健","USERID":"11202990809"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":7,"XM":"脱王捷","USERID":"11304050303"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":8,"XM":"王偲羽","USERID":"11204090224"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160806/smail/20160806210958406.png","BMMC":"数学与统计学院","RN":9,"XM":"王偲俣","USERID":"11601070303"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":10,"XM":"王喆慧","USERID":"11406990211"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"管理学院","RN":11,"XM":"王垚霖","USERID":"91308010106"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"光电信息学院","RN":12,"XM":"王垚锟","USERID":"11416020404"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":13,"XM":"王姮","USERID":"52140648529"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160616/smail/20160616221513437.png","BMMC":"电子信息与自动化学院","RN":14,"XM":"王惇","USERID":"11307991021"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160705/smail/20160705112649209.png","BMMC":"经济与贸易学院","RN":15,"XM":"王旻","USERID":"11502990203"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"机械工程学院","RN":16,"XM":"王濛","USERID":"11412990428"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"材料科学与工程学院","RN":17,"XM":"王燚","USERID":"11209010126"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":18,"XM":"王玥","USERID":"11206040242"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":19,"XM":"王玥","USERID":"11409990214"}],"boolean":true}
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
         * message : [{"XB":"男","BIRTHDAY":null,"USERTYPE":"教师","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":1,"XM":"DelunWang（王德伦）","USERID":"19720004"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"经济与贸易学院","RN":2,"XM":"陈王松","USERID":"11502030120"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"教师","FACEADDRESS":null,"BMMC":"保卫处、保卫部、人武武装部","RN":3,"XM":"符王虎","USERID":"20060091"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"计算机科学与工程学院","RN":4,"XM":"高王星","USERID":"11603080220"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"机械工程学院","RN":5,"XM":"胡王武","USERID":"11412990201"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"经济与贸易学院","RN":6,"XM":"黄王健","USERID":"11202990809"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":7,"XM":"脱王捷","USERID":"11304050303"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"车辆工程学院","RN":8,"XM":"王偲羽","USERID":"11204090224"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160806/smail/20160806210958406.png","BMMC":"数学与统计学院","RN":9,"XM":"王偲俣","USERID":"11601070303"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":10,"XM":"王喆慧","USERID":"11406990211"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"管理学院","RN":11,"XM":"王垚霖","USERID":"91308010106"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"光电信息学院","RN":12,"XM":"王垚锟","USERID":"11416020404"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":13,"XM":"王姮","USERID":"52140648529"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160616/smail/20160616221513437.png","BMMC":"电子信息与自动化学院","RN":14,"XM":"王惇","USERID":"11307991021"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":"http://app.cqut.edu.cn/UIA/file/img/20160705/smail/20160705112649209.png","BMMC":"经济与贸易学院","RN":15,"XM":"王旻","USERID":"11502990203"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"机械工程学院","RN":16,"XM":"王濛","USERID":"11412990428"},{"XB":"男","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"材料科学与工程学院","RN":17,"XM":"王燚","USERID":"11209010126"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":18,"XM":"王玥","USERID":"11206040242"},{"XB":"女","BIRTHDAY":null,"USERTYPE":"学生","FACEADDRESS":null,"BMMC":"会计学院","RN":19,"XM":"王玥","USERID":"11409990214"}]
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
             * XB : 男
             * BIRTHDAY : null
             * USERTYPE : 教师
             * FACEADDRESS : null
             * BMMC : 车辆工程学院
             * RN : 1
             * XM : DelunWang（王德伦）
             * USERID : 19720004
             */

            private String XB;
            private Object BIRTHDAY;
            private String USERTYPE;
            private Object FACEADDRESS;
            private String BMMC;
            private int RN;
            private String XM;
            private String USERID;

            public String getXB() {
                return XB;
            }

            public void setXB(String XB) {
                this.XB = XB;
            }

            public Object getBIRTHDAY() {
                return BIRTHDAY;
            }

            public void setBIRTHDAY(Object BIRTHDAY) {
                this.BIRTHDAY = BIRTHDAY;
            }

            public String getUSERTYPE() {
                return USERTYPE;
            }

            public void setUSERTYPE(String USERTYPE) {
                this.USERTYPE = USERTYPE;
            }

            public Object getFACEADDRESS() {
                return FACEADDRESS;
            }

            public void setFACEADDRESS(Object FACEADDRESS) {
                this.FACEADDRESS = FACEADDRESS;
            }

            public String getBMMC() {
                return BMMC;
            }

            public void setBMMC(String BMMC) {
                this.BMMC = BMMC;
            }

            public int getRN() {
                return RN;
            }

            public void setRN(int RN) {
                this.RN = RN;
            }

            public String getXM() {
                return XM;
            }

            public void setXM(String XM) {
                this.XM = XM;
            }

            public String getUSERID() {
                return USERID;
            }

            public void setUSERID(String USERID) {
                this.USERID = USERID;
            }
        }
    }
}
