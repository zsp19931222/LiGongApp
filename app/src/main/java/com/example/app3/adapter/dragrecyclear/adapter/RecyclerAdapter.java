package com.example.app3.adapter.dragrecyclear.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app3.adapter.dragrecyclear.bean.DragBean;
import com.example.app3.adapter.dragrecyclear.helper.MyItemTouchCallback;
import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 *
 * 可移动的RecyclerViewAdapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements MyItemTouchCallback.ItemTouchAdapter {
    private MyItemClickListener mItemClickListener;
    private Context context;
    private List<DragBean> results;
    public interface OnChangePositionListener{
        void onChange(List<DragBean> dragBeen);
    }

    public void setChangePositionListener(OnChangePositionListener changePositionListener) {
        this.changePositionListener = changePositionListener;
    }

    private OnChangePositionListener changePositionListener;

    public RecyclerAdapter(List<DragBean> results){
        this.results = results;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compile_desc, parent, false);
        return new MyViewHolder(itemView,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textView.setText(results.get(position).getName());
        GlideLoadUtils.getInstance().glideLoad(context, results.get(position).getUrl(),holder.imageView,R.drawable.ico_load_little);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
//        if (fromPosition==results.size()-1 || toPosition==results.size()-1){
//            return;
//        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(results, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(results, i, i - 1);

            }
        }
        changePositionListener.onChange(results);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onSwiped(int position) {
        results.remove(position);
        changePositionListener.onChange(results);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MyItemClickListener mListener;
        public TextView textView;
        public ImageView imageView;
        public ImageView rolaImageView;
        public RelativeLayout rolaRel;

        public MyViewHolder(View itemView , MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;

//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            int width = wm.getDefaultDisplay().getWidth();
//            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
//            layoutParams.height = width/4;
//            itemView.setLayoutParams(layoutParams);
            textView = (TextView) itemView.findViewById(R.id.compile_item_desc_text);
            imageView = (ImageView) itemView.findViewById(R.id.compile_item_desc_image);
            rolaImageView = (ImageView) itemView.findViewById(R.id.compile_item_desc_role);
            rolaRel = (RelativeLayout) itemView.findViewById(R.id.compile_item_desc_role_rel);
            rolaRel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }
    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
