package yh.app.model;

import java.util.List;

/**
 * 网上迎新
 */

public class WangShangModel {

	/**
	 * content : [{"lj":"","cjzt":"1","ios_notice":"PushPerfectPage","hjmc":"完善信息","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/wsxx_y.png","cdbh":"grxx","class_name":"yh.app.acticity.UserInfoCodeActivity"},{"lj":"","cjzt":"0","ios_notice":"","hjmc":"照片上传","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/zpsc_n.png","cdbh":"zpsc","class_name":""},{"lj":"http://192.168.0.198:8080/ydyx/bdlc/bdlc_skipJxfzcj.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"军训服装采购","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/jxfzcg_n.png","cdbh":"jxfz","class_name":"yh.app.web.Web"},{"lj":"http://192.168.0.198:8080/ydyx/rxzb2/rxzb2_skipBdjt.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"报到情况","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/bdqk_n.png","cdbh":"bdqk","class_name":"yh.app.web.Web"},{"lj":"","cjzt":"0","ios_notice":"","hjmc":"报到单打印","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/bdddy_n.png","cdbh":"bdddy","class_name":""},{"lj":"http://192.168.0.198:8080/ydyx/bdlc/bdlc_skipSscx.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"宿舍查询","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/sscx_n.png","cdbh":"sscx","class_name":"yh.app.web.Web"},{"lj":"http://192.168.0.198:8080/ydyx/bdlc/bdlc_skipShyp.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"生活用品","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/shyp_n.png","cdbh":"shyp","class_name":"yh.app.web.Web"},{"lj":"http://192.168.0.198:8080/ydyx/bdlc/bdlc_cwjf.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"财务缴费","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/cwjf_n.png","cdbh":"cwjf","class_name":"yh.app.web.Web"},{"lj":"http://192.168.0.198:8080/ydyx/bdlc/bdlc_skipBxgm.action?","cjzt":"0","ios_notice":"PushWebPage","hjmc":"保险购买","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/bxgm_n.png","cdbh":"bxgm","class_name":"yh.app.web.Web"},{"lj":"","cjzt":"0","ios_notice":"","hjmc":"学费住宿费","icon_url":"http://192.168.0.102:8080/ydyx/Image/yxxc/xfzsf_n.png","cdbh":"xfzsf","class_name":""}]
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
		 * lj : 
		 * cjzt : 1
		 * ios_notice : PushPerfectPage
		 * hjmc : 完善信息
		 * icon_url : http://192.168.0.102:8080/ydyx/Image/yxxc/wsxx_y.png
		 * cdbh : grxx
		 * class_name : yh.app.acticity.UserInfoCodeActivity
		 */

		private String lj;
		private String cjzt;
		private String ios_notice;
		private String hjmc;
		private String icon_url;
		private String cdbh;
		private String class_name;

		public String getLj() {
			return lj;
		}

		public void setLj(String lj) {
			this.lj = lj;
		}

		public String getCjzt() {
			return cjzt;
		}

		public void setCjzt(String cjzt) {
			this.cjzt = cjzt;
		}

		public String getIos_notice() {
			return ios_notice;
		}

		public void setIos_notice(String ios_notice) {
			this.ios_notice = ios_notice;
		}

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

		public String getCdbh() {
			return cdbh;
		}

		public void setCdbh(String cdbh) {
			this.cdbh = cdbh;
		}

		public String getClass_name() {
			return class_name;
		}

		public void setClass_name(String class_name) {
			this.class_name = class_name;
		}
	}
}
