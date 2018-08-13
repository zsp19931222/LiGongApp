package yh.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.model.ZiZhuMuBiaoModel;

public class ZhiZhuMuBiaoAdapter extends BaseAdapter {
	List<ZiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list;
	Context context;
    LayoutInflater inflater;
	
	public ZhiZhuMuBiaoAdapter(Context context, List<ZiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list) {
      this.context=context;
      this.list=list;
      this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder=null;
		if(arg1==null){
			viewHolder=new ViewHolder();
			arg1=inflater.inflate(R.layout.iterm_zhizhumubiao, arg2,false);
			viewHolder.txt_riwu=(TextView) arg1.findViewById(R.id.txt_riwu);
			viewHolder.txt_zhuangtai=(TextView) arg1.findViewById(R.id.txt_zhuangtai);
			arg1.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) arg1.getTag();
		}
		
		viewHolder.txt_riwu.setText(list.get(arg0).getT_name());
		
		viewHolder.txt_zhuangtai.setText(list.get(arg0).getStatus_name());
		if (list.get(arg0).getStatus().equals("0")) {
			viewHolder.txt_zhuangtai.setTextColor(context.getResources().getColor(R.color.button));
		}
		return arg1;
	}
	private class ViewHolder{
		TextView txt_riwu,txt_zhuangtai;
	}

}
