package yh.app.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.yhkj.cqgyxy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import yh.app.model.WelcomeStudentDaLiBaoModel;
import yh.app.model.WelcomeStudentModel;

/**
 * 大礼包 适配器
 */

public class WelcomeStudentDaLiBaoAdapter extends BaseAdapter {
	private Context context;
	private List<WelcomeStudentDaLiBaoModel> list;
	private LayoutInflater inflater;

	public WelcomeStudentDaLiBaoAdapter(Context context, List<WelcomeStudentDaLiBaoModel> list) {
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
		viewHolder.imgWelcomestudentItemReady.setBackgroundResource(list.get(position).getFunction_img());
		viewHolder.txtWelcomestudentItemReady.setText(list.get(position).getFunction_name());
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
