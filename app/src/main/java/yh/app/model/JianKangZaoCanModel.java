package yh.app.model;

public class JianKangZaoCanModel {


	/**
	 * content : {"message":{"djz":"第10周","xqj":"星期四","rq":"2017-04-27","status":"已吃早餐","bz":"0","time":"09:23","starttime":"06:30","endtime":"09:30","title":"【温馨提示】","describe":"早餐距离前一晚餐的时间较长，一般在12小时后，体内储存的糖原量已经消耗殆尽，应及时补充，以免出现血糖过低，血糖浓度低于正常值会出现饥饿感，大脑的兴奋性随之降低，反应迟钝，注意力不集中，所以，不吃早餐和早餐的质与量不够，容易引起能量和营养素的不足，降低上午工作学习的效率。"},"ticket":"f29e99ae-7671-424b-ae33-a3586bd8bd0e","status":"true","code":"60008"}
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
		 * message : {"djz":"第10周","xqj":"星期四","rq":"2017-04-27","status":"已吃早餐","bz":"0","time":"09:23","starttime":"06:30","endtime":"09:30","title":"【温馨提示】","describe":"早餐距离前一晚餐的时间较长，一般在12小时后，体内储存的糖原量已经消耗殆尽，应及时补充，以免出现血糖过低，血糖浓度低于正常值会出现饥饿感，大脑的兴奋性随之降低，反应迟钝，注意力不集中，所以，不吃早餐和早餐的质与量不够，容易引起能量和营养素的不足，降低上午工作学习的效率。"}
		 * ticket : f29e99ae-7671-424b-ae33-a3586bd8bd0e
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
			 * djz : 第10周
			 * xqj : 星期四
			 * rq : 2017-04-27
			 * status : 已吃早餐
			 * bz : 0
			 * time : 09:23
			 * starttime : 06:30
			 * endtime : 09:30
			 * title : 【温馨提示】
			 * describe : 早餐距离前一晚餐的时间较长，一般在12小时后，体内储存的糖原量已经消耗殆尽，应及时补充，以免出现血糖过低，血糖浓度低于正常值会出现饥饿感，大脑的兴奋性随之降低，反应迟钝，注意力不集中，所以，不吃早餐和早餐的质与量不够，容易引起能量和营养素的不足，降低上午工作学习的效率。
			 */

			private String djz;
			private String xqj;
			private String rq;
			private String status;
			private String bz;
			private String time;
			private String starttime;
			private String endtime;
			private String title;
			private String describe;

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

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public String getBz() {
				return bz;
			}

			public void setBz(String bz) {
				this.bz = bz;
			}

			public String getTime() {
				return time;
			}

			public void setTime(String time) {
				this.time = time;
			}

			public String getStarttime() {
				return starttime;
			}

			public void setStarttime(String starttime) {
				this.starttime = starttime;
			}

			public String getEndtime() {
				return endtime;
			}

			public void setEndtime(String endtime) {
				this.endtime = endtime;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getDescribe() {
				return describe;
			}

			public void setDescribe(String describe) {
				this.describe = describe;
			}
		}
	}
}
