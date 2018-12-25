package com.elick.personalcloud.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.elick.personalcloud.R;
import com.elick.personalcloud.widget.LVCircularSmile;

public class LoadingDialog extends Dialog {
    private int loadingViewId;
    private int errorViewId;

    public LoadingDialog(@NonNull Context context) {
        super(context,R.style.Loading_Dialog_Style);
        if (loadingViewId == 0) {
            loadingViewId = R.layout.loading_dialog_layout;
        }
        if (errorViewId == 0) {
            errorViewId = R.layout.dialog_error_layout;
        }
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoadingView() {
        setContentView(loadingViewId);
        LVCircularSmile lvCircularSmile = findViewById(R.id.loading_smile);
        lvCircularSmile.setViewColor(R.color.colorPrimary);
        lvCircularSmile.startAnim();
        this.show();
    }

    public void showErrorView() {
        setContentView(errorViewId);
        this.show();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
