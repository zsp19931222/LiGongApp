package yh.app.model;

public class BasicModel {
	/**
	 * content : {"rdtsj":"2016-05-01","jg":"黑龙江省哈尔滨市市辖区","hkxz":"城镇","dzyx":"1122333","lxdh":"18202729601","lqh":"123456","jkzk":"健康","jgdm":"230101","xx":"o型","qq":"1234156789"}
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
		 * rdtsj : 2016-05-01
		 * jg : 黑龙江省哈尔滨市市辖区
		 * hkxz : 城镇
		 * dzyx : 1122333
		 * lxdh : 18202729601
		 * lqh : 123456
		 * jkzk : 健康
		 * jgdm : 230101
		 * xx : o型
		 * qq : 1234156789
		 */

		private String rdtsj;
		private String jg;
		private String hkxz;
		private String dzyx;
		private String lxdh;
		private String lqh;
		private String jkzk;
		private String jgdm;
		private String xx;
		private String qq;

		public String getRdtsj() {
			return rdtsj;
		}

		public void setRdtsj(String rdtsj) {
			this.rdtsj = rdtsj;
		}

		public String getJg() {
			return jg;
		}

		public void setJg(String jg) {
			this.jg = jg;
		}

		public String getHkxz() {
			return hkxz;
		}

		public void setHkxz(String hkxz) {
			this.hkxz = hkxz;
		}

		public String getDzyx() {
			return dzyx;
		}

		public void setDzyx(String dzyx) {
			this.dzyx = dzyx;
		}

		public String getLxdh() {
			return lxdh;
		}

		public void setLxdh(String lxdh) {
			this.lxdh = lxdh;
		}

		public String getLqh() {
			return lqh;
		}

		public void setLqh(String lqh) {
			this.lqh = lqh;
		}

		public String getJkzk() {
			return jkzk;
		}

		public void setJkzk(String jkzk) {
			this.jkzk = jkzk;
		}

		public String getJgdm() {
			return jgdm;
		}

		public void setJgdm(String jgdm) {
			this.jgdm = jgdm;
		}

		public String getXx() {
			return xx;
		}

		public void setXx(String xx) {
			this.xx = xx;
		}

		public String getQq() {
			return qq;
		}

		public void setQq(String qq) {
			this.qq = qq;
		}
	}
}
