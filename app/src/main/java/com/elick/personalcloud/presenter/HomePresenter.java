package com.elick.personalcloud.presenter;

import com.elick.personalcloud.View.HomeActivity;
import com.elick.personalcloud.api.bean.FileListBean;
import com.elick.personalcloud.base.presenter.IBasePresenter;
import com.elick.personalcloud.model.HomeModel;
import com.elick.personalcloud.utils.MyDebug;

import java.util.List;

public class HomePresenter implements IBasePresenter<List<FileListBean.FileListItem>> {
    private HomeActivity homeActivity;
    private HomeModel homeModel;

    public HomePresenter(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        homeModel = new HomeModel(this);
    }

    @Override
    public void getData() {

    }

    @Override
    public void saveData() {

    }

    public void onRefresh() {
        homeModel.getFileList(true);
    }

    public void getFileList() {
        homeActivity.showLoading();
        homeModel.getFileList(false);
    }

    public void createNewDir(String absolutePath) {
        homeActivity.showLoading();
        homeModel.mkDir(absolutePath);
    }

    @Override
    public void onModelSuccess(List<FileListBean.FileListItem> list) {
        homeActivity.hideLoading();
        homeActivity.refreshRecyclerView(list);
    }

    @Override
    public void onModelFail(List<FileListBean.FileListItem> list) {
        homeActivity.hideLoading();
        homeActivity.show_Toast("加载出错");
    }
}
