package com.elick.personalcloud.model;

import com.elick.personalcloud.api.RetrofitService;
import com.elick.personalcloud.api.bean.FileListBean;
import com.elick.personalcloud.base.Model.BaseModel;
import com.elick.personalcloud.config.Constans;
import com.elick.personalcloud.presenter.HomePresenter;
import com.elick.personalcloud.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeModel extends BaseModel<HomePresenter, List<FileListBean.FileListItem>> {
    public HomeModel(HomePresenter homePresenter) {
        iBasePresenter = homePresenter;
    }

    public void getFileList(boolean isRefresh) {
        if (!isRefresh) {
            loadByLocal();
        } else {
           loadByNetwork();
        }

    }

    public void mkDir(String absolutePath) {
        Observable<String> observable = RetrofitService.buildApiServices().createNewDir(SharedPreferenceUtils.getString("repo"), absolutePath, "mkdir");
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.equals("success")) {
                            getFileList(true);
                        } else {
                            onHandelFail(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void renameDir(String absolutePath, String newName) {
        Observable<String> observable = RetrofitService.buildApiServices().renameDir(SharedPreferenceUtils.getString("repo"), absolutePath, "rename", newName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.equals("success")) {
                            getFileList(true);
                        } else {
                            onHandelFail(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void mutilDelete(String absolutePath,String fileNames){
        Observable<String> observable=RetrofitService.buildApiServices().multiDelFiles(SharedPreferenceUtils.getRepo(),absolutePath,fileNames);
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        if (s.equals("success")) {
                            getFileList(true);
                        } else {
                            onHandelFail(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void loadByLocal() {
        List<FileListBean.FileListItem> fileList;
        fileList=SharedPreferenceUtils.getFileList(Constans.getCurrentPath().toString());
        if (fileList==null){
            loadByNetwork();
        }
        else {
            onHandleSuccess(fileList);
        }
    }

    @Override
    protected void loadByNetwork() {
        Observable<List<FileListBean.FileListItem>> observable = RetrofitService.buildApiServices().getFilesList(SharedPreferenceUtils.getString("repo"), Constans.getCurrentPath().toString());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FileListBean.FileListItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<FileListBean.FileListItem> fileListItems) {
                        SharedPreferenceUtils.storeFileList(Constans.getCurrentPath().toString(),fileListItems);
                        onHandleSuccess(fileListItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onHandelFail(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onHandleSuccess(List<FileListBean.FileListItem> fileListItems) {
            iBasePresenter.onModelSuccess(fileListItems);
    }

    @Override
    public void onHandelFail(List<FileListBean.FileListItem> fileListItems) {
        iBasePresenter.onModelFail(null);
    }
}
