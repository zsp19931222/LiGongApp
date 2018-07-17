package yh.app.model;

import java.util.List;

public class ZiZhuMuBiaoModel {
	/**
     * content : {"message":{"ongoing":[{"t_id":"100027","status":"0","status_name":"进行中","t_name":"测试目标100027"},{"t_id":"100066","status":"0","status_name":"进行中","t_name":"测试目标100066"},{"t_id":"100054","status":"0","status_name":"进行中","t_name":"测试目标100054"},{"t_id":"100048","status":"0","status_name":"进行中","t_name":"测试目标100048"},{"t_id":"100030","status":"0","status_name":"进行中","t_name":"测试目标100030"},{"t_id":"100026","status":"0","status_name":"进行中","t_name":"测试目标100026"},{"t_id":"100024","status":"0","status_name":"进行中","t_name":"测试目标100024"},{"t_id":"100014","status":"0","status_name":"进行中","t_name":"测试目标100014"},{"t_id":"10006","status":"0","status_name":"进行中","t_name":"测试目标10006"},{"t_id":"100080","status":"0","status_name":"进行中","t_name":"测试目标100080"},{"t_id":"AEN","status":"0","status_name":"进行中","t_name":"测试名称zy_05"},{"t_id":"100012","status":"0","status_name":"进行中","t_name":"测试目标100012"},{"t_id":"100096","status":"0","status_name":"进行中","t_name":"测试目标100096"},{"t_id":"0f393ca7-fa90-4917-9101-15db31a226dc","status":"0","status_name":"进行中","t_name":"123456"},{"t_id":"1710f8a5-7e0d-44d7-a398-56e938883259","status":"0","status_name":"进行中","t_name":"ujku"},{"t_id":"76fbf9c9-ef13-431e-bb95-82693fd756da","status":"0","status_name":"进行中","t_name":"测试201704101053px"},{"t_id":"3aabe82a-2d6c-4b11-9925-222d4e826f9c","status":"0","status_name":"进行中","t_name":"快快快快快快加"},{"t_id":"1dd742d4-763d-4d4e-b138-d32ae7a087e2","status":"0","status_name":"进行中","t_name":"12312"},{"t_id":"d4343cba-fc15-479b-a5c3-42cfa57ac7c7","status":"0","status_name":"进行中","t_name":"bbbbbbbbbbbbbbbbbbb"},{"t_id":"f0e37f25-d76d-4bff-ae46-ad8aa1212359","status":"0","status_name":"进行中","t_name":"xxxxxxxxxxxxxxxxxxxxxxxx"},{"t_id":"7a2ed2a3-d97d-4daa-9d69-8994bc7fe622","status":"0","status_name":"进行中","t_name":"测试201704141411"},{"t_id":"1c7f1316-6940-43b0-af5f-4be2982aef8c","status":"0","status_name":"进行中","t_name":"测试201704141416"},{"t_id":"KVF","status":"0","status_name":"进行中","t_name":"测试名称zy_83"},{"t_id":"5NC","status":"0","status_name":"进行中","t_name":"测试名称zy_17"},{"t_id":"6FK","status":"0","status_name":"进行中","t_name":"测试名称zy_80"},{"t_id":"13635","status":"0","status_name":"进行中","t_name":"仪表工"}],"completed":[]},"ticket":"faf9a792-a6dd-49e0-abe9-e606632ee827","status":"true","code":"60008"}
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
         * message : {"ongoing":[{"t_id":"100027","status":"0","status_name":"进行中","t_name":"测试目标100027"},{"t_id":"100066","status":"0","status_name":"进行中","t_name":"测试目标100066"},{"t_id":"100054","status":"0","status_name":"进行中","t_name":"测试目标100054"},{"t_id":"100048","status":"0","status_name":"进行中","t_name":"测试目标100048"},{"t_id":"100030","status":"0","status_name":"进行中","t_name":"测试目标100030"},{"t_id":"100026","status":"0","status_name":"进行中","t_name":"测试目标100026"},{"t_id":"100024","status":"0","status_name":"进行中","t_name":"测试目标100024"},{"t_id":"100014","status":"0","status_name":"进行中","t_name":"测试目标100014"},{"t_id":"10006","status":"0","status_name":"进行中","t_name":"测试目标10006"},{"t_id":"100080","status":"0","status_name":"进行中","t_name":"测试目标100080"},{"t_id":"AEN","status":"0","status_name":"进行中","t_name":"测试名称zy_05"},{"t_id":"100012","status":"0","status_name":"进行中","t_name":"测试目标100012"},{"t_id":"100096","status":"0","status_name":"进行中","t_name":"测试目标100096"},{"t_id":"0f393ca7-fa90-4917-9101-15db31a226dc","status":"0","status_name":"进行中","t_name":"123456"},{"t_id":"1710f8a5-7e0d-44d7-a398-56e938883259","status":"0","status_name":"进行中","t_name":"ujku"},{"t_id":"76fbf9c9-ef13-431e-bb95-82693fd756da","status":"0","status_name":"进行中","t_name":"测试201704101053px"},{"t_id":"3aabe82a-2d6c-4b11-9925-222d4e826f9c","status":"0","status_name":"进行中","t_name":"快快快快快快加"},{"t_id":"1dd742d4-763d-4d4e-b138-d32ae7a087e2","status":"0","status_name":"进行中","t_name":"12312"},{"t_id":"d4343cba-fc15-479b-a5c3-42cfa57ac7c7","status":"0","status_name":"进行中","t_name":"bbbbbbbbbbbbbbbbbbb"},{"t_id":"f0e37f25-d76d-4bff-ae46-ad8aa1212359","status":"0","status_name":"进行中","t_name":"xxxxxxxxxxxxxxxxxxxxxxxx"},{"t_id":"7a2ed2a3-d97d-4daa-9d69-8994bc7fe622","status":"0","status_name":"进行中","t_name":"测试201704141411"},{"t_id":"1c7f1316-6940-43b0-af5f-4be2982aef8c","status":"0","status_name":"进行中","t_name":"测试201704141416"},{"t_id":"KVF","status":"0","status_name":"进行中","t_name":"测试名称zy_83"},{"t_id":"5NC","status":"0","status_name":"进行中","t_name":"测试名称zy_17"},{"t_id":"6FK","status":"0","status_name":"进行中","t_name":"测试名称zy_80"},{"t_id":"13635","status":"0","status_name":"进行中","t_name":"仪表工"}],"completed":[]}
         * ticket : faf9a792-a6dd-49e0-abe9-e606632ee827
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
            private List<OngoingBean> ongoing;
            private List<?> completed;

            public List<OngoingBean> getOngoing() {
                return ongoing;
            }

            public void setOngoing(List<OngoingBean> ongoing) {
                this.ongoing = ongoing;
            }

            public List<?> getCompleted() {
                return completed;
            }

            public void setCompleted(List<?> completed) {
                this.completed = completed;
            }

            public static class OngoingBean {
                /**
                 * t_id : 100027
                 * status : 0
                 * status_name : 进行中
                 * t_name : 测试目标100027
                 */

                private String t_id;
                private String status;
                private String status_name;
                private String t_name;

                public String getT_id() {
                    return t_id;
                }

                public void setT_id(String t_id) {
                    this.t_id = t_id;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getStatus_name() {
                    return status_name;
                }

                public void setStatus_name(String status_name) {
                    this.status_name = status_name;
                }

                public String getT_name() {
                    return t_name;
                }

                public void setT_name(String t_name) {
                    this.t_name = t_name;
                }
            }
        }
    }
	
	
}
