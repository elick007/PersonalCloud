package com.elick.personalcloud.base.Model;

public interface IBaseModel<T> {
    void onHandleSuccess(T t);
    void onHandelFail(T t);
}
