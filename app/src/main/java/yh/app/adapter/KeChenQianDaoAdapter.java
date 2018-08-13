package yh.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.model.KeChenQianDaoModel;

public class KeChenQianDaoAdapter extends BaseAdapter {
	private Context context;
	private List<KeChenQianDaoModel.ContentBean.MessageBean.DataBean> list;
	private LayoutInflater inflater;
	private View.OnClickListener onQianDao;

	public KeChenQianDaoAdapter(Context context, List<KeChenQianDaoModel.ContentBean.MessageBean.DataBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public void setOnQianDao(View.OnClickListener onQianDao) {
		this.onQianDao = onQianDao;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list.size()>0) {
			return list.size();
		}else{
			return 0;
		}
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (arg1 == null) {
			holder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.yh_home_course_activity_item, null, false);
			holder.txt_kechennum = (TextView) arg1.findViewById(R.id.txt_kechennum);
			holder.txt_kechentime = (TextView) arg1.findViewById(R.id.txt_kechentime);
			holder.txt_kemu = (TextView) arg1.findViewById(R.id.txt_kemu);
			holder.btn_kcqd = (Button) arg1.findViewById(R.id.btn_kcqd);
			
			arg1.setTag(holder);
		} else {
			holder = (ViewHolder) arg1.getTag();
		}
		holder.btn_kcqd.setOnClickListener(onQianDao);
		holder.btn_kcqd.setTag(arg0);
		holder.txt_kechennum.setText(list.get(arg0).getKs());// 课数
		holder.txt_kechentime.setText(list.get(arg0).getTime());// 签到时间
		holder.txt_kemu.setText(list.get(arg0).getKcmc());// 可目
		holder.btn_kcqd.setText(list.get(arg0).getStatus());//签到状态
		holder.btn_kcqd.setTextColor(context.getResources().getColor(R.color.color_gray));
		return arg1;
	}

	private class ViewHolder {
		TextView txt_kechennum, txt_kemu, txt_kechentime;
		Button btn_kcqd;
	}

}
