package com.elick.personalcloud.base.View;

import java.util.List;

public interface IBaseView<T> {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误，modify 对网络异常在 BaseActivity 和 BaseFragment 统一处理
     */
    void showNetError();

    /**
     * 完成刷新, 新增控制刷新
     */
    //void finishRefresh(T t);

}
