package yh.app.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.model.ControllerModel;

/**
 * 选择器适配器
 */

public class ControllerAdapter extends BaseAdapter {
    private List<ControllerModel> list;
    private Context context;
    private LayoutInflater inflater;


    public ControllerAdapter(Context context, List<ControllerModel> list) {
        this.list = list;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.item_listview_controller, parent, false);
            initView(viewHolder, convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtController = (TextView)convertView.findViewById(R.id.txt_controller);
        viewHolder.txtController.setText(list.get(position).getName());
        return convertView;
    }

    private void initView(ViewHolder viewHolder, View convertView) {
        viewHolder.txtController = (TextView)convertView.findViewById(R.id.txt_controller);
        convertView.setTag(viewHolder);

    }

    class ViewHolder {
        private TextView txtController;
    }
}
