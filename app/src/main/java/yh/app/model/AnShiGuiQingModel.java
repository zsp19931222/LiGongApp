package yh.app.model;

public class AnShiGuiQingModel {
	/**
	 * content :
	 * {"message":{"djz":"第10周","xqj":"星期四","rq":"2017-04-27","status":"你还没有归寝",
	 * "bz":"-1","time":"23:59","title":"【温馨提示】","describe":
	 * "在学校规定的作息时间内回寝室，不仅是纪律的要求，也是对自己人身安全的负责。","starttime":"20:00","endtime":
	 * "22:30"},"ticket":"411796db-88e1-4966-be01-2d9a9d47347b","status":"false"
	 * ,"code":"60002"} message : 成功 code : 40001
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
		 * {"djz":"第10周","xqj":"星期四","rq":"2017-04-27","status":"你还没有归寝","bz":
		 * "-1","time":"23:59","title":"【温馨提示】","describe":
		 * "在学校规定的作息时间内回寝室，不仅是纪律的要求，也是对自己人身安全的负责。","starttime":"20:00","endtime"
		 * :"22:30"} ticket : 411796db-88e1-4966-be01-2d9a9d47347b status :
		 * false code : 60002
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
			 * djz : 第10周 xqj : 星期四 rq : 2017-04-27 status : 你还没有归寝 bz : -1 time
			 * : 23:59 title : 【温馨提示】 describe :
			 * 在学校规定的作息时间内回寝室，不仅是纪律的要求，也是对自己人身安全的负责。 starttime : 20:00 endtime :
			 * 22:30
			 */

			private String djz;
			private String xqj;
			private String rq;
			private String status;
			private String bz;
			private String time;
			private String title;
			private String describe;
			private String starttime;
			private String endtime;

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
		}
	}
}
