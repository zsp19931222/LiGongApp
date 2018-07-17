package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/11 0011.
 */

public class PushOffLineEntity {

    /**
     * content : {"push":{"content":[{"n_id":"152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"74052eaef5ee4199ae5f9035aabd4f57","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b&ticket=74052eaef5ee4199ae5f9035aabd4f57"},{"n_id":"1526028460331f0c7ca8d2d3e46a6ad3a560d945061df","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"08560a9601744daaab5ab8522670a757","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460331f0c7ca8d2d3e46a6ad3a560d945061df&ticket=08560a9601744daaab5ab8522670a757"},{"n_id":"1526028460831169be10fb57d4650b7317be2a2d95703","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"a23f3c467d54482caa7a61f3b854a575","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460831169be10fb57d4650b7317be2a2d95703&ticket=a23f3c467d54482caa7a61f3b854a575"},{"n_id":"15260283993615689f9d810af44459b4e9573ac492357","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"6457dfbe84be4a2789dfc129ccc5a4b8","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=15260283993615689f9d810af44459b4e9573ac492357&ticket=6457dfbe84be4a2789dfc129ccc5a4b8"}],"message":"成功","code":"40001"}}
     * message : 成功
     * code : 40001
     */

    private ContentBeanX content;
    private String message;
    private String code;

    public ContentBeanX getContent() {
        return content;
    }

