package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class UserMessageEntity {


    /**
     * code : 40001
     * message : 成功
     * content : {"userinfo":["erI4auCSgPXvVIk5ys+TXKa8jVhmpbYfJk9FnF+2ObFcvKTnZiEm2aoP8ugyoB0PMQvde4I7UwC7\n9oX/N5ylqafFunqaZqWY3KvgPo2QCyU4DOh4idLhwduAdlUIbMXag2WqKCHduLz3H4CFId0GPVQv\n0QujBjK0uP2vMNKDESQ=\n","NgREhbnOLJNksUKuTEFHguxuwoDeCDnX3mUYzzCRu2znxObDTHPuf4XyyIhZX5ZrZz4GJCH0pTdP\nxU0FPBsa+eUKcw4qTfTSDKcc9c6FS/GX//j2HwBpt/kyFhbO2f5eSF2xL38SPX4koMZ0JMwp+ef2\npg1sHrKKWPyBRT7vihI=\n","eYlvdjPzi5Q+MAlN3M4m0ZIO8fCEtmDv/J1ylhlNlit8Js415Oik60qqISHQhnOBRcFSy1UigkPM\nAqrxW6/XV++wXaOOB7hDTlrOXErN7hjquulvBrwC8FSM+4Y4tENOxZZGD9mbGKrfML6bD9AEjxb2\nKRv19n9uSM+BpW6zI6I=\n"],"usertype":1}
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
         * userinfo : ["erI4auCSgPXvVIk5ys+TXKa8jVhmpbYfJk9FnF+2ObFcvKTnZiEm2aoP8ugyoB0PMQvde4I7UwC7\n9oX/N5ylqafFunqaZqWY3KvgPo2QCyU4DOh4idLhwduAdlUIbMXag2WqKCHduLz3H4CFId0GPVQv\n0QujBjK0uP2vMNKDESQ=\n","NgREhbnOLJNksUKuTEFHguxuwoDeCDnX3mUYzzCRu2znxObDTHPuf4XyyIhZX5ZrZz4GJCH0pTdP\nxU0FPBsa+eUKcw4qTfTSDKcc9c6FS/GX//j2HwBpt/kyFhbO2f5eSF2xL38SPX4koMZ0JMwp+ef2\npg1sHrKKWPyBRT7vihI=\n","eYlvdjPzi5Q+MAlN3M4m0ZIO8fCEtmDv/J1ylhlNlit8Js415Oik60qqISHQhnOBRcFSy1UigkPM\nAqrxW6/XV++wXaOOB7hDTlrOXErN7hjquulvBrwC8FSM+4Y4tENOxZZGD9mbGKrfML6bD9AEjxb2\nKRv19n9uSM+BpW6zI6I=\n"]
         * usertype : 1
         */

        private int usertype;
        private List<String> userinfo;

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public List<String> getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(List<String> userinfo) {
            this.userinfo = userinfo;
        }
    }
}
