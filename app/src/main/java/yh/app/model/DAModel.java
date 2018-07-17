package yh.app.model;

import java.util.List;

public class DAModel {
	/**
	 * content : [{"banner_id":"15262652302361bb6b6cdd5914306a13df385692268ac","title":"adf名称","function_key":[],"ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=4mu0i1juutg0o7kppjr117ogo7&fileName=%285%29.jpg","funcid":"null","class_name":"null","type":3,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"a","url":"yh.app.appstart.lg"},{"banner_id":"152577244384632cd789f206c414e97ae23a5288e0575","title":"智慧课堂","ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=1st4vco8nadt3oq16go7jbi9bc&fileName=%E6%99%BA%E6%85%A7%E8%AF%BE%E5%A0%82.jpg","funcid":"20150105","class_name":"yh.app.web.Web","type":1,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"7s","url":"undefined"},{"banner_id":"1526264578007db3be96dda514f328291fb102fca97b6","title":"panada","function_key":[],"ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=tlhdon0m2bvb6nhs2plsagj51&fileName=panda.jpg","funcid":"null","class_name":"null","type":3,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"2s","url":"yh.app.appstart.lg"},{"banner_id":"1526281857420f75ec5b6d3f74dd1a607fc18fbb81e7f","title":"cescecfffffffffffffffffffffffff","ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=6ki3bj99j0i5g97nko4d40mkd5&fileName=b.png","funcid":"10000011","class_name":"yh.app.web.Web","type":1,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"4s","url":"yh.app.appstart.lg"},{"banner_id":"1526696345971ca858b12717a4597bb2ab3be54cea8ad","title":"phones","function_key":[],"ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=51ub9mmh8sunikbrugdkm9s4ov&fileName=apps-lg-zh%402x.png","funcid":"null","class_name":"null","type":3,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"6s","url":"undefined"},{"banner_id":"1526697879446d3d750a4e5f7442c917d0e96aa4294f2","title":"hs","ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=1hfk86r2m2n3q4ku2ta5cgkhci&fileName=%E4%BD%8F%E5%AE%BF.png","funcid":"201804103","class_name":"null","type":1,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"2s","url":"undefined"},{"banner_id":"15242748476351d081d54f9bf479f871275f725dc9609","title":"测试刷新","function_key":[],"ios_notice":"null","img":"http://219.153.12.197:11019/fs/filedown?id=7f7vri8m9nbqoslebim21kr938&fileName=%284%29.jpg","funcid":"null","class_name":"null","type":3,"xxbh":"3EAA14419A88D63BB2F32EC68F7BA913","show_time":"333","url":"rrrr"}]
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
		public ContentBean(String banner_id, String title, String ios_notice, Object img, String funcid, String class_name, int type, String xxbh, String show_time, String url, List<String> function_key) {
			this.banner_id = banner_id;
			this.title = title;
			this.ios_notice = ios_notice;
			this.img = img;
			this.funcid = funcid;
			this.class_name = class_name;
			this.type = type;
			this.xxbh = xxbh;
			this.show_time = show_time;
			this.url = url;
			this.function_key = function_key;
		}

		/**
		 * banner_id : 15262652302361bb6b6cdd5914306a13df385692268ac
		 * title : adf名称
		 * function_key : []
		 * ios_notice : null
		 * img : http://219.153.12.197:11019/fs/filedown?id=4mu0i1juutg0o7kppjr117ogo7&fileName=%285%29.jpg
		 * funcid : null
		 * class_name : null
		 * type : 3
		 * xxbh : 3EAA14419A88D63BB2F32EC68F7BA913
		 * show_time : a
		 * url : yh.app.appstart.lg
		 */



		private String banner_id;
		private String title;
		private String ios_notice;
		private Object img;
		private String funcid;
		private String class_name;
		private int type;
		private String xxbh;
		private String show_time;
		private String url;
		private List<String> function_key;

		public String getBanner_id() {
			return banner_id;
		}

