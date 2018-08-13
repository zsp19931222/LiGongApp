package yh.app.mymessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyPush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;import com.yhkj.cqgyxy.R;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.ImageAt;
import yh.tool.widget.DragPointView;
import yh.tool.widget.DragPointView.OnDragListencer;
import 云华.智慧校园.工具.ViewTools;

public class SchoolMessageHome extends ActivityPortrait implements OnDragListencer
{
	private LinearLayout main_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.school_nessage_home);
		new DefaultTopBar(this).doit("学校通知");
		main_layout = (LinearLayout) findViewById(R.id.main_layout);
		addItem(new SqliteHelper().rawQuery("select t2.[functionid] as function_id,t2.face,t2.[name],(case when t1.n_message is null or t1.n_message='' then '暂无数据' else t1.n_message end) as message,(select count(*) from client_notice t3 where t3.function_id=t1.[function_id] and t3.userid=t1.[userid] and t3.read=?) as num from button as t2 LEFT  outer join client_notice_newest as t1 on   t1.function_id=t2.FunctionID   where t2.[function_tybj]=? and t2.[userid]=?", BodyPush.READ_NO, "1", Constants.number));
	}

	public void addItem(List<Map<String, String>> list)
	{
		main_layout.removeAllViews();
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				String wdxxString = list.get(i).get("num");
				View v = ViewTools.getView(this, R.layout.push_type_item, main_layout);
				setImg(list.get(i).get("face"), v.findViewById(R.id.img), list.get(i).get("name"));
				main_layout.addView(v);
				((TextView) v.findViewById(R.id.text)).setText(list.get(i).get("name"));
				v.setTag(list.get(i).get("function_id"));
				((TextView) v.findViewById(R.id.date)).setHint("");
				DragPointView wdxx = ((DragPointView) v.findViewById(R.id.xxjsq));
				if (wdxxString != null && !wdxxString.equals("0"))
				{
					((TextView) v.findViewById(R.id.message)).setText(list.get(i).get("message"));
					wdxx.setText(wdxxString);
					wdxx.setVisibility(View.VISIBLE);
				} else
				{
					((TextView) v.findViewById(R.id.message)).setHint(list.get(i).get("message"));
					wdxx.setVisibility(View.INVISIBLE);
				}
				wdxx.setTag(list.get(i).get("function_id"));
				wdxx.setDragListencer(this);
				v.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent();
						intent.setAction("yh.app.mymessage.Message");
						intent.setPackage(SchoolMessageHome.this.getPackageName());
						intent.putExtra("function_id", v.getTag().toString());
						SchoolMessageHome.this.startActivity(intent);
					}
				});
			}
		}
	}

	private void setImg(String url, View view, String name)
	{
		ImageAt at = new ImageAt(url, (ImageView) view.findViewById(R.id.img), this, name, R.drawable.default_function_icon);
		at.executeOnExecutor(Executors.newCachedThreadPool());
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		addItem(new SqliteHelper().rawQuery("select t2.[functionid] as function_id,t2.face,t2.[name],(case when t1.n_message is null or t1.n_message='' then '暂无数据' else t1.n_message end) as message,(select count(*) from client_notice t3 where t3.function_id=t1.[function_id] and t3.userid=t1.[userid] and t3.read=?) as num from button as t2 LEFT  outer join client_notice_newest as t1 on   t1.function_id=t2.FunctionID   where t2.[function_tybj]=? and t2.[userid]=?", BodyPush.READ_NO, "1", Constants.number));
	}

	@Override
	public void onDragOut(View view)
	{
		if (view.getTag() == null || view.getTag().toString().isEmpty())
		{
			return;
		}
		ExampleApplication.getInstance().getSqliteHelper().execSQL("update client_notice set read='true' where function_id=? and userid=?", view.getTag().toString(), Constants.number);
		ExampleApplication.getInstance().getSqliteHelper().execSQL("update client_notice_messagelist set read='true' where function_id=? and userid=?", view.getTag().toString(), Constants.number);
	}
}
