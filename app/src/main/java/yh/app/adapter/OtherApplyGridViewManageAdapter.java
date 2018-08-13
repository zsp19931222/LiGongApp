package yh.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import yh.app.acticity.AppManage;

import org.androidpn.push.Constants;

import yh.app.model.AppManageModel;
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
public class OtherApplyGridViewManageAdapter extends BaseAdapter {
    private List<AppManageModel.OTHERAPPBean.LISTBean> list;
    ApplyModel.OTHERAPPBean.LISTBean beanX;
    private Context context;
    private LayoutInflater inflater;
    private AppManageModel.OTHERAPPBean.LISTBean listBean;
    private ViewHolder viewHolder;
    private String function_id;
    private SqliteHelper helper;

    public OtherApplyGridViewManageAdapter(List<AppManageModel.OTHERAPPBean.LISTBean> list, Context context) {

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
    int ic;

    private void bindData(ViewHolder viewHolder, int position) {
        listBean = list.get(position);
        if (listBean != null) {
            viewHolder.mTxt_applyName.setText(listBean.getFUNCTION_NAME());
            updataApp();
            // 隐藏编辑按钮图标
            viewHolder.mImg_manage.setVisibility(View.VISIBLE);
            viewHolder.mLyout_AppMagan.setVisibility(View.VISIBLE);
            // 加载推荐应用图片
            GlideLoadUtils.getInstance().glideLoad(context, listBean.getFUNCTION_FACE(), viewHolder.mImg_apply, R.drawable.ico_load_little);

            // 添加矩形边框背景
            viewHolder.mLyout_AppMagan.setBackground(ContextCompat.getDrawable(context, R.drawable.border_square));

        }
    }

    /**
     * 修改应用
     */
    String statu;

    private void updataApp() {
        List<Map<String, String>> listmap = helper.rawQuery("select  function_id from appmanage where userid=?", new String[]{Constants.number});
        if (listmap.size() > 0) {
            // 数据库有
            for (int i = 0; i < listmap.size(); i++) {
                function_id = listmap.get(i).get("function_id");

                if (listBean.getFUNCTION_ID().equals(function_id)) {
                    // 如果数据库有是被用户隐藏显示加号可以隐藏
                    viewHolder.mImg_manage.setImageResource(R.drawable.ico_apply_add);
                    break;
                } else {
                    // 如果数据库没有没有被隐藏显示减号不可可以隐藏
                    viewHolder.mImg_manage.setImageResource(R.drawable.ico_apply_delete);
                }
            }
        } else {
            // 数据没有调用网络上的
            statu = listBean.getSTATUS();
            if (Constants.isdelete) {
                //如果是经过删除数据导致数据库清空说明是最后一条数据那么我们就将全部的图标都设为减号
                viewHolder.mImg_manage.setImageResource(R.drawable.ico_apply_delete);
            } else {
                if (listBean.getSTATUS().equals("0")) {
                    // 已经隐藏
                    viewHolder.mImg_manage.setImageResource(R.drawable.ico_apply_add);

                    // 把已经隐藏的添加到数据库
                    helper.execSQL("replace into appmanage(function_id,userid) values(?,?)", new Object[]{listBean.getFUNCTION_ID(), Constants.number});

                } else {
                    // 没有隐藏
                    viewHolder.mImg_manage.setImageResource(R.drawable.ico_apply_delete);
                }
            }

        }
    }

}
