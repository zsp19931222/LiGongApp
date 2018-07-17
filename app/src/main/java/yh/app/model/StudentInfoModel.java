package yh.app.model;

/**
 *学生信息数据模型
 */

public class StudentInfoModel {


    /**
     * content : {"message":{"nc":"体验一下","lxdh":"13220380556","sr":"1994-10-04","faceaddress":"http://localhost:80/WDDXSERVER/UserImages/11204050220.png","qq":"85303858585","zymc":"车辆工程","xy":"车辆工程学院"},"ticket":"65c3f5fa-222f-45bc-934a-b62a69b0d2fa","status":"true","code":"60008"}
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
         * message : {"nc":"体验一下","lxdh":"13220380556","sr":"1994-10-04","faceaddress":"http://localhost:80/WDDXSERVER/UserImages/11204050220.png","qq":"85303858585","zymc":"车辆工程","xy":"车辆工程学院"}
         * ticket : 65c3f5fa-222f-45bc-934a-b62a69b0d2fa
         * status : true
         * code : 60008
         */

        private MessageBean message;
        private String ticket;
        private String status;
        private String code;

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public static class MessageBean {
            /**
             * nc : 体验一下
             * lxdh : 13220380556
             * sr : 1994-10-04
             * faceaddress : http://localhost:80/WDDXSERVER/UserImages/11204050220.png
             * qq : 85303858585
             * zymc : 车辆工程
             * xy : 车辆工程学院
             */

            private String nc;
            private String lxdh;
            private String sr;
            private String faceaddress;
            private String qq;
            private String zymc;
            private String xy;

            public String getNc() {
                return nc;
            }

            public void setNc(String nc) {
                this.nc = nc;
            }

            public String getLxdh() {
                return lxdh;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getFaceaddress() {
                return faceaddress;
            }

            public void setFaceaddress(String faceaddress) {
                this.faceaddress = faceaddress;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getZymc() {
                return zymc;
            }

            public void setZymc(String zymc) {
                this.zymc = zymc;
            }

            public String getXy() {
                return xy;
            }

            public void setXy(String xy) {
                this.xy = xy;
            }
        }
    }
}
