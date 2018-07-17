package yh.app.model;

import java.util.List;

public class FamilyModel {
	/**
	 * content : [{"id":"2FCE50E644A44A82E050007F01004E84","zmm":"","zw":"你们要好好","dddw":"我的人都有自己","lxdh":"你们","gx":"零零落落","xm":"各国语言","nl":"555"},{"id":"2FCE48225DFF6D31E050007F01004E86","zmm":"","zw":"这里就是","dddw":"分沟沟壑壑","lxdh":"777","gx":"你也","xm":"不能","nl":"111"}]
	 * message : 成功
	 * code : 40001
	 */

	private String message;
	private String code;
	private List<ContentBean> content;

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

	public List<ContentBean> getContent() {
		return content;
	}

	public void setContent(List<ContentBean> content) {
		this.content = content;
	}

	public static class ContentBean {
		/**
		 * id : 2FCE50E644A44A82E050007F01004E84
		 * zmm : 
		 * zw : 你们要好好
		 * dddw : 我的人都有自己
		 * lxdh : 你们
		 * gx : 零零落落
		 * xm : 各国语言
		 * nl : 555
		 */

		private String id;
		private String zzmm;
		private String zw;
		private String dddw;
		private String lxdh;
		private String gx;
		private String xm;
		private String nl;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getZZmm() {
			return zzmm;
		}

		public void setZZmm(String zmm) {
			this.zzmm = zmm;
		}

		public String getZw() {
			return zw;
		}

		public void setZw(String zw) {
			this.zw = zw;
		}

		public String getDddw() {
			return dddw;
		}

		public void setDddw(String dddw) {
			this.dddw = dddw;
		}

		public String getLxdh() {
			return lxdh;
		}

		public void setLxdh(String lxdh) {
			this.lxdh = lxdh;
		}

		public String getGx() {
			return gx;
		}

		public void setGx(String gx) {
			this.gx = gx;
		}

		public String getXm() {
			return xm;
		}

		public void setXm(String xm) {
			this.xm = xm;
		}

		public String getNl() {
			return nl;
		}

		public void setNl(String nl) {
			this.nl = nl;
		}
	}
}
