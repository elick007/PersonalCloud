package com.elick.personalcloud.View.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elick.personalcloud.R;
import com.elick.personalcloud.View.HomeActivity;
import com.elick.personalcloud.adapter.FileListAdapter;
import com.elick.personalcloud.adapter.ItemChildClickListener;
import com.elick.personalcloud.adapter.ItemClickListener;
import com.elick.personalcloud.adapter.ItemHeaderClickListener;
import com.elick.personalcloud.adapter.ItemLongClickListener;
import com.elick.personalcloud.api.bean.FileListBean;
import com.elick.personalcloud.base.View.BaseFragment;
import com.elick.personalcloud.base.View.BaseView;
import com.elick.personalcloud.config.Constans;
import com.elick.personalcloud.utils.ToastUtils;
import com.elick.personalcloud.widget.RefreshView;
import com.elick.personalcloud.widget.SwipeRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FileListFragment extends BaseFragment{
    @BindView(R.id.swipe_refresh_recycler_view)
    SwipeRefreshRecyclerView swipeRefreshRecyclerView;
    @BindView(R.id.long_click_select_count)
    TextView longClickSelectCount;
    @BindView(R.id.long_click_check_all)
    TextView longClickCheckAll;
    @BindView(R.id.long_click_check_cancel)
    TextView longClickCheckCancel;
    @BindView(R.id.popup_operate_up_view)
    RelativeLayout popupOperateUpView;
    @BindView(R.id.long_click_delete)
    TextView longClickDelete;
    @BindView(R.id.long_click_check_move)
    TextView longClickCheckMove;
    @BindView(R.id.long_click_check_rename)
    TextView longClickCheckRename;
    @BindView(R.id.long_click_check_copy)
    TextView longClickCheckCopy;
    @BindView(R.id.long_click_check_download)
    TextView longClickCheckDownload;
    @BindView(R.id.popup_operate_bottom_view)
    LinearLayout popupOperateBottomView;
    private HomeActivity homeActivity;
    private Unbinder unbinder;
    private FileListAdapter fileListAdapter;
    private List<FileListBean.FileListItem> mData = new ArrayList<>();
    private boolean isSwipeToRefresh;
    private Set<Integer> set = new HashSet<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.file_list_fragment_layout, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        initClickListener();
        return rootView;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        return 0;
    }

    private void initClickListener() {
        fileListAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Constans.getCurrentPath().append(mData.get(position).getName()).append("/");
                homeActivity.getFileList();
            }
        });
        fileListAdapter.setItemHeaderClickListener(new ItemHeaderClickListener() {
            @Override
            public void headerClick(View parent) {
                View view = parent.findViewById(R.id.header_btn);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(FileListFragment.this.getContext(), "btn");
                    }
                });
            }
        });
        fileListAdapter.setItemChildClickListener(new ItemChildClickListener() {
            @Override
            public void onClickChild(View view, final int position) {
                CheckBox checkBox = view.findViewById(R.id.choose_item);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            set.add(position);
                        } else {
                            set.remove(position);
                        }
                    }
                });
            }
        });
        fileListAdapter.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                CheckBox checkBox = view.findViewById(R.id.choose_item);
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    private void initRecyclerView() {
        fileListAdapter = new FileListAdapter(mData, getContext());
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_item_layout, null);
        fileListAdapter.setHeadView(headView);
        RefreshView refreshView = new RefreshView(getContext());
        fileListAdapter.setRefreshView(refreshView);
        swipeRefreshRecyclerView.setAdapter(fileListAdapter);
        swipeRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshRecyclerView.setOnRefreshListener(new SwipeRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void refreshToGetData() {
                isSwipeToRefresh = true;
                homeActivity.onRefresh();
            }
        });
    }


    public void updateRecyclerView(List<FileListBean.FileListItem> fileList) {
        if (isSwipeToRefresh) {
            swipeRefreshRecyclerView.OnRefreshFinish();
        }
        mData.clear();
        mData.addAll(fileList);
        fileListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.long_click_check_all, R.id.long_click_check_cancel, R.id.long_click_delete, R.id.long_click_check_move, R.id.long_click_check_rename, R.id.long_click_check_copy, R.id.long_click_check_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.long_click_check_all:
                break;
            case R.id.long_click_check_cancel:
                break;
            case R.id.long_click_delete:
                break;
            case R.id.long_click_check_move:
                break;
            case R.id.long_click_check_rename:
                break;
            case R.id.long_click_check_copy:
                break;
            case R.id.long_click_check_download:
                break;
        }
    }
}
