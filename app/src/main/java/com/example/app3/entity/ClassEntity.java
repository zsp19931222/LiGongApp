package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class ClassEntity  {
    /**
     * content : {"userlist":[{"sex_id":"女","name":"苏婷","department_id":"05","faceaddress":"","userid":"1310504301","usertype":"1"},{"sex_id":"女","name":"袁平","department_id":"05","faceaddress":"","userid":"1310504302","usertype":"1"},{"sex_id":"女","name":"向琪","department_id":"05","faceaddress":"","userid":"1310504303","usertype":"1"},{"sex_id":"男","name":"陈少磊","department_id":"05","faceaddress":"","userid":"1310504304","usertype":"1"},{"sex_id":"男","name":"张夏","department_id":"05","faceaddress":"","userid":"1310504305","usertype":"1"},{"sex_id":"女","name":"李玲","department_id":"05","faceaddress":"","userid":"1310504306","usertype":"1"},{"sex_id":"男","name":"范洪凡","department_id":"05","faceaddress":"","userid":"1310504307","usertype":"1"},{"sex_id":"男","name":"叶欣明","department_id":"05","faceaddress":"","userid":"1310504308","usertype":"1"},{"sex_id":"男","name":"向洋","department_id":"05","faceaddress":"","userid":"1310504309","usertype":"1"},{"sex_id":"女","name":"孙爱林","department_id":"05","faceaddress":"","userid":"1310504310","usertype":"1"},{"sex_id":"女","name":"张洁","department_id":"05","faceaddress":"","userid":"1310504311","usertype":"1"},{"sex_id":"男","name":"高仁平","department_id":"05","faceaddress":"","userid":"1310504312","usertype":"1"},{"sex_id":"男","name":"王子木","department_id":"05","faceaddress":"","userid":"1310504334","usertype":"1"},{"sex_id":"女","name":"吴青青","department_id":"05","faceaddress":"","userid":"1310504330","usertype":"1"},{"sex_id":"男","name":"罗鹏程","department_id":"05","faceaddress":"","userid":"1310504314","usertype":"1"},{"sex_id":"男","name":"杨胜洲","department_id":"05","faceaddress":"","userid":"1310504315","usertype":"1"},{"sex_id":"女","name":"朱庆荣","department_id":"05","faceaddress":"","userid":"1310504316","usertype":"1"},{"sex_id":"女","name":"曹旦","department_id":"05","faceaddress":"","userid":"1310504317","usertype":"1"},{"sex_id":"女","name":"秦安兰","department_id":"05","faceaddress":"","userid":"1310504318","usertype":"1"},{"sex_id":"男","name":"李常伟","department_id":"05","faceaddress":"","userid":"1310504319","usertype":"1"},{"sex_id":"女","name":"向俊","department_id":"05","faceaddress":"","userid":"1310504320","usertype":"1"},{"sex_id":"女","name":"张艺萱","department_id":"05","faceaddress":"","userid":"1310504321","usertype":"1"},{"sex_id":"女","name":"罗文静","department_id":"05","faceaddress":"","userid":"1310504322","usertype":"1"},{"sex_id":"男","name":"蔡诚","department_id":"05","faceaddress":"","userid":"1310504323","usertype":"1"},{"sex_id":"女","name":"李娅玲","department_id":"05","faceaddress":"","userid":"1310504324","usertype":"1"},{"sex_id":"女","name":"宋涵利","department_id":"05","faceaddress":"","userid":"1310504325","usertype":"1"},{"sex_id":"女","name":"杨欣","department_id":"05","faceaddress":"","userid":"1310504326","usertype":"1"},{"sex_id":"女","name":"张瑜珊","department_id":"05","faceaddress":"","userid":"1310504328","usertype":"1"},{"sex_id":"女","name":"袁先容","department_id":"05","faceaddress":"","userid":"1310504329","usertype":"1"},{"sex_id":"男","name":"杨德意","department_id":"05","faceaddress":"","userid":"1310504331","usertype":"1"},{"sex_id":"男","name":"胡孝辉","department_id":"05","faceaddress":"","userid":"1310504332","usertype":"1"},{"sex_id":"男","name":"衡澍","department_id":"05","faceaddress":"","userid":"1310504333","usertype":"1"},{"sex_id":"女","name":"袁金燕","department_id":"05","faceaddress":"","userid":"1310504335","usertype":"1"},{"sex_id":"男","name":"周癸洲","department_id":"05","faceaddress":"","userid":"1310504336","usertype":"1"},{"sex_id":"男","name":"薛佳豪","department_id":"05","faceaddress":"","userid":"1310504337","usertype":"1"},{"sex_id":"男","name":"黄劲为","department_id":"05","faceaddress":"","userid":"1310504338","usertype":"1"},{"sex_id":"男","name":"李晋宇","department_id":"05","faceaddress":"","userid":"1310504339","usertype":"1"},{"sex_id":"男","name":"张华宇","department_id":"05","faceaddress":"","userid":"1310504340","usertype":"1"},{"sex_id":"男","name":"杨成","department_id":"05","faceaddress":"","userid":"1310504341","usertype":"1"},{"sex_id":"男","name":"赵晗","department_id":"05","faceaddress":"","userid":"1310504342","usertype":"1"},{"sex_id":"女","name":"袁颖","department_id":"05","faceaddress":"","userid":"1310504343","usertype":"1"},{"sex_id":"女","name":"金茂竹","department_id":"05","faceaddress":"","userid":"1310504344","usertype":"1"},{"sex_id":"男","name":"王龙","department_id":"05","faceaddress":"","userid":"1310504345","usertype":"1"},{"sex_id":"女","name":"刘念","department_id":"05","faceaddress":"","userid":"1310504346","usertype":"1"},{"sex_id":"女","name":"刘荷","department_id":"05","faceaddress":"","userid":"1310504347","usertype":"1"},{"sex_id":"女","name":"张梦嘉","department_id":"05","faceaddress":"","userid":"1310504348","usertype":"1"},{"sex_id":"女","name":"冉晓鹰","department_id":"05","faceaddress":"","userid":"1310504349","usertype":"1"},{"sex_id":"女","name":"张煜","department_id":"05","faceaddress":"","userid":"1310504350","usertype":"1"},{"sex_id":"女","name":"黄月玲","department_id":"05","faceaddress":"","userid":"1310504351","usertype":"1"},{"sex_id":"男","name":"杨阳","department_id":"05","faceaddress":"","userid":"1310504313","usertype":"1"},{"sex_id":"男","name":"牟锐","department_id":"05","faceaddress":"","userid":"1310504352","usertype":"1"},{"sex_id":"男","name":"徐康凌","department_id":"05","faceaddress":"","userid":"1310504353","usertype":"1"},{"sex_id":"男","name":"姜淦","department_id":"05","faceaddress":"","userid":"1310504354","usertype":"1"},{"sex_id":"女","name":"熊平","department_id":"05","faceaddress":"","userid":"1310504355","usertype":"1"}],"detaillist":[{"name":"班级名称","value":"2013物联网3班"},{"name":"班级人数","value":"54"}]}
     * message : 成功
     * time :
     * code : 40001
     */

    private ContentBean content;
    private String message;
    private String time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ContentBean {
        private List<UserlistBean> userlist;
        private List<DetaillistBean> detaillist;

        public List<UserlistBean> getUserlist() {
            return userlist;
        }

        public void setUserlist(List<UserlistBean> userlist) {
            this.userlist = userlist;
        }

        public List<DetaillistBean> getDetaillist() {
            return detaillist;
        }

        public void setDetaillist(List<DetaillistBean> detaillist) {
            this.detaillist = detaillist;
        }

        public static class UserlistBean {
            /**
             * sex_id : 女
             * name : 苏婷
             * department_id : 05
             * faceaddress :
             * userid : 1310504301
             * usertype : 1
             */

            private String sex_id;
            private String name;
            private String department_id;
            private String faceaddress;
            private String userid;
            private String usertype;

            public String getSex_id() {
                return sex_id;
            }

            public void setSex_id(String sex_id) {
                this.sex_id = sex_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(String department_id) {
                this.department_id = department_id;
            }

            public String getFaceaddress() {
                return faceaddress;
            }

            public void setFaceaddress(String faceaddress) {
                this.faceaddress = faceaddress;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }
        }

        public static class DetaillistBean {
            /**
             * name : 班级名称
             * value : 2013物联网3班
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
