package yh.app.quanzi;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.quanzitool.InitMrfz;import com.yhkj.cqgyxy.R;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DefaultTopBar;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.DefaultPost;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具._链接地址导航.DC;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

public class mrfz extends ActivityPortrait
{
	private LinearLayout layout;
	private Context mContext;
	 private LoadDiaog diaog;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sq_mrfz);
		initView();
		
		new DefaultPost().doPost(DC.圈子默认列表.getUrl(), getFunctionID(), handler);
	
	}

	private void initView()
	{
		layout = (LinearLayout) findViewById(R.id.quanzi_mrfz_layout);
		mContext = this;
		diaog=new LoadDiaog(this);
		diaog.show();
		findViewById(R.id.mrfz).setVisibility(View.GONE);
		new DefaultTopBar(this).doit("默认分组");
	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				if (!new JSONObject(msg.getData().getString("msg")).getBoolean("boolean"))
					return;
				
				saveMRFZ(new JSONObject(msg.getData().getString("msg")));
				
				new InitMrfz(layout, mContext).doInit("mrfz");//查询表名
				if (diaog.isShowing()) {
					diaog.dismiss();
				}
			} catch (Exception e)
			{
			}
		}

	};
	private void saveMRFZ(JSONObject jso)
	{
		try
		{
			new SqliteHelper().execSQL("delete from mrfz");
			JSONArray jsa = jso.getJSONArray("message");
			for (int i = 0; i < jsa.length(); i++)
			{
				new SqliteHelper().execSQL("replace into mrfz(FRIEND_ID,name,userid,handimg,szm) values(?,?,?,?,?) ", JsonTools.getString(jsa.getJSONObject(i), new String[]
				{
						"HYBH", "YHBZ"
				})[0], 
				JsonTools.getString(jsa.getJSONObject(i), new String[]
				{
						"HYBH", "YHBZ",
				})[1],
				Constants.number,//这里必须存当前账号不然默认列表查询不全
				JsonTools.getString(jsa.getJSONObject(i), new String[]
						{
								"FACEADDRESS"
						})[0],
				pinyin.getSpells(JsonTools.getString(jsa.getJSONObject(i), new String[]
				{
						"YHBZ"
				})[0]).substring(0, 1));
				
			}
		} catch (Exception e)
		{
		}
	}

	public void close(View v)
	{
		finish();
	}
}
