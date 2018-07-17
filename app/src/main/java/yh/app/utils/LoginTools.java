package yh.app.utils;

import java.net.URLDecoder;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.Encoded;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.RSAEncrypt;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

@SuppressWarnings("unused")
public class LoginTools
{
	private Context context;

	public Map<String, String> map = CodeList();

	public LoginTools(Context context)
	{
		this.context = context;
	}

	public LoginTools()
	{
	}

	public void doLogin(Map<String, String> parames, final Handler handler)
	{
		new ThreadPoolManage().addPostTask(_链接地址导航.UIA.登录.getUrl(), parames, new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				int what = 0;
				try
				{
					what = dealData(new JSONArray(msg.getData().getString("msg")));
				} catch (Exception e)
				{
					what = 2;
				}
				new HandlerHelper().sendWhat(handler, what);
			}
		});
	}

	private int dealData(JSONArray jsa)
	{
		int what = 0;
		try
		{
			if (jsa.length() > 0 && jsa.getJSONObject(0).getBoolean("islogin"))
			{
				what = 1;
				save(jsa);
			} else
			{
				what = 0;
			}
		} catch (Exception e)
		{
			what = 2;
		}
		return what;
	}

	private void save(JSONArray jsa)
	{
		Object[] array = null;
		try
		{
			String[] zdmc = new String[]
			{
					"USERNAME", "DH", "QQ", "TXDZ", "NC", "ZYMC", "XBMC", "BMMC", "BJDM", "SR"
			};
			String result = "";
			for (int i = 0; i < jsa.getJSONObject(0).getJSONArray("userinfo").length(); i++)
			{
				String code = jsa.getJSONObject(0).getJSONArray("userinfo").get(i).toString();
				String en = new String(RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(code.getBytes("utf-8"), Base64.DEFAULT)));
				result += en.trim();
			}
			result = URLDecoder.decode(result, "UTF-8");
			array = JsonTools.getString(new JSONObject(result), zdmc);
			for (int i = 0; i < array.length; i++)
			{
				array[i] = Encoded.unicodeToUtf8(array[i].toString());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			new SqliteHelper().execSQL("delete from user");
			new SqliteHelper().execSQL("insert into user(userid,name,telphone,qq,faceaddress,nc,zy,sex,bm,bj,birthday) values('" + Constants.number + "',?,?,?,?,?,?,?,?,?,?)", array);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			new SqliteHelper().execSQL(String.format("insert into usertype(userid,usertype) values('%s',%s)", new Object[]
			{
					Constants.number, jsa.getJSONObject(0).getString("usertype")
			}));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private Map<String, String> CodeList()
	{
		return MapTools.buildMap(new String[][]
		{
				{
						"2", "网络异常"
				},
				{
						"0", "账号或密码错误"
				},
				{
						"1", "登录成功"
				}
		});
	}
}