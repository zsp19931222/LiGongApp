package yh.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

/**
 * Created by Administrator on 2017/9/19.
 */

public class TopbarHelper_v3 {

    private View topbarView;
    private Activity context;

    public TopbarHelper_v3(Activity context, View topbarView) {
        this.topbarView = topbarView;
        this.context = context;
    }

    /**
     * @param onClickListener 如果是null，则设置默认点击事件;默认点击事件只设置了返回按钮点击事件
     */
    public void setOnClickListener(final OnClickListener onClickListener) {
        if (onClickListener == null) {
            topbarView.findViewById(R.id.item_topbar_imgb_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.finish();
                }
            });
        } else {
            topbarView.findViewById(R.id.item_topbar_imgb_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.OnBackClick();
                }
            });

            topbarView.findViewById(R.id.itemn_topbar_txt_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.OnTitleClick();
                }
            });
        }
    }

    public void setTitle(String title) {
        ((TextView) topbarView.findViewById(R.id.itemn_topbar_txt_title)).setText(title);
    }

    public interface OnClickListener {
        /**
         * 返回按钮点击事件
         */
        void OnBackClick();

        /**
         * 标题点击事件
         */
        void OnTitleClick();

        /**
         * 暂时没用
         */
        @Deprecated
        void OnRightClick();
    }
}
