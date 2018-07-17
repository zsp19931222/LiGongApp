package yh.app.model;

import java.util.List;

public class WelcomeStudentNotice {

	/**
	 * content : {"url":"http://www.cqvie.edu.cn/cqviezmh/xntz/","list":[{"url":"http://www.cqvie.edu.cn/2017/0623/32275.html","bt":"【工程大讲堂之（九十八）】张永琴：地下采动对高陡边坡稳定性影响","bq":"公告"},{"url":"http://www.cqvie.edu.cn/2017/0623/32275.html","bt":"【工程大讲堂之（九十八）】张永琴：地下采动对高陡边坡稳定性影响","bq":"动态"}]}
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
		 * url : http://www.cqvie.edu.cn/cqviezmh/xntz/
		 * list : [{"url":"http://www.cqvie.edu.cn/2017/0623/32275.html","bt":"【工程大讲堂之（九十八）】张永琴：地下采动对高陡边坡稳定性影响","bq":"公告"},{"url":"http://www.cqvie.edu.cn/2017/0623/32275.html","bt":"【工程大讲堂之（九十八）】张永琴：地下采动对高陡边坡稳定性影响","bq":"动态"}]
		 */

		private String url;
		private List<ListBean> list;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<ListBean> getList() {
			return list;
		}

		public void setList(List<ListBean> list) {
			this.list = list;
		}

		public static class ListBean {
			/**
			 * url : http://www.cqvie.edu.cn/2017/0623/32275.html
			 * bt : 【工程大讲堂之（九十八）】张永琴：地下采动对高陡边坡稳定性影响
			 * bq : 公告
			 */

			private String url;
			private String bt;
			private String bq;

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getBt() {
				return bt;
			}

			public void setBt(String bt) {
				this.bt = bt;
			}

			public String getBq() {
				return bq;
			}

			public void setBq(String bq) {
				this.bq = bq;
			}
		}
	}
}
