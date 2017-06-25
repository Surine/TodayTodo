package com.surine.todaytodo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.surine.todaytodo.Interface.onMoveAndSwipeListener;

/**
 * Created by surine on 2017/5/25.
 */

public class MainItemTouch extends ItemTouchHelper.Callback {

    private onMoveAndSwipeListener mAdapter;

    public MainItemTouch(onMoveAndSwipeListener listener){
        mAdapter = listener;
    }



    //set move and swipe direction
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            //swipe direction
            int swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
            // makeMovementFlags(0,swipeflags); the 0 is not support move
            return makeMovementFlags(0,swipeFlags);
        }
        return 0;
    }

    //move item
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //none
        return false;
    }

    //swipe item
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //start onItemDismiss
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
