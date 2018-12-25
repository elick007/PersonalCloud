package com.elick.personalcloud.base.presenter;

import com.elick.personalcloud.base.Model.IBaseModel;
import com.elick.personalcloud.base.View.IBaseView;

public interface IBasePresenter<T> {
    void getData();
    void saveData();
    void onModelSuccess(T t);
    void onModelFail(T t);
}
