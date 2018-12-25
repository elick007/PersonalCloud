package com.elick.personalcloud.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elick.personalcloud.R;
import com.elick.personalcloud.utils.MyDebug;

public class RefreshView extends LinearLayout implements RefreshListener {
    private LinearLayout mContentLayout;
    private int mDefaultHeight=120;
    private int mState;
    private int mVisibleHeight;
    private ProgressBar progressBar;
    private TextView refreshTV;

    public RefreshView(Context context) {
        super(context);
        init();
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        this.setLayoutParams(layoutParams);
        this.setPadding(0, 0, 0, 0);
        //将refreshHeader高度设置为0
        mContentLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.refresh_item_layout, null);
        addView(mContentLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        progressBar=mContentLayout.findViewById(R.id.progress_bar);
        progressBar.setVisibility(GONE);
        refreshTV=mContentLayout.findViewById(R.id.swipe_to_refresh_tv);
        //初始化控件
//        mArrowImageView = findViewById(R.id.ivHeaderArrow);
//        mStatusTextView =  findViewById(R.id.tvRefreshStatus);
//        mProgressBar = findViewById(R.id.refreshProgress);

        //初始化动画
//        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mRotateUpAnim.setDuration(200);
//        mRotateUpAnim.setFillAfter(true);
//        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mRotateDownAnim.setDuration(200);
//        mRotateDownAnim.setFillAfter(true);

        //将mContentLayout的LayoutParams高度和宽度设为自动包裹并重新测量
//        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mMeasuredHeight = getMeasuredHeight();//获得测量后的高度
//        mState=STATE_NORMAL;
    }

    @Override
    public void onReset() {
        mState = 0;
        mVisibleHeight = 0;
        progressBar.setVisibility(GONE);
        updateViewLayout(mContentLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
    }

    @Override
    public void onPrepare() {
        mState=STATE_RELEASE_TO_REFRESH;
        if (getVisibleHeight()<mDefaultHeight){
            refreshTV.setText(" 继续下拉刷新");
        }else {
            refreshTV.setText(" 释放开始刷新");
        }
        updateViewLayout(mContentLayout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mVisibleHeight));
    }

    @Override
    public void onRefreshing() {
        mState=STATE_REFRESHING;
        progressBar.setVisibility(VISIBLE);
        refreshTV.setText(" 正在刷新");
        updateViewLayout(mContentLayout,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mDefaultHeight));
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {
        if (getVisibleHeight() > 0 || offSet > 0) { //offset>0表示向下滑动
            setVisibleHeight((int) offSet + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                onPrepare();
            }
        }
    }

    @Override
    public boolean onRelease() {
        if (getVisibleHeight()>=mDefaultHeight){
            onRefreshing();
            return true;
        } else {
            onReset();
            return false;
        }
    }

    @Override
    public void refreshComplete() {

    }

    @Override
    public View getHeaderView() {
        return this;
    }

    @Override
    public int getVisibleHeight() {
        return mVisibleHeight;
    }

    @Override
    public boolean canRefresh() {
        return mState<=1;
    }

    public void setVisibleHeight(int visibleHeight) {
        mVisibleHeight = visibleHeight;
    }
}
