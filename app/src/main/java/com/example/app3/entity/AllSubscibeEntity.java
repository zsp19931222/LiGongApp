package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class AllSubscibeEntity {

    /**
     * content : [{"ts_icon":"http://app.cqut.edu.cn/content/image/xwpt.png","ts_name":"教学通知","ts_describe":"","state":"1","ts_id":"30000007"},{"ts_icon":"http://app.cqut.edu.cn/content/image/push_zsjy.png","ts_name":"个人事务","ts_describe":"","state":"1","ts_id":"30000010"}]
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
         * ts_icon : http://app.cqut.edu.cn/content/image/xwpt.png
         * ts_name : 教学通知
         * ts_describe :
         * state : 1
         * ts_id : 30000007
         */

        private String ts_icon;
        private String ts_name;
        private String ts_describe;
        private String state;
        private String ts_id;

        public String getTs_icon() {
            return ts_icon;
        }

        public void setTs_icon(String ts_icon) {
            this.ts_icon = ts_icon;
        }

        public String getTs_name() {
            return ts_name;
        }

        public void setTs_name(String ts_name) {
            this.ts_name = ts_name;
        }

        public String getTs_describe() {
            return ts_describe;
        }

        public void setTs_describe(String ts_describe) {
            this.ts_describe = ts_describe;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTs_id() {
            return ts_id;
        }

        public void setTs_id(String ts_id) {
            this.ts_id = ts_id;
        }
    }
}
