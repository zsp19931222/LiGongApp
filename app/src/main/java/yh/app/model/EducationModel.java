package yh.app.model;

import java.util.List;

public class EducationModel {
	/**
	 * content : [{"id":"2F40094050CB2B25E050007F01002DA3","zw":"班长","zmr":"老叶","jssj":"2016-12","kssj":"2016-10","xxmc":"正方大学"}]
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
		 * id : 2F40094050CB2B25E050007F01002DA3
		 * zw : 班长
		 * zmr : 老叶
		 * jssj : 2016-12
		 * kssj : 2016-10
		 * xxmc : 正方大学
		 */

		private String id;
		private String zw;
		private String zmr;
		private String jssj;
		private String kssj;
		private String xxmc;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getZw() {
			return zw;
		}

		public void setZw(String zw) {
			this.zw = zw;
		}

		public String getZmr() {
			return zmr;
		}

		public void setZmr(String zmr) {
			this.zmr = zmr;
		}

		public String getJssj() {
			return jssj;
		}

		public void setJssj(String jssj) {
			this.jssj = jssj;
		}

		public String getKssj() {
			return kssj;
		}

		public void setKssj(String kssj) {
			this.kssj = kssj;
		}

		public String getXxmc() {
			return xxmc;
		}

		public void setXxmc(String xxmc) {
			this.xxmc = xxmc;
		}
	}
}
