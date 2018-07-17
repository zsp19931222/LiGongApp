package yh.app.model;

import java.util.List;

public class YXXCModel {
	/**
	 * content : [{"hjmc":"统一报到","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/tybd_y.png","hjzt":"1","hjbh":"GD_001"}]
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
		 * hjmc : 统一报到
		 * icon_url : http://192.168.0.102:8080/ydyx/Image/yxxc/tybd_y.png
		 * hjzt : 1
		 * hjbh : GD_001
		 */

		private String hjmc;
		private String icon_url;
		private String hjzt;
		private String hjbh;

		public String getHjmc() {
			return hjmc;
		}

		public void setHjmc(String hjmc) {
			this.hjmc = hjmc;
		}

		public String getIcon_url() {
			return icon_url;
		}

		public void setIcon_url(String icon_url) {
			this.icon_url = icon_url;
		}

		public String getHjzt() {
			return hjzt;
		}

		public void setHjzt(String hjzt) {
			this.hjzt = hjzt;
		}

		public String getHjbh() {
			return hjbh;
		}

		public void setHjbh(String hjbh) {
			this.hjbh = hjbh;
		}
	}
}
