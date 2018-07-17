package com.example.app4.entity;

/**
 * Created by Administrator on 2018/6/27 0027.
 */

public class UserFaceUrlEntity {

    /**
     * content : {"id":"68e96rtcirf0j0vaeofpgatl59","fileName":"hand.jpg","url":"http://file.i.cqut.edu.cn/fs/filedown?id=68e96rtcirf0j0vaeofpgatl59&fileName=hand.jpg"}
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
        /**
         * id : 68e96rtcirf0j0vaeofpgatl59
         * fileName : hand.jpg
         * url : http://file.i.cqut.edu.cn/fs/filedown?id=68e96rtcirf0j0vaeofpgatl59&fileName=hand.jpg
         */

        private String id;
        private String fileName;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
