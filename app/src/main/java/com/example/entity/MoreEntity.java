package com.example.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MoreEntity {


    private List<AllTagsListBean> allTagsList;

    public List<AllTagsListBean> getAllTagsList() {
        return allTagsList;
    }

    public void setAllTagsList(List<AllTagsListBean> allTagsList) {
        this.allTagsList = allTagsList;
    }

    public static class AllTagsListBean {
        /**
         * tagsName : asdd
         * tagsId : 3003442
         * tagInfoList : [{"tagName":"掌上离校单","latlon":"http://app.cqut.edu.cn/content/image/zslxd.png","isSelected":true,"tagId":"30000102","lat":"yh.app.web.Web","tagType":2,"url":"null","power":1,"android_packname":"null","android_address":"null"},{"tagName":"123","latlon":"http://file.i.cqut.edu.cn/fs/filedown?id=2g7jr3k8bqcb91it40lbo7qtmc&fileName=2g7jr3k8bqcb91it40lbo7qtmc.png","isSelected":true,"tagId":"152834755856440c16a4ccd414371b477b73ebd6ddfa3","lat":"yh.app.web.Web","tagType":2,"url":"111","power":2,"android_packname":"null","android_address":"null"},{"tagName":"办公电话","latlon":"http://app.cqut.edu.cn/content/image/bgdhcx.png","isSelected":true,"tagId":"30000002","lat":"yh.app.web.Web","tagType":2,"url":"null","power":1,"android_packname":"null","android_address":"null"}]
         */

        private String tagsName;
        private String tagsId;
        private List<TagInfoListBean> tagInfoList;

        public String getTagsName() {
            return tagsName;
        }

        public void setTagsName(String tagsName) {
            this.tagsName = tagsName;
        }

        public String getTagsId() {
            return tagsId;
        }

        public void setTagsId(String tagsId) {
            this.tagsId = tagsId;
        }

        public List<TagInfoListBean> getTagInfoList() {
            return tagInfoList;
        }

        public void setTagInfoList(List<TagInfoListBean> tagInfoList) {
            this.tagInfoList = tagInfoList;
        }

        public static class TagInfoListBean {
            /**
             * tagName : 掌上离校单
             * latlon : http://app.cqut.edu.cn/content/image/zslxd.png
             * isSelected : true
             * tagId : 30000102
             * lat : yh.app.web.Web
             * tagType : 2
             * url : null
             * power : 1
             * android_packname : null
             * android_address : null
             */

            private String tagName;
            private String latlon;
            private boolean isSelected;
            private String tagId;
            private String lat;
            private int tagType;
            private String url;
            private int power;
            private String android_packname;
            private String android_address;
            private List<String> function_key;


            public List<String> getFunction_key() {
                return function_key;
            }

            public void setFunction_key(List<String> function_key) {
                this.function_key = function_key;
            }


            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getLatlon() {
                return latlon;
            }

            public void setLatlon(String latlon) {
                this.latlon = latlon;
            }

            public boolean isIsSelected() {
                return isSelected;
            }

            public void setIsSelected(boolean isSelected) {
                this.isSelected = isSelected;
            }

            public String getTagId() {
                return tagId;
            }

            public void setTagId(String tagId) {
                this.tagId = tagId;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public int getTagType() {
                return tagType;
            }

            public void setTagType(int tagType) {
                this.tagType = tagType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getPower() {
                return power;
            }

            public void setPower(int power) {
                this.power = power;
            }

            public String getAndroid_packname() {
                return android_packname;
            }

            public void setAndroid_packname(String android_packname) {
                this.android_packname = android_packname;
            }

            public String getAndroid_address() {
                return android_address;
            }

            public void setAndroid_address(String android_address) {
                this.android_address = android_address;
            }
        }
    }
}
