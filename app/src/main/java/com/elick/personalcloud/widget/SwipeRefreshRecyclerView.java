package com.elick.personalcloud.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.elick.personalcloud.R;
import com.elick.personalcloud.adapter.BaseAdapter;
import com.elick.personalcloud.adapter.FileListAdapter;
import com.elick.personalcloud.utils.MyDebug;

public class SwipeRefreshRecyclerView extends RecyclerView {
    private float mLastY;
    private float mTaltolDis;
    private RefreshListener refreshListener;
    private OnRefreshListener onRefreshListener;

    public SwipeRefreshRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SwipeRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(BaseAdapter adapter) {
//        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_item_layout, null);
//        adapter.setHeadView(headView);
//        refreshListener = new RefreshView(getContext());
//        adapter.setRefreshView((View) refreshListener);
        this.refreshListener= (RefreshListener) adapter.getRefreshView();
        super.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (refreshListener == null) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getY();
                mTaltolDis = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = e.getY();
                float distans = (curY - mLastY) / 2;
                mTaltolDis = mTaltolDis + distans;
                mLastY = curY;
                if (isOnTop() && refreshListener.canRefresh()) {
                    refreshListener.onMove(distans, mTaltolDis);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (refreshListener.canRefresh()) {
                    if (refreshListener.onRelease()) {
                        //true 达到滑动距离，开始刷新
                        onRefreshListener.refreshToGetData();
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        boolean result = super.canScrollVertically(direction);
        MyDebug.e("canScrollVertically=" + result);
        return result;
    }

    private boolean isOnTop() {
        return refreshListener.getHeaderView().getParent() != null;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void refreshToGetData();
    }
    public void OnRefreshFinish(){
        refreshListener.onReset();
    }
}
