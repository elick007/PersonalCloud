package com.elick.personalcloud.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.elick.personalcloud.R;
import com.elick.personalcloud.View.fragments.FileListFragment;
import com.elick.personalcloud.api.bean.FileListBean;
import com.elick.personalcloud.config.Constans;
import com.elick.personalcloud.presenter.HomePresenter;
import com.elick.personalcloud.utils.DoubleClickUtil;
import com.elick.personalcloud.utils.MyDebug;
import com.elick.personalcloud.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    private static int LOGIN_ACT = 1000;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.refresh)
    ImageView refresh;
    @BindView(R.id.download_manage)
    ImageView downloadManage;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private FileListFragment fileListFragment;
    private HomePresenter homePresenter = new HomePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //initRecyclerViewContainer();
    }

    private void initRecyclerViewContainer() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fileListFragment = new FileListFragment();
        fragmentTransaction.add(fragmentContainer.getId(), fileListFragment);
        fragmentTransaction.commit();
    }


    //交fregment
    public void refreshRecyclerView(List<FileListBean.FileListItem> fileList) {
        fileListFragment.updateRecyclerView(fileList);
    }

    public void onRefresh() {
        homePresenter.onRefresh();
    }

    public void getFileList() {
        homePresenter.getFileList();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOGIN_ACT) {
            if (resultCode == RESULT_OK) {
                initRecyclerViewContainer();
                getFileList();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (Constans.getCurrentPath().toString().equals("") || Constans.getCurrentPath().toString().equals("/")) {
            if (DoubleClickUtil.canClick(System.currentTimeMillis())) {
                finish();
            } else {
                ToastUtils.showToast(this, "再按一次退出");
            }
        } else {
            Constans.getCurrentPath().delete(Constans.getCurrentPath().length() - 1, Constans.getCurrentPath().length());
            Constans.getCurrentPath().delete(Constans.getCurrentPath().lastIndexOf("/") + 1, Constans.getCurrentPath().length());
            getFileList();
        }
    }

    @OnClick({R.id.menu, R.id.userImage, R.id.userName, R.id.refresh, R.id.download_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu:
                break;
            case R.id.userImage:
            case R.id.userName:
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_ACT);
                break;
            case R.id.refresh:
                break;
            case R.id.download_manage:
                break;
        }
    }
}
