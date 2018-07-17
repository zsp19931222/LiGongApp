package yh.app.yikatong;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;import yh.app.appstart.lg.R;
/**
 * 
 * 包	名:yh.app.yikatong
 * 类	名:zongjiAdapter.java
 * 功	能:一卡通消费总计
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class zongjiAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Map<String, String>> allzongji;
	
	public zongjiAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public zongjiAdapter(Context context, List<Map<String, String>> all) {
		this.inflater = LayoutInflater.from(context);
		this.allzongji = all;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (allzongji != null) {
			return allzongji.size();// ���ص�ǰ���ѧ�����еĿ�Ŀ��Ŀ
		} else {
			return 0; // �����־����Ϊ���򷵻�0
		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		zongjiHolder holder;
		final int p = arg0;
		if (arg1 == null) {
			holder = new zongjiHolder();
			arg1 = inflater.inflate(R.layout.yikatong_zongji,
					null);
			holder.text1=(TextView)arg1.findViewById(R.id.text1);
			holder.text2=(TextView)arg1.findViewById(R.id.text2);
			arg1.setTag(holder);
		} else {
			holder = (zongjiHolder) arg1.getTag();
		}
		
		holder.text1.setText(allzongji.get(arg0).get("text1").toString());
		holder.text2.setText(allzongji.get(arg0).get("text2").toString());
		
		return arg1;
	}
	class zongjiHolder
	{
		TextView text1;
		TextView text2;
	}
}
