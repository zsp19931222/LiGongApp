package yh.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import java.util.List;

import yh.app.model.YXXCModel;

/**
 * 入学准备 适配器
 */

public class YXXCAdapter extends BaseAdapter {
	private Context context;
	private List<YXXCModel.ContentBean> list;
	private LayoutInflater inflater;

	public YXXCAdapter(Context context, List<YXXCModel.ContentBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return list.size();

	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_welcomestudent_ready, parent, false);
			initView(viewHolder, convertView);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		GlideLoadUtils.getInstance().glideLoad(context,list.get(position).getIcon_url(),viewHolder.imgWelcomestudentItemReady,R.drawable.ico_load_little);
		viewHolder.txtWelcomestudentItemReady.setText(list.get(position).getHjmc());
		
		return convertView;
	}

	private void initView(ViewHolder viewHolder, View convertView) {
		viewHolder.imgWelcomestudentItemReady = (ImageView) convertView.findViewById(R.id.img_welcomestudent_ready);
		viewHolder.txtWelcomestudentItemReady = (TextView) convertView.findViewById(R.id.txt_welcomestudent_item_ready);
		viewHolder.img_welcomestudent_state = (ImageView) convertView.findViewById(R.id.img_welcomestudent_state);
		convertView.setTag(viewHolder);
	}

	class ViewHolder {
		private TextView txtWelcomestudentItemReady;
		private ImageView imgWelcomestudentItemReady;
		private ImageView img_welcomestudent_state;
	}
}