    public void setContent(ContentBeanX content) {
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

    public static class ContentBeanX {
        /**
         * push : {"content":[{"n_id":"152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"74052eaef5ee4199ae5f9035aabd4f57","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b&ticket=74052eaef5ee4199ae5f9035aabd4f57"},{"n_id":"1526028460331f0c7ca8d2d3e46a6ad3a560d945061df","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"08560a9601744daaab5ab8522670a757","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460331f0c7ca8d2d3e46a6ad3a560d945061df&ticket=08560a9601744daaab5ab8522670a757"},{"n_id":"1526028460831169be10fb57d4650b7317be2a2d95703","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"a23f3c467d54482caa7a61f3b854a575","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460831169be10fb57d4650b7317be2a2d95703&ticket=a23f3c467d54482caa7a61f3b854a575"},{"n_id":"15260283993615689f9d810af44459b4e9573ac492357","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"6457dfbe84be4a2789dfc129ccc5a4b8","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=15260283993615689f9d810af44459b4e9573ac492357&ticket=6457dfbe84be4a2789dfc129ccc5a4b8"}],"message":"成功","code":"40001"}
         */

        private PushBean push;

        public PushBean getPush() {
            return push;
        }

        public void setPush(PushBean push) {
            this.push = push;
        }

        public static class PushBean {
            /**
             * content : [{"n_id":"152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"74052eaef5ee4199ae5f9035aabd4f57","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b&ticket=74052eaef5ee4199ae5f9035aabd4f57"},{"n_id":"1526028460331f0c7ca8d2d3e46a6ad3a560d945061df","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"08560a9601744daaab5ab8522670a757","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460331f0c7ca8d2d3e46a6ad3a560d945061df&ticket=08560a9601744daaab5ab8522670a757"},{"n_id":"1526028460831169be10fb57d4650b7317be2a2d95703","n_title":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_message":"\u201c花果\u201d志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:47:40","function_id":"20171126","n_ticket":"a23f3c467d54482caa7a61f3b854a575","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=1526028460831169be10fb57d4650b7317be2a2d95703&ticket=a23f3c467d54482caa7a61f3b854a575"},{"n_id":"15260283993615689f9d810af44459b4e9573ac492357","n_title":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_message":"喜报！我校团委荣获\u201c重庆市五四红旗团委\u201d","n_jurl":null,"code":"102","n_send_time":"2018-05-11 16:46:39","function_id":"20171126","n_ticket":"6457dfbe84be4a2789dfc129ccc5a4b8","n_read":"0","n_read_num":0,"n_picpath":null,"n_source":null,"n_create_time":null,"tspd":null,"tsbm":null,"sms":null,"rich":null,"n_url":"http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=15260283993615689f9d810af44459b4e9573ac492357&ticket=6457dfbe84be4a2789dfc129ccc5a4b8"}]
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
                 * n_id : 152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b
                 * n_title : “花果”志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案
                 * n_message : “花果”志愿服务培训计划入选2017年重庆高校共青团思想政治工作优秀个案
                 * n_jurl : null
                 * code : 102
                 * n_send_time : 2018-05-11 16:46:39
                 * function_id : 20171126
                 * n_ticket : 74052eaef5ee4199ae5f9035aabd4f57
                 * n_read : 0
                 * n_read_num : 0
                 * n_picpath : null
                 * n_source : null
                 * n_create_time : null
                 * tspd : null
                 * tsbm : null
                 * sms : null
                 * rich : null
                 * n_url : http://222.179.134.204:8081/PushServer/text/html?userid=0400025&id=152602839981119ebe02f3a944e26b8e4b4e6be6c2e2b&ticket=74052eaef5ee4199ae5f9035aabd4f57
                 */

                private String n_id;
                private String n_title;
                private String n_message;
                private Object n_jurl;
                private String code;
                private String n_send_time;
                private String function_id;
                private String n_ticket;
                private String n_read;
                private int n_read_num;
                private Object n_picpath;
                private Object n_source;
                private Object n_create_time;
                private Object tspd;
                private Object tsbm;
                private Object sms;
                private Object rich;
                private String n_url;

                public String getN_id() {
                    return n_id;
                }

                public void setN_id(String n_id) {
                    this.n_id = n_id;
                }

                public String getN_title() {
                    return n_title;
                }

                public void setN_title(String n_title) {
                    this.n_title = n_title;
                }

                public String getN_message() {
                    return n_message;
                }

                public void setN_message(String n_message) {
                    this.n_message = n_message;
                }

                public Object getN_jurl() {
                    return n_jurl;
                }

                public void setN_jurl(Object n_jurl) {
                    this.n_jurl = n_jurl;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getN_send_time() {
                    return n_send_time;
                }

                public void setN_send_time(String n_send_time) {
                    this.n_send_time = n_send_time;
                }

                public String getFunction_id() {
                    return function_id;
                }

                public void setFunction_id(String function_id) {
                    this.function_id = function_id;
                }

                public String getN_ticket() {
                    return n_ticket;
                }

                public void setN_ticket(String n_ticket) {
                    this.n_ticket = n_ticket;
                }

                public String getN_read() {
                    return n_read;
                }

                public void setN_read(String n_read) {
                    this.n_read = n_read;
                }

                public int getN_read_num() {
                    return n_read_num;
                }

                public void setN_read_num(int n_read_num) {
                    this.n_read_num = n_read_num;
                }

                public Object getN_picpath() {
                    return n_picpath;
                }

                public void setN_picpath(Object n_picpath) {
                    this.n_picpath = n_picpath;
                }

                public Object getN_source() {
                    return n_source;
                }

                public void setN_source(Object n_source) {
                    this.n_source = n_source;
                }

                public Object getN_create_time() {
                    return n_create_time;
                }

                public void setN_create_time(Object n_create_time) {
                    this.n_create_time = n_create_time;
                }

                public Object getTspd() {
                    return tspd;
                }

                public void setTspd(Object tspd) {
                    this.tspd = tspd;
                }

                public Object getTsbm() {
                    return tsbm;
                }

                public void setTsbm(Object tsbm) {
                    this.tsbm = tsbm;
                }

                public Object getSms() {
                    return sms;
                }

                public void setSms(Object sms) {
                    this.sms = sms;
                }

                public Object getRich() {
                    return rich;
                }

                public void setRich(Object rich) {
                    this.rich = rich;
                }

                public String getN_url() {
                    return n_url;
                }

                public void setN_url(String n_url) {
                    this.n_url = n_url;
                }
            }
        }
    }
}
