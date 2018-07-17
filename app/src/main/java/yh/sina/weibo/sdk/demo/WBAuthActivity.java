/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
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

package yh.sina.weibo.sdk.demo;

import android.annotation.SuppressLint;
import android.app.Activity;

/**
 * 该类主要演示如何进行授权、SSO登陆。
 * 
 * @author SINA
 * @since 2013-09-29
 */
@SuppressLint(
{
	"SimpleDateFormat", "HandlerLeak"
})
public class WBAuthActivity extends Activity {
//	private AuthInfo mAuthInfo;
//
//    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
//    private Oauth2AccessToken mAccessToken;
//
//    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
//    private SsoHandler mSsoHandler;
//
//    private LinearLayout main_layout;
//
//    /**
//     * @see {@link Activity#onCreate}
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//	super.onCreate(savedInstanceState);
//	setContentView(R.layout.activity_auth);
//
//	main_layout = (LinearLayout) findViewById(R.id.layout);
//
//	// 获取 Token View，并让提示 View 的内容可滚动（小屏幕可能显示不全）
//
//	// 创建微博实例
//	// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
//	// Constants.REDIRECT_URL, Constants.SCOPE);
//	// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
//	mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
//	mSsoHandler = new SsoHandler(WBAuthActivity.this, mAuthInfo);
//
//	// SSO 授权, ALL IN ONE 如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
//	findViewById(R.id.obtain_token_via_signature).setOnClickListener(new OnClickListener()
//	{
//	    @Override
//	    public void onClick(View v)
//	    {
//		mSsoHandler.authorize(new AuthListener());
//	    }
//	});
//
//	// 用户登出
//	findViewById(R.id.logout).setOnClickListener(new OnClickListener()
//	{
//	    @Override
//	    public void onClick(View v)
//	    {
//		AccessTokenKeeper.clear(getApplicationContext());
//		mAccessToken = new Oauth2AccessToken();
//		updateTokenView(false);
//	    }
//	});
//
//	// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
//	// 第一次启动本应用，AccessToken 不可用
//	mAccessToken = AccessTokenKeeper.readAccessToken(this);
//	updateTokenView(mAccessToken.isSessionValid());
//    }
//
//    /**
//     * 当 SSO 授权 Activity 退出时，该函数被调用。
//     *
//     * @see {@link Activity#onActivityResult}
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//	super.onActivityResult(requestCode, resultCode, data);
//
//	// SSO 授权回调
//	// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//	if (mSsoHandler != null)
//	{
//	    mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//	}
//
//    }
//
//    /**
//     * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
//     * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
//     * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
//     * SharedPreferences 中。
//     */
//    class AuthListener implements WeiboAuthListener
//    {
//
//	@Override
//	public void onComplete(Bundle values)
//	{
//	    // 从 Bundle 中解析 Token
//	    mAccessToken = Oauth2AccessToken.parseAccessToken(values);
//	    mAccessToken.getPhoneNum();
//	    if (mAccessToken.isSessionValid())
//	    {
//		// 显示 Token
//		updateTokenView(mAccessToken.isSessionValid());
//
//		// 保存 Token 到 SharedPreferences
//		AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);
//		Toast.makeText(WBAuthActivity.this, R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
//	    } else
//	    {
//		// 以下几种情况，您会收到 Code：
//		// 1. 当您未在平台上注册的应用程序的包名与签名时；
//		// 2. 当您注册的应用程序包名与签名不正确时；
//		// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
//		String code = values.getString("code");
//		String message = getString(R.string.weibosdk_demo_toast_auth_failed);
//		if (!TextUtils.isEmpty(code))
//		{
//		    message = message + "\nObtained the code: " + code;
//		}
//		Toast.makeText(WBAuthActivity.this, message, Toast.LENGTH_SHORT).show();
//	    }
//	}
//
//	@Override
//	public void onCancel()
//	{
//	    Toast.makeText(WBAuthActivity.this, R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_SHORT).show();
//	}
//
//	@Override
//	public void onWeiboException(WeiboException e)
//	{
//	    Toast.makeText(WBAuthActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//	}
//    }
//
//    /**
//     * 显示当前 Token 信息。
//     *
//     * @param hasExisted
//     *            配置文件中是否已存在 token 信息并且合法
//     */
//    private void updateTokenView(boolean hasExisted)
//    {
//	if (hasExisted)
//	{
//	    main_layout.removeAllViews();
//	    UsersAPI userAPI = new UsersAPI(this, Constants.APP_KEY, mAccessToken);
//	    userAPI.show(Long.parseLong(mAccessToken.getUid()), new RequestListener()
//	    {
//		@Override
//		public void onComplete(String response)
//		{
//		    if (!TextUtils.isEmpty(response))
//		    {
//			// 调用 User#parse 将JSON串解析成User对象
//			try
//			{
//			    setView(new JSONObject(response));
//			} catch (Exception e)
//			{
//			}
//		    }
//		}
//
//		@Override
//		public void onWeiboException(WeiboException arg0)
//		{
//		    // TODO Auto-generated method stub
//
//		}
//	    });
//	} else
//	    try
//	    {
//		mSsoHandler.authorize(new AuthListener());
//	    } catch (Exception e)
//	    {
//		e.printStackTrace();
//	    }
//
//    }
//
//    private View UserView;
//    private Bitmap tx;
//    private JSONObject jso;
//
//    private void setView(JSONObject jso)
//    {
//	this.jso = jso;
//	try
//	{
//	    if (UserView == null)
//	    {
//		UserView = LayoutInflater.from(this).inflate(R.layout.view_weibo_detail, main_layout, false);
//		new TopBarHelper(this, UserView).setTitle("新浪微博").setOnClickLisener(new OnClickLisener()
//		{
//
//		    @Override
//		    public void setRightOnClick()
//		    {
//			new ActivityHelper().goHomeActivity(WBAuthActivity.this);
//		    }
//
//		    @Override
//		    public void setLeftOnClick()
//		    {
//			finish();
//		    }
//
//		    @Override
//		    public void setExtraOnclick()
//		    {
//		    }
//		});
//		main_layout.addView(UserView);
//	    }
//	} catch (Exception e)
//	{
//	}
//	setUserDetail();
//    }
//
//    private void setUserDetail()
//    {
//	((TextView) UserView.findViewById(R.id.name)).setText(JsonTools.getString(jso, "screen_name")[0]);
//	((TextView) UserView.findViewById(R.id.description)).setHint("简介:" + JsonTools.getString(jso, "description")[0]);
//	((TextView) UserView.findViewById(R.id.weibo_num)).setText(JsonTools.getString(jso, "statuses_count")[0]);
//	((TextView) UserView.findViewById(R.id.guanzhu_num)).setText(JsonTools.getString(jso, "friends_count")[0]);
//	((TextView) UserView.findViewById(R.id.fensi_num)).setText(JsonTools.getString(jso, "followers_count")[0]);
//	new Thread(new Runnable()
//	{
//	    public void run()
//	    {
//		try
//		{
//		    InputStream is = new java.net.URL(JsonTools.getString(jso, "avatar_large")[0]).openStream();
//		    Bitmap bitmap = BitmapFactory.decodeStream(is);
//		    is.close();
//		    tx = bitmap;
//		    handler.sendMessage(new Message());
//		} catch (Exception e)
//		{
//		}
//	    }
//	}).start();
//    }
//
//    Handler handler = new Handler()
//    {
//	@SuppressWarnings("deprecation")
//	@Override
//	public void handleMessage(android.os.Message msg)
//	{
//	    ((ImageView) UserView.findViewById(R.id.tx_img)).setBackground(new BitmapDrawable(tx).getCurrent());
//	    ((ImageView) UserView.findViewById(R.id.weibo_tx)).setBackground(new BitmapDrawable(tx).getCurrent());
//	    ((TextView) UserView.findViewById(R.id.weibo_name)).setText(JsonTools.getString(jso, "screen_name")[0]);
//	    initAction();
//	    try
//	    {
//		((TextView) UserView.findViewById(R.id.weibo_content)).setText("\t" + "\t" + JsonTools.getString(jso.getJSONObject("status"), "text")[0]);
//		((TextView) UserView.findViewById(R.id.weibo_time)).setText(new SimpleDateFormat("yyyy/MM/dd     HH:mm:ss").format(new Date(JsonTools.getString(jso.getJSONObject("status"), "created_at")[0])));
//	    } catch (Exception e)
//	    {
//	    }
//	};
//    };
//
//    protected void initAction()
//    {
//	UserView.findViewById(R.id.goto_weibo).setOnClickListener(new OnClickListener()
//	{
//	    @Override
//	    public void onClick(View v)
//	    {
//		// List<PackageInfo> apps = new
//		// AllAPPListHelper(WBAuthActivity.this).getAllAPPList();
//		// Intent intent = new Intent(Intent.ACTION_MAIN);
//		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
//		// intent.setPackage("com.sina.weibog3");
//		// startActivity(intent);
//		doStartApplicationWithPackageName("com.sina.weibog3");
//	    }
//	});
//    }
//
//    private void doStartApplicationWithPackageName(String packagename)
//    {
//
//	// 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
//	PackageInfo packageinfo = null;
//	try
//	{
//	    packageinfo = getPackageManager().getPackageInfo(packagename, 0);
//	} catch (NameNotFoundException e)
//	{
//	    e.printStackTrace();
//	}
//	if (packageinfo == null)
//	{
//	    return;
//	}
//
//	// 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
//	Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
//	resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//	resolveIntent.setPackage(packageinfo.packageName);
//
//	// 通过getPackageManager()的queryIntentActivities方法遍历
//	List<ResolveInfo> resolveinfoList = getPackageManager().queryIntentActivities(resolveIntent, 0);
//
//	ResolveInfo resolveinfo = resolveinfoList.iterator().next();
//	if (resolveinfo != null)
//	{
//	    // packagename = 参数packname
//	    String packageName = resolveinfo.activityInfo.packageName;
//	    // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
//	    String className = resolveinfo.activityInfo.name;
//	    // LAUNCHER Intent
//	    Intent intent = new Intent(Intent.ACTION_MAIN);
//	    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//	    // 设置ComponentName参数1:packagename参数2:MainActivity路径
//	    ComponentName cn = new ComponentName(packageName, className);
//
//	    intent.setComponent(cn);
//	    startActivity(intent);
//	}
//    }
//    // public AppInfo getWeiBo(List<PackageInfo> apps)
//    // {
//    // for(int i = 0;i< )
//    // }
}