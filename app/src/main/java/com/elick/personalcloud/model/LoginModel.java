package com.elick.personalcloud.model;

import com.elick.personalcloud.api.bean.AuthBean;
import com.elick.personalcloud.api.RetrofitService;
import com.elick.personalcloud.api.bean.DefaultRepo;
import com.elick.personalcloud.base.Model.BaseModel;
import com.elick.personalcloud.presenter.LoginPresenter;
import com.elick.personalcloud.utils.MyDebug;
import com.elick.personalcloud.utils.SharedPreferenceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginModel extends BaseModel<LoginPresenter,String> {
    private String encodedAccount;
    private String encodedPasswd;

    public LoginModel(LoginPresenter loginPresenter) {
        super();
        iBasePresenter=loginPresenter;
    }

    public void startLogin(String account, String passwd) throws UnsupportedEncodingException {
        MyDebug.d("model start login");
        encodedAccount = URLEncoder.encode(account, "utf-8");
        encodedPasswd = URLEncoder.encode(passwd,"utf-8");
        loadByNetwork();

    }


    @Override
    protected void loadByLocal() {

    }

    @Override
    protected void loadByNetwork() {
        Observable<AuthBean> observable=RetrofitService.builLoginServices().loginToGetToken(encodedAccount,encodedPasswd);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MyDebug.d("login call start");
                    }

                    @Override
                    public void onNext(AuthBean authBean) {
                        SharedPreferenceUtils.ContentValue contentValue=new SharedPreferenceUtils.ContentValue("auth",authBean.getToken());
                        SharedPreferenceUtils.putValues(contentValue);
                        //onHandleSuccess(authBean.getToken());
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyDebug.d(" login error");
                    }

                    @Override
                    public void onComplete() {
                        MyDebug.d("login success");
                        getDefaultRepo();
                    }
                });
    }
    public void getDefaultRepo(){
        Observable<DefaultRepo> observable=RetrofitService.buildApiServices().getDefaultRepo();
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<DefaultRepo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MyDebug.d("get default repo start");
                    }

                    @Override
                    public void onNext(DefaultRepo defaultRepo) {
                        SharedPreferenceUtils.ContentValue contentValue=new SharedPreferenceUtils.ContentValue("repo",defaultRepo.getRepo_id());
                        SharedPreferenceUtils.putValues(contentValue);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        onHandleSuccess(null);
                    }
                });

    }

    @Override
    public void onHandleSuccess(String s) {
        iBasePresenter.onModelSuccess(s);
    }

    @Override
    public void onHandelFail(String s) {

    }
}
