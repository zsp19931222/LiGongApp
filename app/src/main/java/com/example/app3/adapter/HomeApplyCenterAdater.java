package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class HomeApplyCenterAdater extends BaseAdapter {
    private List<Map<String, String>> data;
    private Context context;
    private View first;
    private GridView gridView;

    public HomeApplyCenterAdater(GridView gridView, List<Map<String, String>> data, Context context) {
        this.data = data;
        this.context = context;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        if (data.size() <= 12) {
            return data != null ? data.size() : 0;
        } else {
            return 12;
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.get(position);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.view_textview_home_apply_item, parent, false);
            holder.img =  convertView.findViewById(R.id.home_apply_item_img_image);
            holder.title =  convertView.findViewById(R.id.home_apply_item_txt_function_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GlideLoadUtils.getInstance().glideLoad(context,data.get(position).get("FUNCTION_FACE"),holder.img,R.drawable.ico_load_little);
        holder.title.setText(data.get(position).get("FUNCTION_NAME"));
        return convertView;
    }

    public View getFirst() {
        return first;
    }

    // ViewHolder静态类
    private static class ViewHolder {
        public ImageView img;
        public TextView title;
    }
}
