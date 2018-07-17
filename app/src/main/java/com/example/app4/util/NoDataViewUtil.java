package com.example.app4.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21 0021.
 * <p>
 * 没有数据显示
 */

public class NoDataViewUtil<T> {

    private List<T> list;
    private Context context;
    private LinearLayout noDataLin;//没有数据
    private ImageView noDataImage;
    private TextView noDataText;

    public NoDataViewUtil(List<T> list, View parentView, Context context) {
        this.list = list;
        this.context = context;
        noDataLin = parentView.findViewById(R.id.no_data_lin);
        noDataImage = parentView.findViewById(R.id.no_data_image);
        noDataText = parentView.findViewById(R.id.no_data_text);
    }

    public void showNoDataView(int image, String hint) {
        if (list.size() != 0) {
            noDataLin.setVisibility(View.GONE);
        } else {
            noDataLin.setVisibility(View.VISIBLE);
            noDataImage.setBackgroundResource(image);
            noDataText.setText(hint);
        }
    }

}
