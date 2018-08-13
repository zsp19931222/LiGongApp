package yh.app.adapter;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.yhkj.cqgyxy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import yh.app.acticity.TargetDetailActivity;
import yh.app.model.WangShangModel;

/**
 * 网上迎新适配器
 */

public class WangShangAdapter extends BaseAdapter {
    private Context context;
    private List<WangShangModel.ContentBean> list;
    private LayoutInflater inflater;
    private int max;


    public WangShangAdapter(Context context, List<WangShangModel.ContentBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        max=list.size();
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = inflater.inflate(R.layout.item_wangshang_listview, parent, false);
            viewHolder.imgDottedTop = convertView.findViewById(R.id.img_dotted_top);
            viewHolder.imgItemeName = (ImageView) convertView.findViewById(R.id.img_iteme_name);
            viewHolder.imgDottedDow = convertView.findViewById(R.id.img_dotted_dow);
            viewHolder.txtItemContent = (TextView) convertView.findViewById(R.id.txt_item_content);
            viewHolder.imgItemeState = (ImageView) convertView.findViewById(R.id.img_iteme_state);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //如果是最后一项隐藏底部虚线
        if (position==max){
            viewHolder.imgDottedDow.setVisibility(View.INVISIBLE);
        }

        //如果是第一个就隐藏顶部虚线
        if (position==0){
            viewHolder.imgDottedTop.setVisibility(View.INVISIBLE);
            

        }
        if (list.get(position).getCjzt().equals("1")) {
        	viewHolder.imgItemeState.setVisibility(View.VISIBLE);
		}
        viewHolder.txtItemContent.setText(list.get(position).getHjmc());
//        Glide.with(context)
//                .load(list.get(position).getIcon_url())
//                .error(R.drawable.ico_load_little)
//                .into(viewHolder.imgItemeName);

        GlideLoadUtils.getInstance().glideLoad(context,list.get(position).getIcon_url(),viewHolder.imgItemeName,R.drawable.ico_load_little);


        return convertView;
    }



    class ViewHolder {
        ImageView  imgItemeName, imgItemeState;
        View imgDottedTop,imgDottedDow;
        TextView txtItemContent;
    }

}
