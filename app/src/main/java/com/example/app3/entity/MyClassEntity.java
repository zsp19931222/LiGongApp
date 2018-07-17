package com.example.app3.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class MyClassEntity {

    /**
     * content : {"jxb":{"type":"10010","list":[{"id":"(2016-2017-1)-10056-20100007-1","count":"111","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"线性代数【理工】"},{"id":"(2016-2017-1)-10216-20060063-2","count":"35","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"材料概论"},{"id":"(2016-2017-1)-10802-20090027-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"物理化学"},{"id":"(2016-2017-1)-12572-19790003-1","count":"134","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学物理学【Ⅲ（2）】"},{"id":"(2016-2017-1)-10731-19860026-1","count":"125","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"工程力学"},{"id":"(2016-2017-1)-10188-20060028-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学英语【III】"},{"id":"(2016-2017-1)-00070-19980005-1","count":"2342","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"毛泽东思想和中国特色社会主义理论体系概论【理论】"},{"id":"(2016-2017-1)-10484-19870024-5","count":"105","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"中国近现代史纲要"}]},"xzb":{"type":"10086","list":[{"id":"115090303","count":"1","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=xzb.png","kcmc":"115090303"}]}}
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
         * jxb : {"type":"10010","list":[{"id":"(2016-2017-1)-10056-20100007-1","count":"111","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"线性代数【理工】"},{"id":"(2016-2017-1)-10216-20060063-2","count":"35","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"材料概论"},{"id":"(2016-2017-1)-10802-20090027-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"物理化学"},{"id":"(2016-2017-1)-12572-19790003-1","count":"134","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学物理学【Ⅲ（2）】"},{"id":"(2016-2017-1)-10731-19860026-1","count":"125","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"工程力学"},{"id":"(2016-2017-1)-10188-20060028-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学英语【III】"},{"id":"(2016-2017-1)-00070-19980005-1","count":"2342","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"毛泽东思想和中国特色社会主义理论体系概论【理论】"},{"id":"(2016-2017-1)-10484-19870024-5","count":"105","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"中国近现代史纲要"}]}
         * xzb : {"type":"10086","list":[{"id":"115090303","count":"1","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=xzb.png","kcmc":"115090303"}]}
         */

        private JxbBean jxb;
        private XzbBean xzb;

        public JxbBean getJxb() {
            return jxb;
        }

        public void setJxb(JxbBean jxb) {
            this.jxb = jxb;
        }

        public XzbBean getXzb() {
            return xzb;
        }

        public void setXzb(XzbBean xzb) {
            this.xzb = xzb;
        }

        public static class JxbBean {
            /**
             * type : 10010
             * list : [{"id":"(2016-2017-1)-10056-20100007-1","count":"111","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"线性代数【理工】"},{"id":"(2016-2017-1)-10216-20060063-2","count":"35","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"材料概论"},{"id":"(2016-2017-1)-10802-20090027-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"物理化学"},{"id":"(2016-2017-1)-12572-19790003-1","count":"134","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学物理学【Ⅲ（2）】"},{"id":"(2016-2017-1)-10731-19860026-1","count":"125","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"工程力学"},{"id":"(2016-2017-1)-10188-20060028-1","count":"76","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"大学英语【III】"},{"id":"(2016-2017-1)-00070-19980005-1","count":"2342","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"毛泽东思想和中国特色社会主义理论体系概论【理论】"},{"id":"(2016-2017-1)-10484-19870024-5","count":"105","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png","kcmc":"中国近现代史纲要"}]
             */

            private String type;
            private List<ListBean> list;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : (2016-2017-1)-10056-20100007-1
                 * count : 111
                 * faceaddress : http://192.168.0.102:8081/GroupChat/file/getfile.action?name=jxb.png
                 * kcmc : 线性代数【理工】
                 */

                private String id;
                private String count;
                private String faceaddress;
                private String kcmc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getFaceaddress() {
                    return faceaddress;
                }

                public void setFaceaddress(String faceaddress) {
                    this.faceaddress = faceaddress;
                }

                public String getKcmc() {
                    return kcmc;
                }

                public void setKcmc(String kcmc) {
                    this.kcmc = kcmc;
                }
            }
        }

        public static class XzbBean {
            /**
             * type : 10086
             * list : [{"id":"115090303","count":"1","faceaddress":"http://192.168.0.102:8081/GroupChat/file/getfile.action?name=xzb.png","kcmc":"115090303"}]
             */

            private String type;
            private List<ListBeanX> list;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * id : 115090303
                 * count : 1
                 * faceaddress : http://192.168.0.102:8081/GroupChat/file/getfile.action?name=xzb.png
                 * kcmc : 115090303
                 */

                private String id;
                private String count;
                private String faceaddress;
                private String kcmc;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getFaceaddress() {
                    return faceaddress;
                }

                public void setFaceaddress(String faceaddress) {
                    this.faceaddress = faceaddress;
                }

                public String getKcmc() {
                    return kcmc;
                }

                public void setKcmc(String kcmc) {
                    this.kcmc = kcmc;
                }
            }
        }
    }
}
