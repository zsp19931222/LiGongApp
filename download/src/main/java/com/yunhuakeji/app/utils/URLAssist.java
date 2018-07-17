package com.yunhuakeji.app.utils;

public class URLAssist
{
	private static final String dc = ConfigTools.loadProperties(Constants.xx + "dc");
	private static final String uia = ConfigTools.loadProperties(Constants.xx + "uia");
	private static final String push = ConfigTools.loadProperties(Constants.xx + "push");
	private static final String test = ConfigTools.loadProperties(Constants.xx + "test");
	
	public static final String addString = "yunhua";

	public static enum DC
	{
		成绩查询(URL.score), 通讯录(URL.tongxunlu), 时间(URL.term),

		当前时间(URL.nowterm), 课表查询(URL.kbcx), 发起点名(URL.fqdm),

		学生点名(URL.xsdm), 课堂学生列表(URL.ktxslb), 已点名学生列表(URL.ydmxslb),

		提交点名(URL.tjdm), 校内地图(URL.map), 点名次数(URL.dmcs),

		点名班级(URL.dmbj), 教师小纸条学生名单(URL.jsxztxsmd), 教师对话框内容(URL.jsdhknr),

		学生对话框内容(URL.xsdhknr), 教师列表(URL.jslb), 学生评教(URL.xspj),

		获取学生评教(URL.getxspj), 获取教师评教(URL.getjspj), 发送纸条(URL.fszt),

		所有学生点名结果(URL.syxsdmjg), 圈子好友列表(URL.qzhylb), 圈子默认列表(URL.qzmrlb), 保存课堂信息(URL.saveKtxx),

		添加好友(URL.tjhy), 是否同意添加好友(URL.sftytjhy), 搜索好友(URL.sshy), 节假日(URL.jjr), 所有时间(URL.sysj);
		private String url;

		private DC(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return dc + url;
		}
	}

	public static enum UIA
	{
		修改密码(URL.password), 修改资料(URL.setting), 登录(URL.logon), 登录_老版本(URL.login), 下载页面(URL.download_page_old),

		网页跳转(URL.webview), 功能列表(URL.function), 功能列表_老版本(URL.function_old), 更新(URL.CheckUpdate), 新功能2(URL.apply_url);
		private String url;

		private UIA(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return uia + url;
		}
	}

	public static enum PUSH
	{
		聊天接口(URL.fslt), 聊天反馈接口(URL.fslt), 离线聊天接口(URL.lxlt),

		推送反馈接口(URL.pushreceive), 推送离线接口(URL.pushoffine),

		添加好友反馈接口(URL.tjhyfk), 添加好友离线接口(URL.lxtjhy),

		确认添加好友反馈接口(URL.qrtjhyfk), 确认添加好友离线接口(URL.lxqrtjhy),

		离线接口(URL.lxjk);
		private String url;

		private PUSH(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return push + url;
		}

	}
	
	public static enum TEST
	{
		获取票据(URL_TEST._获取票据), _自主目标(URL_TEST._自主目标), _目标详情(URL_TEST._目标详情);
		private String url;

		private TEST(String url)
		{
			this.url = url;
		}

		public String getUrl()
		{
			return test + url;
		}
	}

	
	private class URL_TEST
	{
		private static final String _获取票据 = "/WDDXSERVER/ticket/createTicket2.action";
		private static final String _自主目标 = "/WDDXSERVER/target/GetMineTarget";
		
		private static final String _目标详情 = "WDDXSERVER/target/getTargetDetails";
	}
	
	private class URL
	{
		private static final String syxsdmjg = "/DC/zhkt/getAllxsdmjg.action";
		private static final String fszt = "/DC/zhkt/saveZtnr.action";
		private static final String score = "/DC/score/getlist.action";
		private static final String login = "/UIA/login/login.action";
		private static final String logon = "/UIA/login/logon.action";
		private static final String tongxunlu = "/DC/txl/getlist.action";
		private static final String term = "/DC/calendar/list.action";
		private static final String nowterm = "/DC/calendar/nowterm.action";
		private static final String webview = "/UIA/rout/geturl.action";
		private static final String kbcx = "/DC/Course/list.action";
		private static final String fqdm = "/DC/zhkt/fqdm.action";
		private static final String xsdm = "/DC/zhkt/xsdm.action";
		private static final String ktxslb = "/DC/zhkt/ktxs.action";
		private static final String function = "/UIA/function/getlist.action";
		private static final String function_old = "/UIA/function/list.action";
		private static final String download_page_old = "/UIA/?userid=";
		private static final String ydmxslb = "/DC/zhkt/yyydmxs.action";
		private static final String tjdm = "/DC/zhkt/tjdmjg.action";
		private static final String CheckUpdate = "/UIA/update/updateVersion.action?mobileType=android&versionCode=";
		private static final String map = "/DC/map/getlist.action";
		private static final String dmcs = "/DC/zhkt/getDmcs.action";
		private static final String dmbj = "/DC/zhkt/dmbj.action";
		private static final String jsxztxsmd = "/DC/zhkt/getJsztxsmd.action";
		private static final String jsdhknr = "/DC/zhkt/getJsdhknr.action";
		private static final String xsdhknr = "/DC/zhkt/getXsdhknr.action";
		private static final String xspj = "/DC/zhkt/saveXspj.action";
		private static final String getxspj = "/DC/zhkt/getXspjnr.action";
		private static final String getjspj = "/DC/zhkt/getXspjnr.action";
		private static final String saveKtxx = "/DC/zhkt/saveKtxx.action";
		private static final String pushreceive = "/push/receive";
		private static final String pushoffine = "/push/offine";
		private static final String setting = "/UIA/user/updateuser.action";
		private static final String password = "/UIA/user/updatepassword.action";
		private static final String jslb = "/DC/zhkt/getKtjsbh.action";
		private static final String qzhylb = "/DC/sq/hqhylb.action";
		private static final String qzmrlb = "/DC/sq/hqmrfz.action";
		private static final String sshy = "/DC/sq/msrcx.action";
		private static final String tjhy = "/DC/sq/tjhy.action";
		private static final String sftytjhy = "/DC/sq/yzhy.action";
		private static final String jjr = "/DC/calendar/jjr.action";
		private static final String sysj = "/DC/calendar/termlist.action";

		private static final String fslt = "/chat/sendChat";
		private static final String lxlt = "/chat/offineSend";
		private static final String tjhyfk = "/chat/receiveAdd";
		private static final String lxtjhy = "/chat/offineAdd";
		private static final String qrtjhyfk = "/chat/receiveReadd";
		private static final String lxqrtjhy = "/chat/offineReadd";
		private static final String lxjk = "/offine/all";
		private static final String apply_url = "/GroupChat/Contacts/getFunctionList";// 应用页地址
	}

}