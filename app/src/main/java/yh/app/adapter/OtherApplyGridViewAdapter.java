package yh.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidpn.push.Constants;

import yh.app.model.ApplyModel;
import yh.app.tool.SqliteHelper;

import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import java.util.List;
import java.util.Map;

/***
 * 应用页推荐应用GridView item适配器 用于填装应用数据
 *
 * @author lft
 *
 */
public class OtherApplyGridViewAdapter extends BaseAdapter {
    private List<ApplyModel.OTHERAPPBean.LISTBean> list;
    ApplyModel.OTHERAPPBean.LISTBean beanX;
    private Context context;
    private LayoutInflater inflater;
    private ApplyModel.OTHERAPPBean.LISTBean listBean;
    private SqliteHelper helper;

    public OtherApplyGridViewAdapter(List<ApplyModel.OTHERAPPBean.LISTBean> list, Context context) {

        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        helper = ExampleApplication.getInstance().getSqliteHelper();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.scrollgridview_item, parent, false);
            viewHolder.mImg_apply = (ImageView) convertView.findViewById(R.id.img_apply);
            viewHolder.mTxt_applyName = (TextView) convertView.findViewById(R.id.txt_applyname);
            viewHolder.mImg_manage = (ImageView) convertView.findViewById(R.id.img_manage);
            viewHolder.mLyout_AppMagan = (LinearLayout) convertView.findViewById(R.id.lyout_appmagan);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindData(viewHolder, position);
        return convertView;

    }

    private class ViewHolder {
        private ImageView mImg_apply, mImg_manage;
        private TextView mTxt_applyName;
        private LinearLayout mLyout_AppMagan;
    }

    /**
     * 绑定应用数据
     *
     * @param viewHolder
     * @param position
     */
    private String function_id;

    private void bindData(ViewHolder viewHolder, int position) {
        listBean = list.get(position);

        //数据库没有正常获取网络资源
        if (listBean != null) {
            viewHolder.mTxt_applyName.setText(listBean.getFUNCTION_NAME());
            // 隐藏编辑按钮图标
            viewHolder.mImg_manage.setVisibility(View.VISIBLE);
            viewHolder.mLyout_AppMagan.setVisibility(View.VISIBLE);
            // 加载推荐应用图片

            GlideLoadUtils.getInstance().glideLoad(context, listBean.getFUNCTION_FACE(), viewHolder.mImg_apply, R.drawable.ico_load_little);

        }

    }

}
