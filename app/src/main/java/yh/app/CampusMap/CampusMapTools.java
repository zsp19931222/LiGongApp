package yh.app.CampusMap;

import java.util.List;
import java.util.Map;

import com.baidu.mapapi.model.LatLng;
import com.yhkj.cqgyxy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import yh.app.function.CampusMap;
import 云华.智慧校园.自定义控件.MapCampusPopwindow;

public class CampusMapTools {
    public LinearLayout getCampusLayout(Context context, List<Map<String, String>> jsa, ViewGroup parent) {
        if (jsa == null || jsa.size() == 0)
            return null;
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.map_campus, parent, false);
        LinearLayout xq_layout = layout.findViewById(R.id.xq_layout);
        try {
            for (int i = 0; i < jsa.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.map_campus_list_item, parent, false);
                try {
                    Map<String, String> jso = jsa.get(i);
                    view.setTag(jso);
                    ((TextView) view.findViewById(R.id.text)).setText(jso.get("MAP_NAME"));
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            @SuppressWarnings("unchecked")
                            Map<String, String> map = (Map<String, String>) v.getTag();
                            LatLng ll = null;
                            String name = null;
                            try {
                                ll = new LatLng(Double.valueOf(map.get("MAP_LAT")), Double.valueOf(map.get("MAP_LNG")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            MapCampusPopwindow.getPopupWindow().dismiss();
                            try {
                                name = map.get("MAP_NAME");
                            } catch (Exception e) {
                                return;
                            }
                            CampusMap.myLocal(name, ll);
                        }
                    });
                    xq_layout.addView(view);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        return layout;
    }
}
