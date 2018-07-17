package com.example.app4.entity;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class PushEntity {

    /**
     * content : {"n_id":"15247238102640a7ef07270f34839a7fa9b344bd10962","n_send_time":"2018-04-26 14:23:30","n_title":"123","function_id":"20160225","n_source":null,"n_url":"http://jxzl.cqvie.edu.cn:11001/PushServer/text/html?userid=2005012025&id=15247238102640a7ef07270f34839a7fa9b344bd10962&ticket=59e873fc793544d8b4babc14393cf8de","code":"102","n_ticket":"59e873fc793544d8b4babc14393cf8de","n_message":"az","n_push_type":"102","n_picpath":"http://192.168.0.108:8090/fs/filedown?id=6ki3bj99j0i5g97nko4d40mkd5&fileName=b.png","n_read_num":0,"n_start_user":null,"n_read":0}
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
         * n_id : 15247238102640a7ef07270f34839a7fa9b344bd10962
         * n_send_time : 2018-04-26 14:23:30
         * n_title : 123
         * function_id : 20160225
         * n_source : null
         * n_url : http://jxzl.cqvie.edu.cn:11001/PushServer/text/html?userid=2005012025&id=15247238102640a7ef07270f34839a7fa9b344bd10962&ticket=59e873fc793544d8b4babc14393cf8de
         * code : 102
         * n_ticket : 59e873fc793544d8b4babc14393cf8de
         * n_message : az
         * n_push_type : 102
         * n_picpath : http://192.168.0.108:8090/fs/filedown?id=6ki3bj99j0i5g97nko4d40mkd5&fileName=b.png
         * n_read_num : 0
         * n_start_user : null
         * n_read : 0
         */

        private String n_id;
        private String n_send_time;
        private String n_title;
        private String function_id;
        private Object n_source;
        private String n_url;
        private String code;
        private String n_ticket;
        private String n_message;
        private String n_push_type;
        private String n_picpath;
        private int n_read_num;
        private Object n_start_user;
        private int n_read;

        public String getN_id() {
            return n_id;
        }

        public void setN_id(String n_id) {
            this.n_id = n_id;
        }

        public String getN_send_time() {
            return n_send_time;
        }

        public void setN_send_time(String n_send_time) {
            this.n_send_time = n_send_time;
        }

        public String getN_title() {
            return n_title;
        }

        public void setN_title(String n_title) {
            this.n_title = n_title;
        }

        public String getFunction_id() {
            return function_id;
        }

        public void setFunction_id(String function_id) {
            this.function_id = function_id;
        }

        public Object getN_source() {
            return n_source;
        }

        public void setN_source(Object n_source) {
            this.n_source = n_source;
        }

        public String getN_url() {
            return n_url;
        }

        public void setN_url(String n_url) {
            this.n_url = n_url;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getN_ticket() {
            return n_ticket;
        }

        public void setN_ticket(String n_ticket) {
            this.n_ticket = n_ticket;
        }

        public String getN_message() {
            return n_message;
        }

        public void setN_message(String n_message) {
            this.n_message = n_message;
        }

        public String getN_push_type() {
            return n_push_type;
        }

        public void setN_push_type(String n_push_type) {
            this.n_push_type = n_push_type;
        }

        public String getN_picpath() {
            return n_picpath;
        }

        public void setN_picpath(String n_picpath) {
            this.n_picpath = n_picpath;
        }

        public int getN_read_num() {
            return n_read_num;
        }

        public void setN_read_num(int n_read_num) {
            this.n_read_num = n_read_num;
        }

        public Object getN_start_user() {
            return n_start_user;
        }

        public void setN_start_user(Object n_start_user) {
            this.n_start_user = n_start_user;
        }

        public int getN_read() {
            return n_read;
        }

        public void setN_read(int n_read) {
            this.n_read = n_read;
        }
    }
}
