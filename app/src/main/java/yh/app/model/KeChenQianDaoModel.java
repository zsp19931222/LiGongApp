package yh.app.model;

import java.util.List;

public class KeChenQianDaoModel {
	/**
	 * content :
	 * {"message":{"djz":"第10周","xqj":"星期四","rq":"2017-04-27","data":[{"id":"1",
	 * "kcmc":"大学体育【（7）】","xkkh":"(2016-2017-1)-12223-20040018-4","ks":"1","js":
	 * "2","time":"08:00-09:40","status":"未签到","operate":"-1"},{"id":"2","kcmc":
	 * "无机材料工厂设计概论","xkkh":"(2016-2017-1)-10272-19950020-1","ks":"3","js":"4",
	 * "time":"10:00-11:40","status":"未签到","operate":"-1"},{"id":"3","kcmc":
	 * "先进陶瓷材料","xkkh":"(2016-2017-1)-10274-20080028-1","ks":"5","js":"6","time"
	 * :"14:00-15:40","status":"已签到","operate":"1"},{"id":"4","kcmc":"纳米材料【I】",
	 * "xkkh":"(2016-2017-1)-02155-19950020-1","ks":"7","js":"8","time":
	 * "16:00-17:40","status":"签到","operate":"0"}]},"ticket":
	 * "24729c8e-900c-4060-aed6-da9c4674790a","status":"true","code":"60008"}
	 * message : 成功 code : 40001
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
		 * message :
		 * {"djz":"第10周","xqj":"星期四","rq":"2017-04-27","data":[{"id":"1","kcmc":
		 * "大学体育【（7）】","xkkh":"(2016-2017-1)-12223-20040018-4","ks":"1","js":"2"
		 * ,"time":"08:00-09:40","status":"未签到","operate":"-1"},{"id":"2","kcmc"
		 * :"无机材料工厂设计概论","xkkh":"(2016-2017-1)-10272-19950020-1","ks":"3","js":
		 * "4","time":"10:00-11:40","status":"未签到","operate":"-1"},{"id":"3",
		 * "kcmc":"先进陶瓷材料","xkkh":"(2016-2017-1)-10274-20080028-1","ks":"5","js"
		 * :"6","time":"14:00-15:40","status":"已签到","operate":"1"},{"id":"4",
		 * "kcmc":"纳米材料【I】","xkkh":"(2016-2017-1)-02155-19950020-1","ks":"7",
		 * "js":"8","time":"16:00-17:40","status":"签到","operate":"0"}]} ticket :
		 * 24729c8e-900c-4060-aed6-da9c4674790a status : true code : 60008
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
			 * djz : 第10周 xqj : 星期四 rq : 2017-04-27 data :
			 * [{"id":"1","kcmc":"大学体育【（7）】","xkkh":
			 * "(2016-2017-1)-12223-20040018-4","ks":"1","js":"2","time":
			 * "08:00-09:40","status":"未签到","operate":"-1"},{"id":"2","kcmc":
			 * "无机材料工厂设计概论","xkkh":"(2016-2017-1)-10272-19950020-1","ks":"3",
			 * "js":"4","time":"10:00-11:40","status":"未签到","operate":"-1"},{
			 * "id":"3","kcmc":"先进陶瓷材料","xkkh":"(2016-2017-1)-10274-20080028-1",
			 * "ks":"5","js":"6","time":"14:00-15:40","status":"已签到","operate":
			 * "1"},{"id":"4","kcmc":"纳米材料【I】","xkkh":
			 * "(2016-2017-1)-02155-19950020-1","ks":"7","js":"8","time":
			 * "16:00-17:40","status":"签到","operate":"0"}]
			 */

			private String djz;
			private String xqj;
			private String rq;
			private List<DataBean> data;

			public String getDjz() {
				return djz;
			}

			public void setDjz(String djz) {
				this.djz = djz;
			}

			public String getXqj() {
				return xqj;
			}

			public void setXqj(String xqj) {
				this.xqj = xqj;
			}

			public String getRq() {
				return rq;
			}

			public void setRq(String rq) {
				this.rq = rq;
			}

			public List<DataBean> getData() {
				return data;
			}

			public void setData(List<DataBean> data) {
				this.data = data;
			}

			public static class DataBean {
				/**
				 * id : 1 kcmc : 大学体育【（7）】 xkkh : (2016-2017-1)-12223-20040018-4
				 * ks : 1 js : 2 time : 08:00-09:40 status : 未签到 operate : -1
				 */

				private String id;
				private String kcmc;
				private String xkkh;
				private String ks;
				private String js;
				private String time;
				private String status;
				private String operate;

				public String getId() {
					return id;
				}

				public void setId(String id) {
					this.id = id;
				}

				public String getKcmc() {
					return kcmc;
				}

				public void setKcmc(String kcmc) {
					this.kcmc = kcmc;
				}

				public String getXkkh() {
					return xkkh;
				}

				public void setXkkh(String xkkh) {
					this.xkkh = xkkh;
				}

				public String getKs() {
					return ks;
				}

				public void setKs(String ks) {
					this.ks = ks;
				}

				public String getJs() {
					return js;
				}

				public void setJs(String js) {
					this.js = js;
				}

				public String getTime() {
					return time;
				}

				public void setTime(String time) {
					this.time = time;
				}

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getOperate() {
					return operate;
				}

				public void setOperate(String operate) {
					this.operate = operate;
				}
			}
		}
	}
}
