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
import yh.app.acticity.AppManage;
import org.androidpn.push.Constants;

import yh.app.model.AppManageModel;
import yh.app.model.ApplyModel;
import yh.app.tool.SqliteHelper;
import yh.tool.widget.ScrollGridview;
import 云华.智慧校园.工具._功能跳转;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

/**
 * 应用主页推荐应用ListView适配器 用于装推荐应用数据
 *
 * @author lft
 */
public class OtherApplyManageAdapter extends BaseAdapter {
	private List<AppManageModel.OTHERAPPBean> list;
	private Context context;
	private LayoutInflater inflater;
	private OtherApplyGridViewManageAdapter gridViewAdapter;
	private String function_id;
	private SqliteHelper helper;

	public OtherApplyManageAdapter(Context context, List<AppManageModel.OTHERAPPBean> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
        helper=ExampleApplication.getInstance().getSqliteHelper();
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
	String f;

	private void bindData(ViewHolder viewHolder, int position) {
		final AppManageModel.OTHERAPPBean applyModel = list.get(position);
		gridViewAdapter = new OtherApplyGridViewManageAdapter(applyModel.getLIST(), context);
		viewHolder.mGdv_list_item.setAdapter(gridViewAdapter);
		if (!applyModel.getNAME().isEmpty()) {
			viewHolder.mTxt_GroupName.setText(applyModel.getNAME());

		}

		// 应用单击击事件
		viewHolder.mGdv_list_item.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				setManage(applyModel, position);
			}
		});

	}

	/**
	 * 点击修改管理
	 */
	int size;

	private void setManage(AppManageModel.OTHERAPPBean applyModel, int position) {
		// 修改应用
		Constants.isAppManageUpdata=true;
		// 删除id
		List<Map<String, String>> listmap = helper.rawQuery("select  function_id from appmanage where userid=?", new String[] { Constants.number });
		size = listmap.size();
		if (listmap.size() > 0) {
			// 数据库有就删除
			for (int i = 0; i < listmap.size(); i++) {
				function_id = listmap.get(i).get("function_id");
				if (function_id.equals(applyModel.getLIST().get(position).getFUNCTION_ID())) {
					helper.execSQL("delete  from appmanage where userid=? and function_id=?",new Object[] { Constants.number, function_id });
					Constants.isdelete = true;
					break;

				} else {
					// 数据没有找到相应的function_id就添加这条信息
					helper.execSQL("replace into appmanage(function_id,userid) values(?,?)",new Object[] { applyModel.getLIST().get(position).getFUNCTION_ID(), Constants.number });
				}

			}
		} else {
			//第一次进来数据库为空调用这个
			helper.execSQL("replace into appmanage(function_id,userid) values(?,?)",new Object[] { applyModel.getLIST().get(position).getFUNCTION_ID(), Constants.number });
		}
		// 全局刷新
		AppManage.getDataAdapter();
	}

}
