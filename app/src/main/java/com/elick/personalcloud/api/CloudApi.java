package com.elick.personalcloud.api;

import com.elick.personalcloud.api.bean.AuthBean;
import com.elick.personalcloud.api.bean.DefaultRepo;
import com.elick.personalcloud.api.bean.FileListBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import io.reactivex.Observable;

public interface CloudApi {
    /**
     * @param username
     * @param password
     * @return return {"token":"xxx" }
     */
    @FormUrlEncoded
    @POST("api2/auth-token/")
    Observable<AuthBean> loginToGetToken(@Field(value = "username",encoded = true) String username, @Field(value = "password",encoded = true) String password);

    /**
     * 获取默认repo
     * @return
     */
    @GET("api2/default-repo/")
    Observable<DefaultRepo> getDefaultRepo();

    /**
     * 获取路径下所以文件
     * @param repoId
     * @return
     */
    @GET("api2/repos/{repoId}/dir/")
    Observable<List<FileListBean.FileListItem>> getFilesList(@Path("repoId") String repoId,
                                                             @Query("p") String dirPath);

    /**
     * 创建新的文件夹
     * @param repoId
     * @param absolutePath  绝对路径
     * @param operation
     * @return
     */
    @FormUrlEncoded
    @POST("api2/repos/{repoId}/dir/?p={absolutePath}")
    Observable<String> createNewDir(@Path("repoId") String repoId,
                                    @Path("absolutePath") String absolutePath,
                                    @Field("operation") String operation);

    /**
     * 重命名文件夹
     * @param repoId
     * @param absolutePath
     * @param operation
     * @param newname
     * @return
     */
    @FormUrlEncoded
    @POST("api2/repos/{repoId}/dir/?p={absolutePath}")
    Observable<String> renameDir(@Path("repoId") String repoId,
                                 @Path("absolutePath") String absolutePath,
                                 @Field("operation") String operation,
                                 @Field("newname") String newname);

    /**
     * 单个或多个删除文件
     * @param repoId
     * @param absolutePath
     * @param file_names
     * @return
     */
    @FormUrlEncoded
    @POST("api2/repos/{repoId}/fileops/delete/?p={absolutePath}")
    Observable<String> multiDelFiles(@Path("repoId") String repoId,
                                     @Path("absolutePath") String absolutePath,
                                     @Field("file_names") String file_names);

    /**
     * 单个或多个复制文件
     * @param Authorization
     * @param repoId
     * @param absolutePath
     * @param dst_repo
     * @param dst_dir
     * @param file_names
     * @return
     */
    @FormUrlEncoded
    @Headers("Accept: application/json; indent=4")
    @POST("api2/repos/{repoId}/fileops/copy/?p={absolutePath}")
    Observable<String> multiCopyFiles(@Header("Authorization") String Authorization,
                                      @Path("repoId") String repoId,
                                      @Path("absolutePath") String absolutePath,
                                      @Field("dst_repo") String dst_repo,
                                      @Field("dst_dir") String dst_dir,
                                      @Field("file_names") String file_names);

    /**
     * 单个或多个复制文件
     * @param Authorization
     * @param repoId
     * @param absolutePath
     * @param dst_repo
     * @param dst_dir
     * @param file_names
     * @return
     */
    @FormUrlEncoded
    @Headers("Accept: application/json; indent=4")
    @POST("api2/repos/{repoId}/fileops/move/?p={absolutePath}")
    Observable<String> multiMoveFiles(@Header("Authorization") String Authorization,
                                      @Path("repoId") String repoId,
                                      @Path("absolutePath") String absolutePath,
                                      @Field("dst_repo") String dst_repo,
                                      @Field("dst_dir") String dst_dir,
                                      @Field("file_names") String file_names);

    /**
     * 多个文件下载获取ziptoken eg: c2778e64-9575-4ac1-a790-3adb2726f9c5
     * 获取后下载地址为http://104.206.144.11:8082/zip/c2778e64-9575-4ac1-a790-3adb2726f9c5
     * @param Authorization
     * @param repoId
     * @param parent_dir
     * @param fileMap
     * @return
     */
    @FormUrlEncoded
    @Headers("Accept: application/json; charset=utf-8; indent=4")
    @GET("api/v2.1/repos/{repoId}/zip-task/")
    Observable<String> multiDownloadGetZipToken(@Header("Authorization") String Authorization,
                                                @Path("repoId") String repoId,
                                                @Query("parent_dir") String parent_dir,
                                                @QueryMap Map<String,String> fileMap);

    /**
     * 获取单个文件下载URL，一个小时内有效
     * @param Authorization
     * @param repoId
     * @param absolutePath
     * @return
     */
    @FormUrlEncoded
    @Headers("Accept: application/json; charset=utf-8; indent=4")
    @GET("api2/repos/{repoId}/fileops/move/?p={absolutePath}&reuse=1")
    Observable<String> singleFileDownloadUrl(@Header("Authorization") String Authorization,
                                             @Path("repoId") String repoId,
                                             @Path("absolutePath") String absolutePath);
}
