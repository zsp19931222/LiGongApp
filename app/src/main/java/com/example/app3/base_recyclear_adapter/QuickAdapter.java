package com.example.app3.base_recyclear_adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.app3.tool.SwipeMenuLayout;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.entity.HomePageWidgetEntity;
import yh.app.appstart.lg.R;

import java.io.File;
import java.util.List;

/**
 * RecyclerView万能适配类
 */
public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH> {

    private List<T> mDatas;

    public QuickAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(VH holder, T data, int position);

    public static class VH extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        private VH(View v) {
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(convertView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T) v;
        }

        public TextView setTextView(int id, CharSequence value) {

            TextView view = getView(id);
            if (value != null) view.setText(value);

            return view;
        }


        public View setImageView(Activity activity, int id, Object url) {
            final ImageView view = getView(id);
            GlideLoadUtils.getInstance().glideLoad(activity, (String) url, view, R.drawable.frist_pushdefaule);
            return view;
        }

        public View setImageView(Activity activity, int id, Object url, int frist_pushdefaule, int a) {
            final ImageView view = getView(id);
            GlideLoadUtils.getInstance().glideLoad(activity, (String) url, view, frist_pushdefaule);
            return view;
        }

        public ImageView setImageView(final Activity activity, final int id, final Object url, final int value) {

            final ImageView view = getView(id);
            String str = (String) url;
            String result = str.substring(0, str.indexOf(":"));
            if (result.equals("http")) {
                if (!GlideLoadUtils.isDestroy(activity))
                    Glide.with(activity)
                            .load(url)
                            .listener(new RequestListener() {
                                @Override
                                public boolean onException(Exception e, Object o, Target target, boolean b) {
                                    setImageView(activity, id, value);
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(Object o, Object o2, Target target, boolean b, boolean b1) {
                                    setImageView(activity, id, url);
                                    return true;
                                }
                            })
                            .into(view);
            } else {
                if (!GlideLoadUtils.isDestroy(activity))
                    Glide.with(activity)
                            .load(new File(str))

                            .listener(new RequestListener() {
                                @Override
                                public boolean onException(Exception e, Object o, Target target, boolean b) {
                                    setImageView(activity, id, value);
                                    return true;
                                }

                                @Override
                                public boolean onResourceReady(Object o, Object o2, Target target, boolean b, boolean b1) {
                                    setImageView(activity, id, url);
                                    return true;
                                }
                            })
                            .into(view);
            }
            return view;
        }

        public ImageView setImageView(Activity activity, int id, int value) {
            ImageView view = getView(id);
            Glide.with(activity).load(value).into(view);
            return view;
        }

        public ImageView setImageView(Context context, int id, int drawable) {
            ImageView view = getView(id);
            view.setBackground(ContextCompat.getDrawable(context, drawable));
            return view;
        }

        public ImageView setCircleImageView(Activity activity, int id, Object value) {

            final ImageView view = getView(id);

            if (value != null)
                GlideLoadUtils.getInstance().glideLoadCircle(activity, (String) value, view, R.drawable.frist_pushdefaule);
            return view;
        }

        public Button setButton(int id, String value) {

            Button view = getView(id);

            if (value != null) view.setText(value);

            return view;
        }

        public RelativeLayout setRelativeLayout(int id) {
            RelativeLayout view = getView(id);
            return view;

        }

        public LinearLayout setLinearLayout(int id) {
            LinearLayout view = getView(id);
            return view;
        }

        public CheckBox setCheckBox(int id, boolean isCheck) {
            CheckBox box = getView(id);
            box.setChecked(isCheck);
            return box;
        }

        /**
         * 禁止侧滑
         */
        public void forbidSideslip() {
            SwipeMenuLayout layout = (SwipeMenuLayout) this.itemView;
            layout.setLeftSwipe(false);
            layout.setSwipeEnable(false);
        }

    }


}