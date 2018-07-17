/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yh.app.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

public class Constants {

//	public static final String NETWORK_REQUEST_SUCCESS = "40001";
//
//	/**
//	 * 微信appid
//	 */
//	public static final String APP_ID = "wxd930ea5d5a258f4f";
//	
//	/**
//	 * 计步器使用
//	 */
//	public static final int MSG_FROM_CLIENT = 0;
//	public static final int MSG_FROM_SERVER = 1;
//	public static final int REQUEST_SERVER = 2;

//	// 目标库任我内容
//	public static final String target_operate = "0";
//
//	public static String xxmc = null;
//	public static String number = null;// 用户id
//	public static String ticket = null;
//
//	// 密码
//	public static String code = null;
//	public static int usertype;
//	public static String user_type;
//	public static String name;
//	public static String dqjm = "";
//	public static String dqltr = "";
//	public static Handler ChatHandler;
//	public static Context App_Context;
//	public static double screenInch;
//	public static String xx;
//	public static int SqliteVersion;
	//应用管理判断用户是否提交应用修改
//	public static boolean isSubmit=false;
//	//应用管理判断用户是否修改
//	public static boolean isAppManageUpdata=false;
//	//应用管理判断是否执行删除
//	public static boolean isdelete=false;
//	//设置图片大小为1M
//	public static int IO_BUFFER_SIZE=1*1024;
//	
//	public static boolean ISOVER=false;
//	
//	public static Handler viewpagerChange, pushHandler, sq_main_handler;
	// 新浪微博
	/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
	public static final String SINA_WEIBO_APP_KEY = "2045436852";

	/**
	 * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
	 * 
	 * <p>
	 * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响， 但是没有定义将无法使用 SDK 认证登录。
	 * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
	 * </p>
	 */
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

	/**
	 * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
	 * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
	 * 
	 * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的 使用权限，高级权限需要进行申请。
	 * 
	 * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
	 * 
	 * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
	 * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
	 */
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";

	public static List<Map<String, String>> messageList = new ArrayList<Map<String, String>>();

	

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String getVersion(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	public static String getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return String.valueOf(pi.versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "0";
		}
	}

}