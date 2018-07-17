package com.example.smartclass.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class GetStudentListEntity {

    /**
     * content : [{"xh":"11416010121","xm":"蔡朝焬"},{"xh":"11416010107","xm":"陈冬雪"},{"xh":"11416010208","xm":"陈浩"},{"xh":"11416010204","xm":"陈鹏"},{"xh":"11216010203","xm":"陈攴龙"},{"xh":"11416010112","xm":"陈锐"},{"xh":"11416010114","xm":"陈世明"},{"xh":"11416010214","xm":"成俊桦"},{"xh":"11416010133","xm":"程宇星"},{"xh":"11416010124","xm":"程彧"},{"xh":"11416010138","xm":"褚洪松"},{"xh":"11316010109","xm":"刁恒"},{"xh":"11416010226","xm":"冯辉"},{"xh":"11416010211","xm":"傅意超"},{"xh":"11416010207","xm":"胡晏箫"},{"xh":"11416010104","xm":"黄镜锋"},{"xh":"11416010127","xm":"黄韬"},{"xh":"11416010222","xm":"黄钟"},{"xh":"11416010206","xm":"江旭海"},{"xh":"11416010220","xm":"蒋佶宏"},{"xh":"11416010111","xm":"黎远明"},{"xh":"11416010116","xm":"李坤明"},{"xh":"11316010132","xm":"李磊"},{"xh":"11416010125","xm":"梁鑫"},{"xh":"11416010227","xm":"廖杰"},{"xh":"11316010112","xm":"廖能祥"},{"xh":"11416010115","xm":"刘磊"},{"xh":"11416010224","xm":"刘强"},{"xh":"11416010108","xm":"陆俊杰"},{"xh":"11416010216","xm":"罗缉"},{"xh":"11416010120","xm":"牟春波"},{"xh":"11416010118","xm":"倪大千"},{"xh":"11416010134","xm":"潘路"},{"xh":"11316010110","xm":"彭斌"},{"xh":"11416010101","xm":"钱程"},{"xh":"11416010117","xm":"冉阳莉"},{"xh":"11416010106","xm":"申小康"},{"xh":"11316010123","xm":"生子悦"},{"xh":"11416010119","xm":"苏华健"},{"xh":"11416010122","xm":"粟柏木"},{"xh":"11416010234","xm":"孙定强"},{"xh":"11416010235","xm":"孙华宗"},{"xh":"11416010131","xm":"覃春林"},{"xh":"11416010113","xm":"覃红"},{"xh":"11416010137","xm":"覃攀"},{"xh":"11416010110","xm":"谭小康"},{"xh":"11416010218","xm":"陶嘉亮"},{"xh":"11416010239","xm":"滕剑豪"},{"xh":"11416010103","xm":"汪衡"},{"xh":"11416010228","xm":"王波"},{"xh":"11416010230","xm":"王敏"},{"xh":"11416010135","xm":"王庆发"},{"xh":"11416010237","xm":"王威"},{"xh":"11416010126","xm":"王治丹"},{"xh":"11416010209","xm":"文涛"},{"xh":"11416010202","xm":"吴川乔"},{"xh":"11416010203","xm":"吴双宝"},{"xh":"11416010105","xm":"吴文峰"},{"xh":"11416010223","xm":"向勇"},{"xh":"11216010130","xm":"项纪斯"},{"xh":"11416010215","xm":"肖波"},{"xh":"11416010139","xm":"谢陆发"},{"xh":"11416010213","xm":"熊超越"},{"xh":"11416010238","xm":"许勇"},{"xh":"11416010212","xm":"颜珊"},{"xh":"11416010217","xm":"杨超越"},{"xh":"11216010134","xm":"杨天保"},{"xh":"11416010102","xm":"叶宝荣"},{"xh":"11416010123","xm":"雍瑞"},{"xh":"11416010129","xm":"余洋"},{"xh":"11416010236","xm":"张家美"},{"xh":"11416010229","xm":"张庞"},{"xh":"11416010219","xm":"张欣"},{"xh":"11416010201","xm":"赵发"},{"xh":"11416010140","xm":"郑晨"},{"xh":"11416010240","xm":"郑驰"},{"xh":"11416010128","xm":"钟远沂"},{"xh":"11416010231","xm":"周亮吉"}]
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
         * xh : 11416010121
         * xm : 蔡朝焬
         */

        private String xh;
        private String xm;
        private String xt;

        public String getXh() {
            return xh;
        }

        public void setXh(String xh) {
            this.xh = xh;
        }

        public String getXm() {
            return xm;
        }

        public void setXm(String xm) {
            this.xm = xm;
        }

        public String getXt() {
            return xt;
        }

        public void setXt(String xt) {
            this.xt = xt;
        }
    }
}
