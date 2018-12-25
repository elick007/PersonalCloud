package com.elick.personalcloud.api.bean;

import java.util.List;

public class FileListBean {
    private List<FileListItem> fileList;

    public List<FileListItem> getFileList() {
        return fileList;
    }

    public static class FileListItem{
        private String permission;
        private long mtime;
        private String type;
        private String name;
        private String id;
        private long size;

        public long getSize() {
            return size;
        }

        public String getPermission() {
            return permission;
        }

        public long getmTime() {
            return mtime*1000L;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }
}
