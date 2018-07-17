package yh.app.model;

import java.util.List;

public class MuBiaoContent {

	/**
	 * content :
	 * {"message":{"total":14,"rows":[{"target_operate":"0","status":"进行中",
	 * "target_name":"测试目标100066","t_follow_num":"3639","target_id":"100066"},{
	 * "target_operate":"0","status":"进行中","target_name":"测试目标100030",
	 * "t_follow_num":"791","target_id":"100030"},{"target_operate":"0","status"
	 * :"进行中","target_name":"测试目标100024","t_follow_num":"4992","target_id":
	 * "100024"},{"target_operate":"0","status":"进行中","target_name":"测试目标100012"
	 * ,"t_follow_num":"3668","target_id":"100012"},{"target_operate":"0",
	 * "status":"进行中","target_name":"测试目标10006","t_follow_num":"851","target_id"
	 * :"10006"},{"target_operate":"0","status":"进行中","target_name":"测试目标100096"
	 * ,"t_follow_num":"983","target_id":"100096"},{"target_operate":"1",
	 * "status":"4700","target_name":"测试目标100078","t_follow_num":"4700",
	 * "target_id":"100078"},{"target_operate":"1","status":"3596","target_name"
	 * :"测试目标100084","t_follow_num":"3596","target_id":"100084"},{
	 * "target_operate":"1","status":"3578","target_name":"测试目标100036",
	 * "t_follow_num":"3578","target_id":"100036"},{"target_operate":"1",
	 * "status":"2969","target_name":"测试目标100072","t_follow_num":"2969",
	 * "target_id":"100072"},{"target_operate":"1","status":"2423","target_name"
	 * :"测试目标100060","t_follow_num":"2423","target_id":"100060"},{
	 * "target_operate":"1","status":"1939","target_name":"测试目标100090",
	 * "t_follow_num":"1939","target_id":"100090"},{"target_operate":"1",
	 * "status":"1878","target_name":"测试目标100018","t_follow_num":"1878",
	 * "target_id":"100018"},{"target_operate":"1","status":"0","target_name":
	 * "学驾驶","t_follow_num":"0","target_id":
	 * "59fe6f22-3cee-44eb-9ed3-d5c3ed3d7e90"}]},"ticket":
	 * "abe34261-069a-4940-b956-aca67213ebcb","status":"true","code":"60008"}
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
		 * message : {"total":14,"rows":[{"target_operate":"0","status":"进行中",
		 * "target_name":"测试目标100066","t_follow_num":"3639","target_id":"100066"
		 * },{"target_operate":"0","status":"进行中","target_name":"测试目标100030",
		 * "t_follow_num":"791","target_id":"100030"},{"target_operate":"0",
		 * "status":"进行中","target_name":"测试目标100024","t_follow_num":"4992",
		 * "target_id":"100024"},{"target_operate":"0","status":"进行中",
		 * "target_name":"测试目标100012","t_follow_num":"3668","target_id":"100012"
		 * },{"target_operate":"0","status":"进行中","target_name":"测试目标10006",
		 * "t_follow_num":"851","target_id":"10006"},{"target_operate":"0",
		 * "status":"进行中","target_name":"测试目标100096","t_follow_num":"983",
		 * "target_id":"100096"},{"target_operate":"1","status":"4700",
		 * "target_name":"测试目标100078","t_follow_num":"4700","target_id":"100078"
		 * },{"target_operate":"1","status":"3596","target_name":"测试目标100084",
		 * "t_follow_num":"3596","target_id":"100084"},{"target_operate":"1",
		 * "status":"3578","target_name":"测试目标100036","t_follow_num":"3578",
		 * "target_id":"100036"},{"target_operate":"1","status":"2969",
		 * "target_name":"测试目标100072","t_follow_num":"2969","target_id":"100072"
		 * },{"target_operate":"1","status":"2423","target_name":"测试目标100060",
		 * "t_follow_num":"2423","target_id":"100060"},{"target_operate":"1",
		 * "status":"1939","target_name":"测试目标100090","t_follow_num":"1939",
		 * "target_id":"100090"},{"target_operate":"1","status":"1878",
		 * "target_name":"测试目标100018","t_follow_num":"1878","target_id":"100018"
		 * },{"target_operate":"1","status":"0","target_name":"学驾驶",
		 * "t_follow_num":"0","target_id":"59fe6f22-3cee-44eb-9ed3-d5c3ed3d7e90"
		 * }]} ticket : abe34261-069a-4940-b956-aca67213ebcb status : true code
		 * : 60008
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
			 * total : 14 rows :
			 * [{"target_operate":"0","status":"进行中","target_name":"测试目标100066",
			 * "t_follow_num":"3639","target_id":"100066"},{"target_operate":"0"
			 * ,"status":"进行中","target_name":"测试目标100030","t_follow_num":"791",
			 * "target_id":"100030"},{"target_operate":"0","status":"进行中",
			 * "target_name":"测试目标100024","t_follow_num":"4992","target_id":
			 * "100024"},{"target_operate":"0","status":"进行中","target_name":
			 * "测试目标100012","t_follow_num":"3668","target_id":"100012"},{
			 * "target_operate":"0","status":"进行中","target_name":"测试目标10006",
			 * "t_follow_num":"851","target_id":"10006"},{"target_operate":"0",
			 * "status":"进行中","target_name":"测试目标100096","t_follow_num":"983",
			 * "target_id":"100096"},{"target_operate":"1","status":"4700",
			 * "target_name":"测试目标100078","t_follow_num":"4700","target_id":
			 * "100078"},{"target_operate":"1","status":"3596","target_name":
			 * "测试目标100084","t_follow_num":"3596","target_id":"100084"},{
			 * "target_operate":"1","status":"3578","target_name":"测试目标100036",
			 * "t_follow_num":"3578","target_id":"100036"},{"target_operate":"1"
			 * ,"status":"2969","target_name":"测试目标100072","t_follow_num":"2969"
			 * ,"target_id":"100072"},{"target_operate":"1","status":"2423",
			 * "target_name":"测试目标100060","t_follow_num":"2423","target_id":
			 * "100060"},{"target_operate":"1","status":"1939","target_name":
			 * "测试目标100090","t_follow_num":"1939","target_id":"100090"},{
			 * "target_operate":"1","status":"1878","target_name":"测试目标100018",
			 * "t_follow_num":"1878","target_id":"100018"},{"target_operate":"1"
			 * ,"status":"0","target_name":"学驾驶","t_follow_num":"0","target_id":
			 * "59fe6f22-3cee-44eb-9ed3-d5c3ed3d7e90"}]
			 */

			private int total;
			private List<RowsBean> rows;

			public int getTotal() {
				return total;
			}

			public void setTotal(int total) {
				this.total = total;
			}

			public List<RowsBean> getRows() {
				return rows;
			}

			public void setRows(List<RowsBean> rows) {
				this.rows = rows;
			}

			public static class RowsBean {
				/**
				 * target_operate : 0 status : 进行中 target_name : 测试目标100066
				 * t_follow_num : 3639 target_id : 100066
				 */

				private String target_operate;
				private String status;
				private String target_name;
				private String t_follow_num;
				private String target_id;

				public String getTarget_operate() {
					return target_operate;
				}

				public void setTarget_operate(String target_operate) {
					this.target_operate = target_operate;
				}

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getTarget_name() {
					return target_name;
				}

				public void setTarget_name(String target_name) {
					this.target_name = target_name;
				}

				public String getT_follow_num() {
					return t_follow_num;
				}

				public void setT_follow_num(String t_follow_num) {
					this.t_follow_num = t_follow_num;
				}

				public String getTarget_id() {
					return target_id;
				}

				public void setTarget_id(String target_id) {
					this.target_id = target_id;
				}
			}
		}
	}
}
