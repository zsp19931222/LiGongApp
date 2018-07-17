package yh.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yh.app.appstart.lg.R;

import yh.app.acticity.AppManage;
import yh.app.model.ApplyModel;
import yh.tool.widget.ScrollGridview;
import 云华.智慧校园.工具._功能跳转;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用主页推荐应用ListView适配器 用于装推荐应用数据
 *
 * @author lft
 */
public class VisitorApplyAdapter extends BaseAdapter {
	private List<ApplyModel.OTHERAPPBean> list;
	private Context context;
	private LayoutInflater inflater;
	private OtherApplyGridViewAdapter gridViewAdapter;

	public VisitorApplyAdapter(Context context, List<ApplyModel.OTHERAPPBean> list) {
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_listview_item, parent, false);
			viewHolder.mGdv_list_item = (ScrollGridview) convertView.findViewById(R.id.gdv_list_item);
			viewHolder.mTxt_GroupName = (TextView) convertView.findViewById(R.id.txt_groupname);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		bindData(viewHolder, position);

		return convertView;

	}

	private class ViewHolder {
		ScrollGridview mGdv_list_item;
		TextView mTxt_GroupName;

	}

	/**
	 * 绑定数据
	 *
	 * @param viewHolder
	 * @param position
	 */
	private void bindData(ViewHolder viewHolder, int position) {
		final ApplyModel.OTHERAPPBean applyModel = list.get(position);
		gridViewAdapter = new OtherApplyGridViewAdapter(applyModel.getLIST(), context);
		viewHolder.mGdv_list_item.setAdapter(gridViewAdapter);
		if (!applyModel.getNAME().isEmpty()) {
			viewHolder.mTxt_GroupName.setText(applyModel.getNAME());

		}

		// 应用点击事件
		viewHolder.mGdv_list_item.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toWeb(applyModel, position);
			}
		});

//		// 长按事件
//		viewHolder.mGdv_list_item.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO Auto-generated method stub
//				Intent intentappmanage = new Intent(context, AppManage.class);
//				context.startActivity(intentappmanage);
//				return true;
//			}
//		});

	}

	/**
	 * 跳转到web 或原生
	 * 
	 * @param applyModel
	 * @param position
	 */
	private void toWeb(ApplyModel.OTHERAPPBean applyModel, int position) {

		Map<String, String> parames = new HashMap<>();
		parames.put("cls", applyModel.getLIST().get(position).getCLASS_NAME());
		parames.put("FunctionID", applyModel.getLIST().get(position).getFUNCTION_ID());
		parames.put("name", applyModel.getLIST().get(position).getFUNCTION_NAME());
		parames.put("packagename()", applyModel.getLIST().get(position).getPACKAGE_NAME());
		parames.put("INTEGRATE_KEY", applyModel.getLIST().get(position).getINTEGRATE_KEY().toString());
		parames.put("function_type", applyModel.getLIST().get(position).getFUNCTION_TYPE());
		new _功能跳转().Jump(context, parames);

	}

}
