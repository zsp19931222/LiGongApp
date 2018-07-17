package com.example.smartclass.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/3 0003.
 */

public class MyClassEntity {

    /**
     * content : [{"kcbh":"(2016-2017-1)-11040-19990027-1","bjmc":"17城市管理、17城市管理","kcmc":"数据库原理及应用"},{"kcbh":"(2016-2017-1)-11040-19990027-1A","bjmc":"17城市管理、17城市管理","kcmc":"数据库原理及应用"}]
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
         * kcbh : (2016-2017-1)-11040-19990027-1
         * bjmc : 17城市管理、17城市管理
         * kcmc : 数据库原理及应用
         */

        private String kcbh;
        private String bjmc;
        private String kcmc;

        public String getKcbh() {
            return kcbh;
        }

        public void setKcbh(String kcbh) {
            this.kcbh = kcbh;
        }

        public String getBjmc() {
            return bjmc;
        }

        public void setBjmc(String bjmc) {
            this.bjmc = bjmc;
        }

        public String getKcmc() {
            return kcmc;
        }

        public void setKcmc(String kcmc) {
            this.kcmc = kcmc;
        }
    }
}
