package yh.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.activity.BrowserActivity;
import com.yhkj.cqgyxy.R;

import java.util.List;

import yh.app.model.MuBiaodetailModel;

public class MuBiaoXiangQinAdapter extends BaseAdapter {
	private List<MuBiaodetailModel.ContentBean.MessageBean.DetailsBean> list;
	private Context context;
	private LayoutInflater inflater;
	private int selectItem = 0;// 用于点击改变字体颜色

	private View.OnClickListener onQiandao = null;

	private String url;// 资料地址

	public MuBiaoXiangQinAdapter(Context context, List<MuBiaodetailModel.ContentBean.MessageBean.DetailsBean> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	// 签单的点击事件
	public void setOnQianDao(View.OnClickListener mqiandaoOnclick) {
		this.onQiandao = mqiandaoOnclick;
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
			arg1 = inflater.inflate(R.layout.yh_target_detail_activity_item_target_explain, arg2, false);
			viewHolder.yh_txt_target_detail_item_num = (TextView) arg1.findViewById(R.id.yh_txt_target_detail_item_num);
			viewHolder.yh_txt_target_detail_item_message = (TextView) arg1
					.findViewById(R.id.yh_txt_target_detail_item_message);
			viewHolder.yh_txt_target_detail_activity_item_sign = (TextView) arg1
					.findViewById(R.id.yh_txt_target_detail_activity_item_sign);
			viewHolder.yh_txt_target_detail_item_data = (TextView) arg1
					.findViewById(R.id.yh_txt_target_detail_item_data);

			viewHolder.checkbox_qiandao = (CheckBox) arg1.findViewById(R.id.checkbox_qiandao);
			viewHolder.yh_progbar_target_detail_activity_item_progbar = (ProgressBar) arg1
					.findViewById(R.id.yh_progbar_target_detail_activity_item_progbar);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		if (arg0 == selectItem) {
			viewHolder.yh_txt_target_detail_item_num.setTextColor(context.getResources().getColor(R.color.button));
		}
		viewHolder.yh_txt_target_detail_item_num.setText((arg0 + 1) + "");
		viewHolder.yh_txt_target_detail_item_message.setText(list.get(arg0).getC_name());

		// 资料
		viewHolder.yh_txt_target_detail_item_data.setText(list.get(arg0).getC_resource_name());
		viewHolder.yh_txt_target_detail_item_data.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 添加下划线
		url = list.get(arg0).getC_resource();
		viewHolder.yh_txt_target_detail_item_data.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(url)) {
					Toast.makeText(context, "查看" + url, Toast.LENGTH_SHORT).show();
					Intent intentweb = new Intent(context, BrowserActivity.class);
					intentweb.putExtra("url", url);
					context.startActivity(intentweb);
				} else {
					Toast.makeText(context, "暂不支持查看", Toast.LENGTH_SHORT).show();
				}

			}
		});
		// 签到用
		viewHolder.checkbox_qiandao.setOnClickListener(onQiandao);
		// 签到
		String qdtxt = list.get(arg0).getC_sign_times();
		if (list.get(arg0).getCan_sign().equals("0")) {
			// 已经签到了      不能签到
			viewHolder.checkbox_qiandao.setChecked(true);
			viewHolder.yh_txt_target_detail_activity_item_sign.setText("已签到/" + qdtxt);
		} else {
			if (list.get(arg0).getC_completion().equals("0")) {
				viewHolder.checkbox_qiandao.setTag(arg0);
				// 可以签到
				viewHolder.yh_txt_target_detail_activity_item_sign.setText("签到/" + qdtxt);
			} else {
				viewHolder.checkbox_qiandao.setChecked(true);
				viewHolder.yh_txt_target_detail_activity_item_sign.setText("已签到/" + qdtxt);
				// 不能签到栏
			}
		}

		return arg1;
	}

	class ViewHolder {
		private TextView yh_txt_target_detail_item_num;
		private TextView yh_txt_target_detail_item_message;// 目标标题
		private TextView yh_txt_target_detail_activity_item_sign;// 签到
		private TextView yh_txt_target_detail_item_data;// 相关资料
		private ProgressBar yh_progbar_target_detail_activity_item_progbar;// 签到进度条
		private CheckBox checkbox_qiandao;
	}

}
