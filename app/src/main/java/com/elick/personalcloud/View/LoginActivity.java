package com.elick.personalcloud.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.elick.personalcloud.R;
import com.elick.personalcloud.presenter.LoginPresenter;
import com.elick.personalcloud.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.img_account)
    ImageView imgAccount;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.img_pw)
    ImageView imgPw;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_see_password)
    ImageView ivSeePassword;
    @BindView(R.id.checkBox_password)
    CheckBox checkBoxPassword;
    @BindView(R.id.checkBox_login)
    CheckBox checkBoxLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    LoginPresenter loginPresenter=new LoginPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_see_password, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_see_password:
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(etAccount.getText())&&TextUtils.isEmpty(etPassword.getText())){
                    ToastUtils.showToast(LoginActivity.this,"账号密码为空");
                }else {
                    String account=etAccount.getText().toString().trim();
                    String passwd=etPassword.getText().toString().trim();
                    loginPresenter.startLogin(account,passwd);
                }
                break;
        }
    }
    public void loginSuccess(){
        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
        this.setResult(RESULT_OK,intent);
        finish();
    }
}