		public void setBanner_id(String banner_id) {
			this.banner_id = banner_id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getIos_notice() {
			return ios_notice;
		}

		public void setIos_notice(String ios_notice) {
			this.ios_notice = ios_notice;
		}

		public Object getImg() {
			return img;
		}

		public void setImg(Object img) {
			this.img = img;
		}

		public String getFuncid() {
			return funcid;
		}

		public void setFuncid(String funcid) {
			this.funcid = funcid;
		}

		public String getClass_name() {
			return class_name;
		}

		public void setClass_name(String class_name) {
			this.class_name = class_name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getXxbh() {
			return xxbh;
		}

		public void setXxbh(String xxbh) {
			this.xxbh = xxbh;
		}

		public String getShow_time() {
			return show_time;
		}

		public void setShow_time(String show_time) {
			this.show_time = show_time;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<String> getFunction_key() {
			return function_key;
		}

		public void setFunction_key(List<String> function_key) {
			this.function_key = function_key;
		}
	}
//	/**
//	 * content : [{"AD_PRI":2,"AD_BUILD_TIME":"2017/02/14","AD_FACE":
//	 * "http://192.168.0.198:8081/Image/ad/xxhome4.png","AD_URL":
//	 * "https://www.baidu.com","AD_END_TIME":null,"AD_DESCRIBE":"指定元素中的所有子节点",
//	 * "AD_ID":"4"},{"AD_PRI":3,"AD_BUILD_TIME":"2017/02/13","AD_FACE":
//	 * "http://192.168.0.198:8081/Image/ad/xxhome3.png","AD_URL":null,
//	 * "AD_END_TIME":null,"AD_DESCRIBE":"可仔细观察效果的话就可以发现","AD_ID":"3"},{"AD_PRI":
//	 * 4,"AD_BUILD_TIME":"2017/02/13","AD_FACE":
//	 * "http://192.168.0.198:8081/Image/ad/xxhome5.png","AD_URL":
//	 * "https://www.baidu.com","AD_END_TIME":null,"AD_DESCRIBE":"仍保留其在dom中所占的位置"
//	 * ,"AD_ID":"5"}] boolean : true message : 成功
//	 */
//
//	@SerializedName("boolean")
//	private boolean booleanX;
//	private String message;
//	private List<ContentBean> content;
//
//	public boolean isBooleanX() {
//		return booleanX;
//	}
//
//	public void setBooleanX(boolean booleanX) {
//		this.booleanX = booleanX;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public List<ContentBean> getContent() {
//		return content;
//	}
//
//	public void setContent(List<ContentBean> content) {
//		this.content = content;
//	}
//
//	public static class ContentBean {
//		/**
//		 * AD_PRI : 2 AD_BUILD_TIME : 2017/02/14 AD_FACE :
//		 * http://192.168.0.198:8081/Image/ad/xxhome4.png AD_URL :
//		 * https://www.baidu.com AD_END_TIME : null AD_DESCRIBE : 指定元素中的所有子节点
//		 * AD_ID : 4
//		 */
//
//		private int AD_PRI;
//		private String AD_BUILD_TIME;
//		private Object AD_FACE;
//		private String AD_URL;
//		private Object AD_END_TIME;
//		private String AD_DESCRIBE;
//		private String AD_ID;
//
//		public ContentBean(int AD_PRI, String AD_BUILD_TIME, Object AD_FACE, String AD_URL, Object AD_END_TIME, String AD_DESCRIBE, String AD_ID) {
//			this.AD_PRI = AD_PRI;
//			this.AD_BUILD_TIME = AD_BUILD_TIME;
//			this.AD_FACE = AD_FACE;
//			this.AD_URL = AD_URL;
//			this.AD_END_TIME = AD_END_TIME;
//			this.AD_DESCRIBE = AD_DESCRIBE;
//			this.AD_ID = AD_ID;
//		}
//
//		public int getAD_PRI() {
//			return AD_PRI;
//		}
//
//		public void setAD_PRI(int AD_PRI) {
//			this.AD_PRI = AD_PRI;
//		}
//
//		public String getAD_BUILD_TIME() {
//			return AD_BUILD_TIME;
//		}
//
//		public void setAD_BUILD_TIME(String AD_BUILD_TIME) {
//			this.AD_BUILD_TIME = AD_BUILD_TIME;
//		}
//
//		public Object getAD_FACE() {
//			return AD_FACE;
//		}
//
//		public void setAD_FACE(String AD_FACE) {
//			this.AD_FACE = AD_FACE;
//		}
//
//		public String getAD_URL() {
//			return AD_URL;
//		}
//
//		public void setAD_URL(String AD_URL) {
//			this.AD_URL = AD_URL;
//		}
//
//		public Object getAD_END_TIME() {
//			return AD_END_TIME;
//		}
//
//		public void setAD_END_TIME(Object AD_END_TIME) {
//			this.AD_END_TIME = AD_END_TIME;
//		}
//
//		public String getAD_DESCRIBE() {
//			return AD_DESCRIBE;
//		}
//
//		public void setAD_DESCRIBE(String AD_DESCRIBE) {
//			this.AD_DESCRIBE = AD_DESCRIBE;
//		}
//
//		public String getAD_ID() {
//			return AD_ID;
//		}
//
//		public void setAD_ID(String AD_ID) {
//			this.AD_ID = AD_ID;
//		}
//	}


}
