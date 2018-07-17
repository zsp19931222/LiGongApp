package yh.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import yh.app.model.MuBiaoContent;
import yh.app.model.MuBiaoContent.ContentBean.MessageBean.RowsBean;

public class MuBiaoKuContentAdapter extends BaseAdapter {
	private Context context;
	private List<MuBiaoContent.ContentBean.MessageBean.RowsBean> list;
	private LayoutInflater inflater;

	public MuBiaoKuContentAdapter(Context context, List<MuBiaoContent.ContentBean.MessageBean.RowsBean> list) {
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
		ViewHolder viewHolder=null;
		if (arg1==null) {
			viewHolder=new ViewHolder();
			arg1=inflater.inflate(R.layout.item_mubiaokucontent_listview, arg2,false);
			viewHolder.txt_mubiaoku_name=(TextView) arg1.findViewById(R.id.txt_mubiaoku_name);
			viewHolder.txt_mubiaoku_state=(TextView) arg1.findViewById(R.id.txt_mubiaoku_state);
			arg1.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) arg1.getTag();
		}
		RowsBean mlist=list.get(arg0);
		if (mlist.getTarget_operate().equals(Constants.target_operate)) {
			viewHolder.txt_mubiaoku_state.setTextColor(context.getResources().getColor(R.color.button));
		}
		viewHolder.txt_mubiaoku_name.setText(mlist.getTarget_name());
		viewHolder.txt_mubiaoku_state.setText(mlist.getStatus());
		return arg1;
	}

	class ViewHolder {
		TextView txt_mubiaoku_state, txt_mubiaoku_name;
	}

}
