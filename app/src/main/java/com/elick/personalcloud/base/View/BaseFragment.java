package com.elick.personalcloud.base.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elick.personalcloud.base.presenter.IBasePresenter;
import com.elick.personalcloud.dialog.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    private Context context;
    private LoadingDialog loadingDialog;
    private View rootView;
    private Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.context=getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(attachLayout(),null);
        unbinder=ButterKnife.bind(this,rootView);
        initView();
        return rootView;
    }

    protected abstract void initView();

    protected abstract int attachLayout();

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

    @Override
    public void showNetError() {
        if (loadingDialog!=null)
            loadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
