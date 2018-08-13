package yh.app.quanzi.tjhy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import yh.app.activitytool.ActivityPortrait;
import yh.app.contacts.UserDetail;import com.yhkj.cqgyxy.R;
import yh.app.tool.URLHelper;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.功能._二维码;
import 云华.智慧校园.工具.IsNull;
/**
 * 搜索好友界面
 * @author 云华科技
 * @date 2017年5月11日
 */
public class Quanzi_UI_tjhy extends ActivityPortrait
{
	private Quanzi_tools_添加好友 tools;
	private Quanzi_view_sshy_list sshy_list;
	private EditText input;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sshy);
		initTopBar();
		initView();
		initAction();
	}

	private void initAction()
	{
		((EditText) findViewById(R.id.input)).addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				tools = new Quanzi_tools_添加好友();
				sshy_list = new Quanzi_view_sshy_list(Quanzi_UI_tjhy.this, R.id.parent, list, tools, s.toString());
				sshy_list.doit();
			}
		});
		// ss.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v)
		// {
		// if (input.getText().toString() != null &&
		// input.getText().toString().length() > 0)
		// {
		// tools = new Quanzi_tools_添加好友();
		// sshy_list = new Quanzi_view_sshy_list(Quanzi_UI_tjhy.this,
		// R.id.parent, list, tools, input.getText().toString());
		// sshy_list.doit();
		// }
		// }
		// });
	}

	private void initTopBar()
	{
		new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle("搜索好友").setExtra(R.drawable.ewm_64, true).setOnClickLisener(new OnClickLisener()
		{

			@Override
			public void setRightOnClick()
			{
//				Intent intent = new Intent();
//				intent.setAction("com.example.app3.HomePageActivity");
//				intent.setPackage(Quanzi_UI_tjhy.this.getPackageName());
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				Quanzi_UI_tjhy.this.startActivity(intent);
				((Activity) Quanzi_UI_tjhy.this).finish();
			}

			@Override
			public void setLeftOnClick()
			{
				finish();
			}

			@Override
			public void setExtraOnclick()
			{
				new _二维码().scan(Quanzi_UI_tjhy.this);
			}
		});
	}

	private void initView()
	{
		input = (EditText) findViewById(R.id.input);
		list = (ListView) findViewById(R.id.sshy_listview);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		try
		{
			// 获取二维码内容
			String url = data.getExtras().getString("result");
			if (IsNull.isNotNull(url))
			{
				// 提取二维码中的用户编号
				url = new URLHelper().getParames(url, "userid");
				if (IsNull.isNotNull(url))
				{
					Intent intent = new Intent(this, UserDetail.class);
					intent.putExtra("hybh", url);
					this.startActivity(intent);
					finish();
				} else
				{
					input.setText(url);
					tools = new Quanzi_tools_添加好友();
					sshy_list = new Quanzi_view_sshy_list(Quanzi_UI_tjhy.this, R.id.parent, list, tools, url);
					sshy_list.doit();
				}
			}
		} catch (Exception e)
		{
		}
	}
}