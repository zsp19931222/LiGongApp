package yh.app.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import com.yunhuakeji.app.MainActivityDown;

import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具._功能跳转;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FunctionList implements View.OnClickListener
{
	private Context context;
	private LinearLayout layout;

	public static ImageView sq;

	public FunctionList(Context context, LinearLayout layout)
	{
		this.layout = layout;
		this.context = context;
		getFunctionList();
	}

	private List<Map<String, String>> functionList;

	public void getFunctionList()
	{
		functionList = new SqliteHelper().rawQuery("select FunctionID,name,cls,pkg,face,key,function_tybj,type from button where function_tybj='0' order by px");
	}

	public void doit()
	{

		int num = 0;
		while (num < functionList.size())
		{
			if (functionList.get(num).get("function_tybj").equals("1"))
			{
				num++;
				continue;
			}
			LayoutInflater inflater = LayoutInflater.from(context);
			View mView = inflater.inflate(R.layout.home_fucntion_layout, layout, false);
			ImageView image1 = (ImageView) mView.findViewById(R.id.home_function_img1);
			TextView text1 = (TextView) mView.findViewById(R.id.home_function_text1);
			ImageView image2 = (ImageView) mView.findViewById(R.id.home_function_img2);
			TextView text2 = (TextView) mView.findViewById(R.id.home_function_text2);
			ImageView image3 = (ImageView) mView.findViewById(R.id.home_function_img3);
			TextView text3 = (TextView) mView.findViewById(R.id.home_function_text3);
			ImageView image4 = (ImageView) mView.findViewById(R.id.home_function_img4);
			TextView text4 = (TextView) mView.findViewById(R.id.home_function_text4);
			for (int i = 1; i < 5; i++)
			{
				try
				{
					switch (i)
					{
					case 1:
						ImageAt at1 = new ImageAt(functionList.get(num).get("face"), image1, context, functionList.get(num).get("name"), R.drawable.default_function_icon);
						at1.executeOnExecutor(Executors.newCachedThreadPool());
						image1.setVisibility(View.VISIBLE);
						image1.setTag(num);
						image1.setOnClickListener(this);
						text1.setText(functionList.get(num).get("name"));
						setxyd(functionList.get(num).get("name"), (ImageView) mView.findViewById(R.id.wdtsbj1));
						break;
					case 2:
						ImageAt at2 = new ImageAt(functionList.get(num).get("face"), image2, context, functionList.get(num).get("name"), R.drawable.default_function_icon);
						at2.executeOnExecutor(Executors.newCachedThreadPool());
						image1.setVisibility(View.VISIBLE);
						image2.setTag(num);
						image2.setOnClickListener(this);
						text2.setText(functionList.get(num).get("name"));
						setxyd(functionList.get(num).get("name"), (ImageView) mView.findViewById(R.id.wdtsbj2));
						break;
					case 3:
						ImageAt at3 = new ImageAt(functionList.get(num).get("face"), image3, context, functionList.get(num).get("name"), R.drawable.default_function_icon);
						at3.executeOnExecutor(Executors.newCachedThreadPool());
						image1.setVisibility(View.VISIBLE);
						image3.setTag(num);
						image3.setOnClickListener(this);
						text3.setText(functionList.get(num).get("name"));
						setxyd(functionList.get(num).get("name"), (ImageView) mView.findViewById(R.id.wdtsbj3));
						break;
					case 4:
						ImageAt at4 = new ImageAt(functionList.get(num).get("face"), image4, context, functionList.get(num).get("name"), R.drawable.default_function_icon);
						at4.executeOnExecutor(Executors.newCachedThreadPool());
						image1.setVisibility(View.VISIBLE);
						image4.setTag(num);
						image4.setOnClickListener(this);
						text4.setText(functionList.get(num).get("name"));
						setxyd(functionList.get(num).get("name"), (ImageView) mView.findViewById(R.id.wdtsbj4));
						break;
					default:
						break;
					}
				} catch (Exception e)
				{
				}
				num++;
				if (num >= functionList.size())
					break;
			}
			layout.addView(mView);
		}
	}

	public void setxyd(String name, ImageView wdtsbj)
	{
		if (name.equals("社区"))
		{
			sq = wdtsbj;
			String count = new SqliteHelper().rawQuery("select count(*) num from lt where userid=? and isread='false'", new String[]
			{
					Constants.number
			}).get(0).get("num");
			if (!count.equals("0"))
			{
				wdtsbj.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onClick(View v)
	{
		try
		{
			Intent intent = new Intent(context, MainActivityDown.class);
			context.startActivity(intent);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
//		try
//		{
//			new _功能跳转().Jump(context, functionList.get(Integer.valueOf(v.getTag().toString())));
//		} catch (Exception e)
//		{
//			Toast.makeText(context, "功能异常,请重试", Toast.LENGTH_SHORT).show();
//		}
	}
}
