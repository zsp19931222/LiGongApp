package yh.app.score;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import yh.app.appstart.lg.R;
/**
 * 
 * 包 名:yh.app.score 类 名:Stu_Grade_Adapter.java 功 能:对从网络获取的课程信息进行解析
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class Stu_Grade_Adapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private List<Map<String, Object>> allGrades;//

	public Stu_Grade_Adapter(Context context)
	{
		inflater = LayoutInflater.from(context);
	}

	public Stu_Grade_Adapter(Context context, List<Map<String, Object>> all)
	{
		this.inflater = LayoutInflater.from(context);
		this.allGrades = all;
	}

	@Override
	public int getCount()
	{
		return allGrades.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		StuGradeViewHolder holder;
		if (convertView == null)
		{
			holder = new StuGradeViewHolder();
			convertView = inflater.inflate(R.layout.stu_grade_query_list_item, null);
			holder.setCourse_name((TextView) convertView.findViewById(R.id.course_name));
			holder.setCourse_grade((TextView) convertView.findViewById(R.id.course_grade));
			holder.setCourse_xf((TextView) convertView.findViewById(R.id.course_xf));
			convertView.setTag(holder);
		} else
		{
			holder = (StuGradeViewHolder) convertView.getTag();
		}
		holder.getCourse_grade().setText(allGrades.get(position).get("c_grade").toString());
		try
		{
			if (Float.valueOf(allGrades.get(position).get("c_grade").toString()) < 60)
				holder.getCourse_grade().setTextColor(Color.RED);
		} catch (Exception e)
		{
		}
		holder.getCourse_xf().setText(allGrades.get(position).get("c_xuefen").toString());
		if (allGrades.get(position).get("c_name").toString().length() > 20)
		{
			holder.getCourse_name().setText(allGrades.get(position).get("c_name").toString().substring(0, 15) + "...");
		} else
		{
			holder.getCourse_name().setText(allGrades.get(position).get("c_name").toString());
		}
		return convertView;
	}

}
