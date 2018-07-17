package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16 0016.
 */

public class SettingPasswordEntity {

    /**
     * content : {"password":["o4FHhdAb40lRP7Hlh0kDsMkFYi93/1AfuiUzSF3kdGZ7/7UI6fnIWiCkUB7JSE3Gdpt5jKiSoaQg\n4F3coRHQBtxPEgzCTNW4DA4vha4wbJXj0RHPbB2X5lI5cnLpNKdwNBHjSYmf+ptGYR3sOqpMcVkF\n7QGPYeXPvdfuOuUIXTA=\n"]}
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
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
