package yh.app.quanzi;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;import com.yhkj.cqgyxy.R;
import yh.app.utils.DefaultTopBar;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.ViewTools;

public class MyClass extends ActivityPortrait implements View.OnClickListener
{
	private LinearLayout layout;
	private LoadDiaog dialg;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_myclass);
		dialg=new LoadDiaog(this);
		dialg.show();
		new DefaultTopBar(this).doit("我的班级");
		layout = (LinearLayout) findViewById(R.id.layout);
		try
		{
			List<Map<String, String>> result = new SqliteHelper().rawQuery("select * from classlist where userid=?", Constants.number);
			for (int i = 0; i < result.size(); i++)
			{
				View view = ViewTools.getView(MyClass.this, R.layout.contacts_myclass_item, layout);
				((TextView) view.findViewById(R.id.name)).setText(result.get(i).get("classname"));
				view.setTag(result.get(i).get("id"));
				((TextView) view.findViewById(R.id.count)).setHint("人数: " + result.get(i).get("count"));
				view.setOnClickListener(MyClass.this);
				layout.addView(view);
				if (dialg.isShowing()) {
					dialg.dismiss();
				}
			}
		} catch (Exception e)
		{
		}
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		intent.setAction(yh.app.mymessage.Message.class.getName());
		intent.setPackage(getPackageName());
		intent.putExtra("function_id", v.getTag().toString());
		startActivity(intent);
	}
}
