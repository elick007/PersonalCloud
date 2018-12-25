package com.elick.personalcloud.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.elick.personalcloud.base.View.IBaseView;
import com.elick.personalcloud.dialog.LoadingDialog;
import com.elick.personalcloud.utils.MyDebug;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private LoadingDialog loadingDialog;
    @Override
    protected void onResume() {
        super.onResume();
        String contextString = this.toString();
        MyDebug.d("current Activity = " + contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@")));
    }

    @Override
    public void showLoading() {
        if (loadingDialog==null){
            loadingDialog=new LoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.showLoadingView();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog!=null)
        loadingDialog.dismiss();
    }

    @Override
    public void showNetError() {
        loadingDialog.setCanceledOnTouchOutside(true);
        loadingDialog.showErrorView();
    }

    public void show_Toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void show_Dialog(int resLayout){

    }
}
