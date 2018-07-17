package com.example.app3.entity;

/**
 * Created by Administrator on 2017/10/11.
 */

public class FrRrquestEntity {

    /**
     * content : {"sex_id":"1","sex_name":"男","name":"任广鹏","faceaddress":"http://192.168.0.198:8081/Image/default_face.png","userid":"20141027","usertype":"教师","department_name":"管理学院"}
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
        /**
         * sex_id : 1
         * sex_name : 男
         * name : 任广鹏
         * faceaddress : http://192.168.0.198:8081/Image/default_face.png
         * userid : 20141027
         * usertype : 教师
         * department_name : 管理学院
         */

        private String sex_id;
        private String sex_name;
        private String name;
        private String faceaddress;
        private String userid;
        private String usertype;
        private String department_name;

        public String getSex_id() {
            return sex_id;
        }

        public void setSex_id(String sex_id) {
            this.sex_id = sex_id;
        }

        public String getSex_name() {
            return sex_name;
        }

        public void setSex_name(String sex_name) {
            this.sex_name = sex_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }
    }
}
