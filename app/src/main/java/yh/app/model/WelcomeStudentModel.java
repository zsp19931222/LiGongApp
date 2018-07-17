package yh.app.model;

import java.util.List;

/**
 * 入学准备数据模型
 */

public class WelcomeStudentModel {

	/**
	 * content : [{"cls":"yh.app.acticity.UserInfoCodeActivity","gnlx":"1","ios_notice":"PushPerfectPage","gnyxj":"0","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/wsxx_y.png","gnzt":"0","gnid":"10001","gnmc":"完善信息","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"1","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/rxxz_n.png","gnzt":"1","gnid":"10002","gnmc":"入学须知","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"1","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/wjdc_n.png","gnzt":"1","gnid":"10003","gnmc":"调查问卷","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"1","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/bxgm_n.png","gnzt":"1","gnid":"10004","gnmc":"保险购买","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/bdjt_n.png","gnzt":"2","gnid":"10005","gnmc":"报道交通","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/csxy_n.png","gnzt":"2","gnid":"10006","gnmc":"初识校园","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/lstd_n.png","gnzt":"2","gnid":"10007","gnmc":"绿色通道","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/jlsy_n.png","gnzt":"2","gnid":"10008","gnmc":"军旅生涯","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/jgb_n.png","gnzt":"2","gnid":"10009","gnmc":"教改班","url":"https://www.baidu.com"},{"cls":"yh.app.web.web","gnlx":"2","ios_notice":"PushWebPage","gnyxj":"2","icon_url":"http://192.168.0.102:8080/ydyx/Image/rxzb/kaist_n.png","gnzt":"2","gnid":"10010","gnmc":"KAIST项目","url":"https://www.baidu.com"}]
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
		 * cls : yh.app.acticity.UserInfoCodeActivity
		 * gnlx : 1
		 * ios_notice : PushPerfectPage
		 * gnyxj : 0
		 * icon_url : http://192.168.0.102:8080/ydyx/Image/rxzb/wsxx_y.png
		 * gnzt : 0
		 * gnid : 10001
		 * gnmc : 完善信息
		 * url : https://www.baidu.com
		 */

		private String cls;
		private String gnlx;
		private String ios_notice;
		private String gnyxj;
		private String icon_url;
		private String gnzt;
		private String gnid;
		private String gnmc;
		private String url;
		private String jkfwbs;

		public String getCls() {
			return cls;
		}

		public void setCls(String cls) {
			this.cls = cls;
		}

		public String getGnlx() {
			return gnlx;
		}

		public void setGnlx(String gnlx) {
			this.gnlx = gnlx;
		}

		public String getIos_notice() {
			return ios_notice;
		}

		public void setIos_notice(String ios_notice) {
			this.ios_notice = ios_notice;
		}

		public String getGnyxj() {
			return gnyxj;
		}

		public void setGnyxj(String gnyxj) {
			this.gnyxj = gnyxj;
		}

		public String getIcon_url() {
			return icon_url;
		}

		public void setIcon_url(String icon_url) {
			this.icon_url = icon_url;
		}

		public String getGnzt() {
			return gnzt;
		}

		public void setGnzt(String gnzt) {
			this.gnzt = gnzt;
		}

		public String getGnid() {
			return gnid;
		}

		public void setGnid(String gnid) {
			this.gnid = gnid;
		}

		public String getGnmc() {
			return gnmc;
		}

		public void setGnmc(String gnmc) {
			this.gnmc = gnmc;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getJkfwbs()
		{
		    return jkfwbs;
		}

		public void setJkfwbs(String jkfwbs)
		{
		    this.jkfwbs = jkfwbs;
		}
		
	}

}
