package com.example.app3.base_recyclear_adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Toommi on 2017/4/27.
 * RecyclerView Item间距
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = mSpace;
//        outRect.right = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildPosition(view) == 0) {
            outRect.top = mSpace;
        }

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}
