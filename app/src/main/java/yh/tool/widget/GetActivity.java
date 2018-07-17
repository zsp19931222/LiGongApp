package yh.tool.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * 包 名:yh.tool.widget 类 名:GetActivity.java 功 能:获取Activity的Action
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class GetActivity
{
	public List<Button> GetButtonByKey(List<String> keys, Context context)
	{
		final List<activitys> aclist = new ArrayList<activitys>();
		activitys ac = new activitys();
		final Context ctxt = context;
		ac.setClassName("yh.app.coursetable.coureActivity");
		ac.setPram(getpram());
		ac.setPackageName("yh.app.coursetable");
		aclist.add(ac);

		activitys ac1 = new activitys();
		ac1.setClassName("yh.app.score.Stu_Grade_Query_Activity");
		ac1.setPram(getpram());
		ac1.setPackageName("yh.app.score");
		aclist.add(ac1);

		activitys ac2 = new activitys();
		ac2.setClassName("yh.app.library.LibraryActivity");
		ac2.setPram(getpram());
		ac2.setPackageName("yh.app.library");
		aclist.add(ac2);

		activitys ac3 = new activitys();
		ac3.setClassName("yh.app.lostFound.LostFoundActivity");
		ac3.setPram(getpram());
		ac3.setPackageName("yh.app.lostFound");
		aclist.add(ac3);

		List<Button> btnlist = new ArrayList<Button>();
		for (int i = 0; i < aclist.size(); i++)
		{
			final int a = i;
			// 生成按钮
			Button bt1 = new Button(ctxt);
			// 设置模块名
			bt1.setText(aclist.get(i).getTitle());
			// 设置点击事件
			bt1.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					final String cls = aclist.get(a).getClassName();
					final String pag = aclist.get(a).getPackageName();
					Intent intent = new Intent();
					intent.setAction(cls);
					intent.setPackage(pag);
					ctxt.startActivity(intent);
				}
			});
			btnlist.add(bt1);
		}
		return btnlist;
	}

	public List<keyValue> getpram()
	{
		List<keyValue> kvlist = new ArrayList<keyValue>();
		keyValue kv1 = new keyValue();
		kv1.setKey("view1Activity");
		kv1.setValue("1");
		kvlist.add(kv1);
		return kvlist;
	}
}
