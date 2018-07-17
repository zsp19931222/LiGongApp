package yh.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.model.PersnaldataDetailsModel;

/**
 * 入学准备 适配器
 */

public class PersnaldataDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<PersnaldataDetailsModel.ContentBean> list;
    private LayoutInflater inflater;


    public PersnaldataDetailsAdapter(Context context, List<PersnaldataDetailsModel.ContentBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list.size() <= 0) {
            return 0;
        }
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
            convertView = inflater.inflate(R.layout.item_persnaldata_details, parent, false);
            initView(viewHolder, convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtPersnaldatadetailsTitle.setText(list.get(position).getBmmc());
        viewHolder.txtPersnaldatadetailsName.setText(list.get(position).getBjmc());
        return convertView;
    }


    private void initView(ViewHolder viewHolder, View convertView) {
        viewHolder.txtPersnaldatadetailsTitle = (TextView) convertView.findViewById(R.id.txt_persnaldatadetails_title);
        viewHolder.txtPersnaldatadetailsName = (TextView) convertView.findViewById(R.id.txt_persnaldatadetails_name);

        convertView.setTag(viewHolder);

    }


    class ViewHolder {
        private TextView txtPersnaldatadetailsTitle;
        private TextView txtPersnaldatadetailsName;
    }
}
