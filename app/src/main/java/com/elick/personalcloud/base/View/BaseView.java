package com.elick.personalcloud.base.View;

import android.content.Context;

import com.elick.personalcloud.base.presenter.IBasePresenter;
import com.elick.personalcloud.dialog.LoadingDialog;

public abstract class BaseView implements IBaseView {
    private IBasePresenter iBasePresenter;
    private LoadingDialog loadingDialog;
    private Context context;
    public BaseView(IBasePresenter iBasePresenter, Context context) {
        this.iBasePresenter = iBasePresenter;
        this.context=context;
    }

    @Override
    public void showLoading() {
        if (loadingDialog==null){
            loadingDialog=new LoadingDialog(context);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog!=null)
        loadingDialog.dismiss();
    }
}
