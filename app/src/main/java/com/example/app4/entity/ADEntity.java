package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class ADEntity {

    /**
     * content : [{"img":"http://192.168.0.108:8090/fs/filedown?id=7fgt35mc719qrcjee3ce87laap&fileName=%E6%B6%88%E8%B4%B9.png"}]
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
         * img : http://192.168.0.108:8090/fs/filedown?id=7fgt35mc719qrcjee3ce87laap&fileName=%E6%B6%88%E8%B4%B9.png
         */

        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
