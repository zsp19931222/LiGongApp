package 云华.智慧校园.自定义适配器;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import yh.app.contacts.WDBJXQJM;

import com.example.app3.utils.GlideLoadUtils;
import com.yhkj.cqgyxy.R;

import yh.app.tool.ImageAtNotSave;
import 云华.智慧校园.工具.ViewTools;

public class MyBJXQAdapter extends BaseAdapter {
    private JSONArray jsa = new JSONArray();
    private Map<Integer, View> viewlist = new HashMap<>();
    private Context context;

    public MyBJXQAdapter(JSONArray jsa, Context context) {
        this.context = context;
        this.jsa = jsa;
    }

    public int getCount() {
        return jsa.length();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getContentItem(position, convertView, parent);
        }

        try {
            if (viewlist.get(position) == null)
                return getContentItem(position, convertView, parent);
            else
                return viewlist.get(position);
        } catch (Exception e) {
            return getContentItem(position, convertView, parent);
        }

    }

    public View getContentItem(int position, View convertView, ViewGroup parent) {
        if (viewlist.get(position) == null) {
            convertView = ViewTools.getView(context, R.layout.wdbjxqjm_user_item, parent);
            try {
                if (jsa.getJSONObject(position).getString("usertype").equals("2")) {
                    ((ImageView) convertView.findViewById(R.id.face)).setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.jsbj));
                }
                convertView.setTag(jsa.getJSONObject(position).getString("userid"));
                ((TextView) convertView.findViewById(R.id.name)).setText(jsa.getJSONObject(position).getString("name"));
                GlideLoadUtils.getInstance().glideLoad(context,jsa.getJSONObject(position).getString("faceaddress"),((ImageView) convertView.findViewById(R.id.face)),R.drawable.ico_error_little);

            } catch (JSONException e) {
                convertView.findViewById(R.id.face).setBackgroundResource(R.drawable.q1);
            }
        }
        viewlist.put(position, convertView);
        return convertView;
    }
}
