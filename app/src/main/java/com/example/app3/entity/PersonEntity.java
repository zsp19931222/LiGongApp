package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class PersonEntity {

    /**
     * content : [{"PAGE_ADDRESS":"http://192.168.0.102:8081/UIA/function/getUserSubscibePushFunction.action?userid=20141029&ticket=740C065E7C0733D47CB8129214BECF23&function_GroupId=GRZX_WDDY","FUNCTION_ID":"GRZX_WDDY","NATIVE_ACCESS":"0","FUNCTION_NAME":"我的订阅","PATH":"/mydy","FUNCTION_FACE":"http://192.168.0.102:8081/UIA/img/grzx/GRZX_WDDY.png","FUNCTION_GROUP_ID":"GRZX_WDDY","FUNCTION_GROUP_NAME":"我的订阅","WEB_ACCESS":"1","RN":"1"},{"PAGE_ADDRESS":"http://192.168.0.102:8081/UIA/rout/CreateQrCode.action?userid=20141029&ticket=740C065E7C0733D47CB8129214BECF23&function_GroupId=GRZX_WDEWM","FUNCTION_ID":"GRZX_WDEWM","NATIVE_ACCESS":"0","FUNCTION_NAME":"我的二维码","PATH":"/myewm","FUNCTION_FACE":"http://192.168.0.102:8081/UIA/img/grzx/GRZX_WDEWM.png","FUNCTION_GROUP_ID":"GRZX_WDEWM","FUNCTION_GROUP_NAME":"我的二维码","WEB_ACCESS":"1","RN":"1"},{"PAGE_ADDRESS":"http://192.168.0.102:8081/DC/grzx/suggestions.action?userid=20141029&ticket=740C065E7C0733D47CB8129214BECF23&function_GroupId=GRZX_FK","FUNCTION_ID":"GRZX_YJFK","NATIVE_ACCESS":"0","FUNCTION_NAME":"意见反馈","PATH":"/fk","FUNCTION_FACE":"http://192.168.0.102:8081/UIA/img/grzx/GRZX_FK.png","FUNCTION_GROUP_ID":"GRZX_FK","FUNCTION_GROUP_NAME":"反馈","WEB_ACCESS":"1","RN":"1"}]
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
         * PAGE_ADDRESS : http://192.168.0.102:8081/UIA/function/getUserSubscibePushFunction.action?userid=20141029&ticket=740C065E7C0733D47CB8129214BECF23&function_GroupId=GRZX_WDDY
         * FUNCTION_ID : GRZX_WDDY
         * NATIVE_ACCESS : 0
         * FUNCTION_NAME : 我的订阅
         * PATH : /mydy
         * FUNCTION_FACE : http://192.168.0.102:8081/UIA/img/grzx/GRZX_WDDY.png
         * FUNCTION_GROUP_ID : GRZX_WDDY
         * FUNCTION_GROUP_NAME : 我的订阅
         * WEB_ACCESS : 1
         * RN : 1
         */

        private String PAGE_ADDRESS;
        private String FUNCTION_ID;
        private String NATIVE_ACCESS;
        private String FUNCTION_NAME;
        private String PATH;
        private String FUNCTION_FACE;
        private String FUNCTION_GROUP_ID;
        private String FUNCTION_GROUP_NAME;
        private String WEB_ACCESS;
        private String RN;

        public String getPAGE_ADDRESS() {
            return PAGE_ADDRESS;
        }

        public void setPAGE_ADDRESS(String PAGE_ADDRESS) {
            this.PAGE_ADDRESS = PAGE_ADDRESS;
        }

        public String getFUNCTION_ID() {
            return FUNCTION_ID;
        }

        public void setFUNCTION_ID(String FUNCTION_ID) {
            this.FUNCTION_ID = FUNCTION_ID;
        }

        public String getNATIVE_ACCESS() {
            return NATIVE_ACCESS;
        }

        public void setNATIVE_ACCESS(String NATIVE_ACCESS) {
            this.NATIVE_ACCESS = NATIVE_ACCESS;
        }

        public String getFUNCTION_NAME() {
            return FUNCTION_NAME;
        }

        public void setFUNCTION_NAME(String FUNCTION_NAME) {
            this.FUNCTION_NAME = FUNCTION_NAME;
        }

        public String getPATH() {
            return PATH;
        }

        public void setPATH(String PATH) {
            this.PATH = PATH;
        }

        public String getFUNCTION_FACE() {
            return FUNCTION_FACE;
        }

        public void setFUNCTION_FACE(String FUNCTION_FACE) {
            this.FUNCTION_FACE = FUNCTION_FACE;
        }

        public String getFUNCTION_GROUP_ID() {
            return FUNCTION_GROUP_ID;
        }

        public void setFUNCTION_GROUP_ID(String FUNCTION_GROUP_ID) {
            this.FUNCTION_GROUP_ID = FUNCTION_GROUP_ID;
        }

        public String getFUNCTION_GROUP_NAME() {
            return FUNCTION_GROUP_NAME;
        }

        public void setFUNCTION_GROUP_NAME(String FUNCTION_GROUP_NAME) {
            this.FUNCTION_GROUP_NAME = FUNCTION_GROUP_NAME;
        }

        public String getWEB_ACCESS() {
            return WEB_ACCESS;
        }

        public void setWEB_ACCESS(String WEB_ACCESS) {
            this.WEB_ACCESS = WEB_ACCESS;
        }

        public String getRN() {
            return RN;
        }

        public void setRN(String RN) {
            this.RN = RN;
        }
    }
}
