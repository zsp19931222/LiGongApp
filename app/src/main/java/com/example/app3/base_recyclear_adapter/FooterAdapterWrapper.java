package com.example.app3.base_recyclear_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView 尾item
 */
public class FooterAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE {
        FOOTER,
        NORMAL
    }

    private QuickAdapter mAdapter;
    private View mFooterView;

    public FooterAdapterWrapper(QuickAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mAdapter.getItemCount()) {
            return ITEM_TYPE.FOOTER.ordinal();
        } else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == mAdapter.getItemCount()) {
            return;
        } else {
            mAdapter.onBindViewHolder(((QuickAdapter.VH) holder), position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.FOOTER.ordinal()) {
            return new RecyclerView.ViewHolder(mFooterView) {
            };
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    public void addFooterView(View view) {
        this.mFooterView = view;
    }
}