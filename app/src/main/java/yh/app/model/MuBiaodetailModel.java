package yh.app.model;

import java.util.List;

public class MuBiaodetailModel {
	/**
	 * content : {"message":{"t_id":"5NC","details":[{"total":"8","c_sign_times":"1","c_resource":"资源1","c_completion":"0","c_id":"5NC1","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC1"},{"total":"19","c_sign_times":"1","c_resource":"资源2","c_completion":"0","c_id":"5NC2","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC2"},{"total":"7","c_sign_times":"0","c_resource":"资源3","c_completion":"0","c_id":"5NC3","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC3"},{"total":"15","c_sign_times":"0","c_resource":"资源4","c_completion":"0","c_id":"5NC4","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC4"},{"total":"10","c_sign_times":"0","c_resource":"资源5","c_completion":"0","c_id":"5NC5","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC5"}],"t_name":"测试名称zy_17","t_describe":"随便写几个字","sign_count":"1","sign_img":["http://192.168.1.198:8081/Image/tx/tx5.png"]},"ticket":"078f3261-42b0-4b91-af09-7094d36275c4","status":"true","code":"60008"}
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
		 * message : {"t_id":"5NC","details":[{"total":"8","c_sign_times":"1","c_resource":"资源1","c_completion":"0","c_id":"5NC1","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC1"},{"total":"19","c_sign_times":"1","c_resource":"资源2","c_completion":"0","c_id":"5NC2","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC2"},{"total":"7","c_sign_times":"0","c_resource":"资源3","c_completion":"0","c_id":"5NC3","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC3"},{"total":"15","c_sign_times":"0","c_resource":"资源4","c_completion":"0","c_id":"5NC4","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC4"},{"total":"10","c_sign_times":"0","c_resource":"资源5","c_completion":"0","c_id":"5NC5","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC5"}],"t_name":"测试名称zy_17","t_describe":"随便写几个字","sign_count":"1","sign_img":["http://192.168.1.198:8081/Image/tx/tx5.png"]}
		 * ticket : 078f3261-42b0-4b91-af09-7094d36275c4
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
			 * t_id : 5NC
			 * details : [{"total":"8","c_sign_times":"1","c_resource":"资源1","c_completion":"0","c_id":"5NC1","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC1"},{"total":"19","c_sign_times":"1","c_resource":"资源2","c_completion":"0","c_id":"5NC2","can_sign":"0","c_resource_name":"","c_name":"分解测试项5NC2"},{"total":"7","c_sign_times":"0","c_resource":"资源3","c_completion":"0","c_id":"5NC3","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC3"},{"total":"15","c_sign_times":"0","c_resource":"资源4","c_completion":"0","c_id":"5NC4","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC4"},{"total":"10","c_sign_times":"0","c_resource":"资源5","c_completion":"0","c_id":"5NC5","can_sign":"1","c_resource_name":"","c_name":"分解测试项5NC5"}]
			 * t_name : 测试名称zy_17
			 * t_describe : 随便写几个字
			 * sign_count : 1
			 * sign_img : ["http://192.168.1.198:8081/Image/tx/tx5.png"]
			 */

			private String t_id;
			private String t_name;
			private String t_describe;
			private String sign_count;
			private List<DetailsBean> details;
			private List<String> sign_img;

			public String getT_id() {
				return t_id;
			}

			public void setT_id(String t_id) {
				this.t_id = t_id;
			}

			public String getT_name() {
				return t_name;
			}

			public void setT_name(String t_name) {
				this.t_name = t_name;
			}

			public String getT_describe() {
				return t_describe;
			}

			public void setT_describe(String t_describe) {
				this.t_describe = t_describe;
			}

			public String getSign_count() {
				return sign_count;
			}

			public void setSign_count(String sign_count) {
				this.sign_count = sign_count;
			}

			public List<DetailsBean> getDetails() {
				return details;
			}

			public void setDetails(List<DetailsBean> details) {
				this.details = details;
			}

			public List<String> getSign_img() {
				return sign_img;
			}

			public void setSign_img(List<String> sign_img) {
				this.sign_img = sign_img;
			}

			public static class DetailsBean {
				/**
				 * total : 8
				 * c_sign_times : 1
				 * c_resource : 资源1
				 * c_completion : 0
				 * c_id : 5NC1
				 * can_sign : 0
				 * c_resource_name : 
				 * c_name : 分解测试项5NC1
				 */

				private String total;
				private String c_sign_times;
				private String c_resource;
				private String c_completion;
				private String c_id;
				private String can_sign;
				private String c_resource_name;
				private String c_name;

				public String getTotal() {
					return total;
				}

				public void setTotal(String total) {
					this.total = total;
				}

				public String getC_sign_times() {
					return c_sign_times;
				}

				public void setC_sign_times(String c_sign_times) {
					this.c_sign_times = c_sign_times;
				}

				public String getC_resource() {
					return c_resource;
				}

				public void setC_resource(String c_resource) {
					this.c_resource = c_resource;
				}

				public String getC_completion() {
					return c_completion;
				}

				public void setC_completion(String c_completion) {
					this.c_completion = c_completion;
				}

				public String getC_id() {
					return c_id;
				}

				public void setC_id(String c_id) {
					this.c_id = c_id;
				}

				public String getCan_sign() {
					return can_sign;
				}

				public void setCan_sign(String can_sign) {
					this.can_sign = can_sign;
				}

				public String getC_resource_name() {
					return c_resource_name;
				}

				public void setC_resource_name(String c_resource_name) {
					this.c_resource_name = c_resource_name;
				}

				public String getC_name() {
					return c_name;
				}

				public void setC_name(String c_name) {
					this.c_name = c_name;
				}
			}
		}
	}
}
