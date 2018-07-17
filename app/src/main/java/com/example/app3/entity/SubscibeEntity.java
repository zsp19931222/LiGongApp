package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class SubscibeEntity {

    /**
     * content : [{"ts_group":"","ts_group_name":"","ts_icon":"http://app.cqut.edu.cn/content/image/xysc.png","ts_name":"行政事务","ts_id":"20160225","bjzd":"function","ts_group_icon":""},{"ts_group":"","ts_group_name":"","ts_icon":"http://app.cqut.edu.cn/content/image/tscx.png","ts_name":"学生事务","ts_id":"30000009","bjzd":"function","ts_group_icon":""},{"ts_group":"","ts_group_name":"","ts_icon":"http://app.cqut.edu.cn/content/image/xwpt.png","ts_name":"就业信息","ts_id":"30000008","bjzd":"function","ts_group_icon":""},{"ts_group_id":"3DCF47BA72E843149D4DBBDE45D058C3","ts_group_name":"订阅消息","ts_group_icon":"http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png","bjzd":"group","list":[{"ts_group":"3DCF47BA72E843149D4DBBDE45D058C3","ts_group_name":"订阅消息","ts_icon":"http://app.cqut.edu.cn/content/image/push_zsjy.png","ts_name":"个人事务","ts_id":"30000010","bjzd":"function","ts_group_icon":"http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png"},{"ts_group":"3DCF47BA72E843149D4DBBDE45D058C3","ts_group_name":"订阅消息","ts_icon":"http://app.cqut.edu.cn/content/image/xwpt.png","ts_name":"教学通知","ts_id":"30000007","bjzd":"function","ts_group_icon":"http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png"}]}]
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
         * ts_group :
         * ts_group_name :
         * ts_icon : http://app.cqut.edu.cn/content/image/xysc.png
         * ts_name : 行政事务
         * ts_id : 20160225
         * bjzd : function
         * ts_group_icon :
         * ts_group_id : 3DCF47BA72E843149D4DBBDE45D058C3
         * list : [{"ts_group":"3DCF47BA72E843149D4DBBDE45D058C3","ts_group_name":"订阅消息","ts_icon":"http://app.cqut.edu.cn/content/image/push_zsjy.png","ts_name":"个人事务","ts_id":"30000010","bjzd":"function","ts_group_icon":"http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png"},{"ts_group":"3DCF47BA72E843149D4DBBDE45D058C3","ts_group_name":"订阅消息","ts_icon":"http://app.cqut.edu.cn/content/image/xwpt.png","ts_name":"教学通知","ts_id":"30000007","bjzd":"function","ts_group_icon":"http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png"}]
         */

        private String ts_group;
        private String ts_group_name;
        private String ts_icon;
        private String ts_name;
        private String ts_id;
        private String bjzd;
        private String ts_group_icon;
        private String ts_group_id;
        private List<ListBean> list;

        public String getTs_group() {
            return ts_group;
        }

        public void setTs_group(String ts_group) {
            this.ts_group = ts_group;
        }

        public String getTs_group_name() {
            return ts_group_name;
        }

        public void setTs_group_name(String ts_group_name) {
            this.ts_group_name = ts_group_name;
        }

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

        public String getTs_id() {
            return ts_id;
        }

        public void setTs_id(String ts_id) {
            this.ts_id = ts_id;
        }

        public String getBjzd() {
            return bjzd;
        }

        public void setBjzd(String bjzd) {
            this.bjzd = bjzd;
        }

        public String getTs_group_icon() {
            return ts_group_icon;
        }

        public void setTs_group_icon(String ts_group_icon) {
            this.ts_group_icon = ts_group_icon;
        }

        public String getTs_group_id() {
            return ts_group_id;
        }

        public void setTs_group_id(String ts_group_id) {
            this.ts_group_id = ts_group_id;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * ts_group : 3DCF47BA72E843149D4DBBDE45D058C3
             * ts_group_name : 订阅消息
             * ts_icon : http://app.cqut.edu.cn/content/image/push_zsjy.png
             * ts_name : 个人事务
             * ts_id : 30000010
             * bjzd : function
             * ts_group_icon : http://192.168.0.102:8081/UIA/img/push/SUBSCRIBE.png
             */

            private String ts_group;
            private String ts_group_name;
            private String ts_icon;
            private String ts_name;
            private String ts_id;
            private String bjzd;
            private String ts_group_icon;

            public String getTs_group() {
                return ts_group;
            }

            public void setTs_group(String ts_group) {
                this.ts_group = ts_group;
            }

            public String getTs_group_name() {
                return ts_group_name;
            }

            public void setTs_group_name(String ts_group_name) {
                this.ts_group_name = ts_group_name;
            }

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

            public String getTs_id() {
                return ts_id;
            }

            public void setTs_id(String ts_id) {
                this.ts_id = ts_id;
            }

            public String getBjzd() {
                return bjzd;
            }

            public void setBjzd(String bjzd) {
                this.bjzd = bjzd;
            }

            public String getTs_group_icon() {
                return ts_group_icon;
            }

            public void setTs_group_icon(String ts_group_icon) {
                this.ts_group_icon = ts_group_icon;
            }
        }
    }
}
