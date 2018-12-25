package com.elick.personalcloud.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.elick.personalcloud.R;
import com.elick.personalcloud.api.bean.FileListBean;
import com.elick.personalcloud.utils.MyDebug;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileListAdapter extends BaseAdapter<FileListBean.FileListItem> {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:ss");
    public FileListAdapter(List<FileListBean.FileListItem> mData, Context context) {
        super(mData, context);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, FileListBean.FileListItem fileListItem) {
        if (fileListItem.getType().equals("dir")){
            viewHolder.setImageResource(R.id.fileTypeImage,R.drawable.folder);
        }else {
            String fileName=fileListItem.getName();
            String str=fileName.substring(fileName.lastIndexOf(".")+1);
            int imgId=getFileTypeImg(str);
            viewHolder.setImageResource(R.id.fileTypeImage,imgId);
            viewHolder.setText(R.id.fileSize,getFileSize(fileListItem.getSize())+"  ");
        }
        viewHolder.setText(R.id.fileOrDirName,fileListItem.getName());
        viewHolder.setText(R.id.fileRecentTime,simpleDateFormat.format(new Date(fileListItem.getmTime())));
    }

    private int getFileTypeImg(String name) {
        if (TextUtils.isEmpty(name)) {
            return R.drawable.file;
        }
        String lowerStr=name.toLowerCase();
        switch (lowerStr) {
            case "mp3":
            case "aac":
                return R.drawable.music;
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                return R.drawable.pic;
            case "avi":
            case "mp4":
            case "mpeg":
            case "rmvb":
                return R.drawable.video;
            case "doc":
            case "docx":
                return R.drawable.word;
            case "excl":
                return R.drawable.excel;
            case "pdf":
                return R.drawable.pdf;
            case "ppt":
                return R.drawable.ppt;
            case "txt":
                return R.drawable.txt;
            case "rar":
            case "zip":
                return R.drawable.zip;
        }
        return R.drawable.file;
    }

    private CharSequence getFileSize(long size) {
        long fileSize=size;
        int degreed=0;
        while (fileSize>1024){
            degreed++;
            fileSize/=1024;
        }
        String strSize;
        switch (degreed){
            case 0:
                strSize= fileSize+"B";
                break;
            case 1:
                strSize= fileSize+"KB";
                break;
            case 2:
                strSize=fileSize+"MB";
                break;
            default:
                strSize=fileSize+"GB";
                break;
        }
        return strSize;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.list_item_layout;
    }


}
