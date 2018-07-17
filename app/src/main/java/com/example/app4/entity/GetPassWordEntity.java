package com.example.app4.entity;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class GetPassWordEntity {

    /**
     * code : 40001
     * message : 成功
     * content : {"password":"g1xVxhjJBCuyknB8xCZgWQbJ1a9SgdFdzAHUHqiJ0cu8OX19rTzES+OWBPD+ItUZt7S4xPy+ynhS tcFsgXxuPNX7EtgppUj4XwXMQPNA+QG1gvlvWjOUiP0P9/0KOu/ajEGkc2GnHOsMkw9jdQUVEcaH bTxdrkgo5JdUJgES2Dc="}
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
         * password : g1xVxhjJBCuyknB8xCZgWQbJ1a9SgdFdzAHUHqiJ0cu8OX19rTzES+OWBPD+ItUZt7S4xPy+ynhS tcFsgXxuPNX7EtgppUj4XwXMQPNA+QG1gvlvWjOUiP0P9/0KOu/ajEGkc2GnHOsMkw9jdQUVEcaH bTxdrkgo5JdUJgES2Dc=
         */

        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
