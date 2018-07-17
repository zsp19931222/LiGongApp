package com.example.app3.base_recyclear_adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 滑动删除
 */


public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {

//    private QuickAdapter mAdapter;
//    private List<String> mData;
//    private SearchHistoryModelDao historyDao;
    public OnSwipedListener onSwipedListener;

    public SimpleItemTouchCallback() {

    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //s上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END; //左->右和右->左滑动
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        int from = viewHolder.getAdapterPosition();
//        int to = target.getAdapterPosition();
//        Collections.swap(mData, from, to);
//        mAdapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        int pos = viewHolder.getAdapterPosition();
        onSwipedListener.onSwiped();

//        try {
//            mData.remove(pos);
//            mAdapter.notifyItemRemoved(pos);
//            SearchHistoryModel findData = historyDao.queryBuilder().where(SearchHistoryModelDao.Properties.History.eq(mData.get(pos))).build().unique();
//            if (findData != null) {
////                mAdapter.notifyItemRangeChanged(pos, mData.size() - pos);
//                historyDao.deleteByKey(findData.getHistory());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }


//        mAdapter.notifyItemRangeChanged(pos, mData.size() - pos);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            QuickAdapter.VH holder = (QuickAdapter.VH) viewHolder;
//            holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        QuickAdapter.VH holder = (QuickAdapter.VH) viewHolder;
//        holder.itemView.setBackgroundColor(0xffeeeeee); //背景色还原
    }


    public void setOnSwipedListener(OnSwipedListener onSwipedListener) {
        this.onSwipedListener = onSwipedListener;

    }

    public interface OnSwipedListener {
        void onSwiped();
    }
}