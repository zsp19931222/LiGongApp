package yh.app.yikatong;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;import com.yhkj.cqgyxy.R;
/**
 * 
 * 包	名:yh.app.yikatong
 * 类	名:mingxiAdapter.java
 * 功	能:一卡通消费明细适配器
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class mingxiAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<Map<String, String>> allmingxi;

	public mingxiAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public mingxiAdapter(Context context, List<Map<String, String>> all) {
		this.inflater = LayoutInflater.from(context);
		this.allmingxi = all;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (allmingxi != null) {
			return allmingxi.size();// ���ص�ǰ���ѧ�����еĿ�Ŀ��Ŀ
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
		mingxiHolder holder;
		final int p = arg0;
		if (arg1 == null) {
			holder = new mingxiHolder();
			arg1 = inflater.inflate(R.layout.yikatong_mingxi,
					null);
			holder.time=(TextView)arg1.findViewById(R.id.time);
			holder.moneny=(TextView)arg1.findViewById(R.id.money);
			holder.type=(TextView)arg1.findViewById(R.id.type);
			holder.place=(TextView)arg1.findViewById(R.id.place);
			arg1.setTag(holder);
		} else {
			holder = (mingxiHolder) arg1.getTag();
		}
		
		holder.time.setText(allmingxi.get(arg0).get("time").toString());
		holder.moneny.setText(allmingxi.get(arg0).get("moneny").toString());
		holder.type.setText(allmingxi.get(arg0).get("type").toString());
		holder.place.setText(allmingxi.get(arg0).get("place").toString());
		
		return arg1;
	}
	
	class mingxiHolder
	{
		TextView time;
		TextView moneny;
		TextView type;
		TextView place;
	}

}
