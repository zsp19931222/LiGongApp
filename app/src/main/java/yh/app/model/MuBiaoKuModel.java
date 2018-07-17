package yh.app.model;

import java.util.List;

public class MuBiaoKuModel {
	/**
	 * content : {"message":[{"sign":"1","name":"校长推荐","type":"1"},{"sign":"1","name":"教师推荐","type":"2"},{"sign":"1","name":"来自广场","type":"3"},{"sign":"1","name":"来自测评","type":"4"},{"sign":"1","name":"来自其他","type":"5"},{"sign":"1","name":"自定义","type":"6"}],"ticket":"89f7a7de-6542-47e9-b1bb-0a6897ae9b54","status":"true","code":"60008"}
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
		 * message : [{"sign":"1","name":"校长推荐","type":"1"},{"sign":"1","name":"教师推荐","type":"2"},{"sign":"1","name":"来自广场","type":"3"},{"sign":"1","name":"来自测评","type":"4"},{"sign":"1","name":"来自其他","type":"5"},{"sign":"1","name":"自定义","type":"6"}]
		 * ticket : 89f7a7de-6542-47e9-b1bb-0a6897ae9b54
		 * status : true
		 * code : 60008
		 */

		private String ticket;
		private String status;
		private String code;
		private List<MessageBean> message;

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

		public List<MessageBean> getMessage() {
			return message;
		}

		public void setMessage(List<MessageBean> message) {
			this.message = message;
		}

		public static class MessageBean {
			/**
			 * sign : 1
			 * name : 校长推荐
			 * type : 1
			 */

			private String sign;
			private String name;
			private String type;

			public String getSign() {
				return sign;
			}

			public void setSign(String sign) {
				this.sign = sign;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
		}
	}
}
