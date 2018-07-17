package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class GetSchoolListEntity {

    /**
     * code : 40001
     * message : 成功
     * content : {"version":"1","lists":[{"xxmc":"重庆第二师范大学","xxbh":"000001","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆工商职业学院","xxbh":"000002","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆商务职业学院","xxbh":"000003","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆理工大学","xxbh":"000001","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川农业大学","xxbh":"000004","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"南宁职业学院","xxbh":"000005","szd":"南宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川广安职业学院","xxbh":"000006","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆房地产职业学院","xxbh":"000007","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆电子工程职业学院","xxbh":"000008","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆医药高专职业学院","xxbh":"000009","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"南宁职业学院","xxbh":"000010","szd":"南宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"北京大学","xxbh":"000010","szd":"北京","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆政法大学","xxbh":"000011","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"辽宁大学","xxbh":"000012","szd":"辽宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川外语学院","xxbh":"000013","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"}]}
     */

    private String code;
    private String message;
    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * version : 1
         * lists : [{"xxmc":"重庆第二师范大学","xxbh":"000001","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆工商职业学院","xxbh":"000002","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆商务职业学院","xxbh":"000003","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆理工大学","xxbh":"000001","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川农业大学","xxbh":"000004","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"南宁职业学院","xxbh":"000005","szd":"南宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川广安职业学院","xxbh":"000006","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆房地产职业学院","xxbh":"000007","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆电子工程职业学院","xxbh":"000008","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆医药高专职业学院","xxbh":"000009","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"南宁职业学院","xxbh":"000010","szd":"南宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"北京大学","xxbh":"000010","szd":"北京","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"重庆政法大学","xxbh":"000011","szd":"重庆","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"辽宁大学","xxbh":"000012","szd":"辽宁","xxtb":"http://www.cque.edu.cn/image/logonew1.png"},{"xxmc":"四川外语学院","xxbh":"000013","szd":"四川","xxtb":"http://www.cque.edu.cn/image/logonew1.png"}]
         */

        private int version;
        private List<ListsBean> lists;

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * xxmc : 重庆第二师范大学
             * xxbh : 000001
             * szd : 重庆
             * xxtb : http://www.cque.edu.cn/image/logonew1.png
             */

            private String xxmc;
            private String xxbh;
            private String szd;
            private String xxtb;

            public String getXxmc() {
                return xxmc;
            }

            public void setXxmc(String xxmc) {
                this.xxmc = xxmc;
            }

            public String getXxbh() {
                return xxbh;
            }

            public void setXxbh(String xxbh) {
                this.xxbh = xxbh;
            }

            public String getSzd() {
                return szd;
            }

            public void setSzd(String szd) {
                this.szd = szd;
            }

            public String getXxtb() {
                return xxtb;
            }

            public void setXxtb(String xxtb) {
                this.xxtb = xxtb;
            }
        }
    }
}
