package com.elick.personalcloud.base.Model;

import com.elick.personalcloud.base.presenter.IBasePresenter;

public abstract class BaseModel<P extends IBasePresenter,T> implements IBaseModel<T> {
    protected P iBasePresenter;
    protected abstract void loadByLocal();
    protected abstract void loadByNetwork();
}
