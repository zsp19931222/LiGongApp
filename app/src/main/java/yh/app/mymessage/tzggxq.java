package yh.app.mymessage;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;import com.yhkj.cqgyxy.R;
import yh.app.tool.ToastShow;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.NetworkError;
import com.example.jpushdemo.helper.Receiver;
import com.example.jpushdemo.helper.Receiver.IGetMessage;
import com.yunhuakeji.app.utils.DefaultTopBar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class tzggxq extends ActivityPortrait
{
	private TextView text, date, title;
	private String id, ticket, code;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tzgg_xqzs);
		
		initView();

		initIntentData();
	}

	private void initIntentData()
	{
		id 		= getIntent().getStringExtra("id");
		ticket 	= getIntent().getStringExtra("ticket");
		code 	= getIntent().getStringExtra("code");
		
		new DefaultTopBar(this).doit("消息详情");
		
		doMessage();
	}

	private void initView()
	{
		title 	= (TextView) findViewById(R.id.tzgg_xqzs_title);
		text 	= (TextView) findViewById(R.id.tzgg_xqzs_text);
		date 	= (TextView) findViewById(R.id.tzgg_xqzs_date);
	}

	
	private void doMessage()
	{
		if (IsNull.isNotNull(id))
		{
			if (checkLocalMessage())
			{
				title	.setText(result.get(0).get("n_title"));
				text	.setText(result.get(0).get("n_message"));
				date	.setText(result.get(0).get("n_send_time"));
			} else
			{
				String json = String.format("{\"id\":%s,\"code\":%s,\"ticket\":%s,\"userid\":%s}", id, code, ticket, Constants.number);
				new Receiver().getMessageByID(this, com.yunhuakeji.app.utils.JsonTools.createJsonObject(json), new IGetMessage()
				{
					@Override
					public void callBack(JSONObject data)
					{
						String [] datas = JsonTools.getString(data, "", new String[] { "n_title", "n_message", "n_send_time "});
						title	.setText(datas[0]);
						text	.setText(datas[1]);
						date	.setText(datas[2]);
					}
				});
			}
		} else
		{
			new ToastShow().show(this, "");
		}
	}
	
	private List<Map<String, String>> result;
	/**
	 * 判断本地是否有该条消息
	 * 
	 * @param id
	 * @return
	 */
	private boolean checkLocalMessage()
	{
		if (IsNull.isNotNull(id))
		{
			result = new SqliteHelper().rawQuery("select * from client_notice where userid=? and n_id=?", Constants.number, id);
			if (result != null && result.size() > 0)
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			new ToastShow().show(this, "消息内部错误");
			return false;
		}
	}
}
