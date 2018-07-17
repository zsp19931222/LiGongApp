package com.example.smartclass.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class StudentListEntity {

    /**
     * code : 40001
     * message : 成功
     * content : [{"state":"5","name":"秦美英","xh":"1500658"},{"state":"5","name":"董梅","xh":"1500679"},{"state":"5","name":"陈春燕","xh":"1500654"},{"state":"5","name":"丁游清","xh":"1500698"},{"state":"5","name":"刘奕","xh":"1500697"},{"state":"5","name":"蒋金杉","xh":"1500675"},{"state":"5","name":"司祚伟","xh":"1500678"},{"state":"5","name":"贾忠庆","xh":"1500655"},{"state":"5","name":"胥思源","xh":"1500677"},{"state":"5","name":"罗利聪","xh":"1500694"},{"state":"5","name":"兰庆利","xh":"1500671"},{"state":"5","name":"唐峻森","xh":"1500673"},{"state":"5","name":"蒋芳","xh":"1500690"},{"state":"5","name":"杨敏","xh":"1500692"},{"state":"5","name":"吴玉梅","xh":"1500670"},{"state":"5","name":"张鼎","xh":"1500691"},{"state":"5","name":"杨叶","xh":"1500705"},{"state":"5","name":"庹佳棋","xh":"1500701"},{"state":"5","name":"杨轶淇","xh":"1500687"},{"state":"5","name":"罗燕","xh":"1500689"},{"state":"5","name":"刘莎莎","xh":"1500661"},{"state":"5","name":"卿春","xh":"1500663"},{"state":"5","name":"张总领","xh":"1500685"},{"state":"5","name":"杨俊虎","xh":"1500662"},{"state":"5","name":"何水苗","xh":"1500681"},{"state":"5","name":"蒋玉娇","xh":"1500680"}]
     */

    private String code;
    private String message;
    private List<ContentBean> content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * state : 5
         * name : 秦美英
         * xh : 1500658
         */

        private String state;
        private String name;
        private String xh;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getXh() {
            return xh;
        }

        public void setXh(String xh) {
            this.xh = xh;
        }
    }
}
