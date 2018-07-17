package com.example.app3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yunhuakeji.app.utils.DpPx;
import yh.app.appstart.lg.R;

import org.json.JSONArray;
import org.json.JSONObject;

import yh.app.logTool.Log;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2017/9/19.
 */

public class BaseListAdapter extends BaseAdapter {
    public static final String FILL_VIEW = "fill_view";
    private JSONArray jsa;
    private Context context;
    private ListView listview;
    private int ListHeight = 0;

    public BaseListAdapter(Context context, ListView listview, JSONArray jsa) {
        this.jsa = jsa;
        this.context = context;
        this.listview = listview;
    }

    @Override
    public int getCount() {
        return jsa.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return jsa.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject jso = null;
        if (convertView == null) {
            try {
                jso = jsa.getJSONObject(position);
                if (jso.getString("type").equals(FILL_VIEW)) {
                    int layout = new RHelper().getLayout(context, jso.getString("layout"));
                    convertView = LayoutInflater.from(context).inflate(layout, parent, false);
                    ListHeight += new DpPx(context).getDpToPx(15);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_img_txt_txt_more, parent, false);
                    int drawable = new RHelper().getDrawable(context, jso.getString("pic_default"));
                    convertView.findViewById(R.id.myself_img_face).setBackgroundResource(drawable);
                    ((TextView) convertView.findViewById(R.id.myself_txt_title)).setText(jso.getString("txt"));
                    ((TextView) convertView.findViewById(R.id.myself_txt_num)).setText(jso.getString("num"));
                    convertView.setTag(jso.getString("cls"));
                    if (position == jsa.length() - 1 || jsa.getJSONObject(position + 1).getString("type").equals(FILL_VIEW)) {
                        convertView.findViewById(R.id.myself_view_divider).setVisibility(View.INVISIBLE);
                    }
                    ListHeight += new DpPx(context).getDpToPx(51);
                }
                if (position == jsa.length() - 1) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ListHeight);
                    listview.setLayoutParams(lp);
                    Log.d("" + ListHeight);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}