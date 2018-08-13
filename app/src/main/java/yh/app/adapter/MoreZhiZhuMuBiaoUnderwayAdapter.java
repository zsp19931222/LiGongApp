package yh.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.model.MoreZhiZhuMuBiaoModel;

/**
 * 查看更多自主目标 任务进行中
 * 
 * @author 云华科技
 * @date 2017年4月28日
 */
public class MoreZhiZhuMuBiaoUnderwayAdapter extends BaseAdapter {
	private Context context;
	private List<MoreZhiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list;
	private LayoutInflater inflater;

	public MoreZhiZhuMuBiaoUnderwayAdapter(Context context,
			List<MoreZhiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.inflater = inflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.iterm_zhizhumubiao, parent, false);
			viewHolder.txt_riwu = (TextView) convertView.findViewById(R.id.txt_riwu);
			viewHolder.txt_zhuangtai = (TextView) convertView.findViewById(R.id.txt_zhuangtai);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txt_riwu.setText(list.get(position).getT_name());

		viewHolder.txt_zhuangtai.setText(list.get(position).getStatus_name());
		
		return convertView;
	}

	private class ViewHolder {
		TextView txt_riwu, txt_zhuangtai;

	}

}
