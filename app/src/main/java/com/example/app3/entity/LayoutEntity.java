package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/21.
 */

public class LayoutEntity {

    private List<AllTagsListBean> allTagsList;

    public List<AllTagsListBean> getAllTagsList() {
        return allTagsList;
    }

    public void setAllTagsList(List<AllTagsListBean> allTagsList) {
        this.allTagsList = allTagsList;
    }

    public static class AllTagsListBean {
        /**
         * pic : http://www.fdsfds.com/pic/dsfds.png
         * pic_default : myself_bg_2x
         * txt : 党委部
         * num :
         * cls :
         * type : no_fill_view
         * explain : 说明
         * layout : item_archives
         * isSelect  : true
         */

        private String pic;
        private String pic_default;
        private String txt;
        private String num;
        private String cls;
        private String type;
        private String explain;
        private String layout;
        private boolean isSelect;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic_default() {
            return pic_default;
        }

        public void setPic_default(String pic_default) {
            this.pic_default = pic_default;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCls() {
            return cls;
        }

        public void setCls(String cls) {
            this.cls = cls;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public boolean isIsSelect() {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect) {
            this.isSelect = isSelect;
        }
    }
}
