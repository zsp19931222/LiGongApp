package yh.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.model.MuBiaoKuModel;

public class MuBiaoKuTitleListAdapter extends BaseAdapter {
	private List<MuBiaoKuModel.ContentBean.MessageBean> list;
	private Context context;
	private LayoutInflater inflater;
	private int selectItem = 0;// 用于点击改变字体颜色

	public MuBiaoKuTitleListAdapter(Context context, List<MuBiaoKuModel.ContentBean.MessageBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		ViewHolder viewHolder = null;
		if (arg1 == null) {
			viewHolder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.item_mubiaoku_title, arg2, false);
			viewHolder.txt_mubiaoku_title = (TextView) arg1.findViewById(R.id.txt_mubiaoku_title);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		if (arg0 == selectItem) {
			viewHolder.txt_mubiaoku_title.setTextColor(context.getResources().getColor(R.color.button));
		}
		viewHolder.txt_mubiaoku_title.setText(list.get(arg0).getName());
		return arg1;
	}

	class ViewHolder {
		private TextView txt_mubiaoku_title;
	}

	/**
	 * 得到点击项id
	 * 
	 * @param selectItem
	 */
	public void setChengeTextColor(int selectItem) {
		this.selectItem = selectItem;
	}

}
