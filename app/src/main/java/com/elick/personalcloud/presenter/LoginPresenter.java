package com.elick.personalcloud.presenter;

import com.elick.personalcloud.View.LoginActivity;
import com.elick.personalcloud.api.RetrofitService;
import com.elick.personalcloud.base.Model.IBaseModel;
import com.elick.personalcloud.base.View.IBaseView;
import com.elick.personalcloud.base.presenter.IBasePresenter;
import com.elick.personalcloud.model.LoginModel;
import com.elick.personalcloud.utils.SharedPreferenceUtils;

import java.io.UnsupportedEncodingException;

public class LoginPresenter implements IBasePresenter<String> {
    private LoginActivity baseView;
    private LoginModel baseModel;
    public LoginPresenter(IBaseView iBaseView) {
        this.baseView = (LoginActivity) iBaseView;
        baseModel = new LoginModel(this);
    }

    public void startLogin(String account, String passwd) {
        baseView.showLoading();
        try {
            baseModel.startLogin(account, passwd);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {

    }

    @Override
    public void onModelSuccess(String s) {
        baseView.hideLoading();
        baseView.loginSuccess();
    }

    @Override
    public void onModelFail(String s) {

    }

}
