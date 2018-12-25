package com.elick.personalcloud.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elick.personalcloud.R;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int REFRESH_TYPE = 2000;
    private static final int HEADER_TYPE = 2001;
    private static final int NORMOL_TYPE = 2002;
    private View refreshView;
    private View headView;
    private View normalView;
    private List<T> mData;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private ViewGroup parentView;
    private int resLayoutId;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;
    private ItemHeaderClickListener itemHeaderClickListener;
    private ItemChildClickListener itemChildClickListener;

    public BaseAdapter(List<T> mData, Context context) {
        this.mData = mData;
        this.ctx = context;
        layoutInflater = LayoutInflater.from(context);
        resLayoutId = attachLayoutRes();
    }

    @Override
    public int getItemViewType(int position) {
        if (refreshView != null || headView != null) {
            if (position == 0)
                return REFRESH_TYPE;
            if (position == 1) {
                return HEADER_TYPE;
            }
            return NORMOL_TYPE;
        } else {
            return NORMOL_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case REFRESH_TYPE:
                break;
            case HEADER_TYPE:
                initClickListener((BaseViewHolder) viewHolder, i);
                break;
            case NORMOL_TYPE:
            default:
                convert((BaseViewHolder) viewHolder, mData.get(viewHolder.getLayoutPosition() - getHeaderCount()));
                initClickListener((BaseViewHolder) viewHolder, i);
        }
    }

    private void initClickListener(final BaseViewHolder viewHolder, final int i) {
        if (itemClickListener != null) {
            if (viewHolder.getItemViewType() == HEADER_TYPE) {
                if (itemHeaderClickListener != null) {
                    View view = viewHolder.itemView.getRootView();
                    itemHeaderClickListener.headerClick(view);
                }
            }
            if (viewHolder.getItemViewType() == NORMOL_TYPE) {
                if (itemChildClickListener != null) {
                    View view = viewHolder.itemView.getRootView();
                    itemChildClickListener.onClickChild(view, i-getHeaderCount());
                }
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(view, i - getHeaderCount());
                    }
                });
            }
        }
        if (itemLongClickListener != null) {
            if (viewHolder.getItemViewType() == NORMOL_TYPE) {
                viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        itemLongClickListener.onItemLongClick(view, i - getHeaderCount());
                        return true;
                    }
                });
            }
        }
    }

    protected abstract void convert(BaseViewHolder viewHolder, T t);

    private int getHeaderCount() {
        if (headView != null && refreshView != null) {
            return 2;
        } else return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (parentView == null) {
            parentView = viewGroup;
        }
        BaseViewHolder baseViewHolder;
        switch (viewType) {
            case REFRESH_TYPE:
                baseViewHolder = new BaseViewHolder(refreshView);
                break;
            case HEADER_TYPE:
                baseViewHolder = new BaseViewHolder(headView);
                break;
            case NORMOL_TYPE:
            default:
                baseViewHolder = onCreateDefViewHolder(parentView, viewType);
                // 设置用于单项刷新的tag标识
                //baseViewHolder.itemView.setTag(R.id.view_holder_tag, baseViewHolder);
                break;
        }
        return baseViewHolder;
    }

    private BaseViewHolder onCreateDefViewHolder(ViewGroup parentView, int viewType) {
        View defaultView = layoutInflater.inflate(resLayoutId, parentView, false);
        return new BaseViewHolder(defaultView);
    }

    @Override
    public int getItemCount() {
        if (refreshView != null && headView != null) {
            return mData.size() + 2;
        } else return mData.size();
    }

    protected abstract int attachLayoutRes();

    public void setRefreshView(View refreshView) {
        refreshView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.refreshView = refreshView;
    }

    public void setHeadView(View headView) {
        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.headView = headView;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setItemHeaderClickListener(ItemHeaderClickListener itemHeaderClickListener) {
        this.itemHeaderClickListener = itemHeaderClickListener;
    }

    public void setItemChildClickListener(ItemChildClickListener itemChildClickListener) {
        this.itemChildClickListener = itemChildClickListener;
    }

    public View getRefreshView() {
        return refreshView;
    }
}
