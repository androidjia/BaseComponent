package com.jjs.zero.httplibrary.api;

import com.jjs.zero.httplibrary.dto.Order;
import com.jjs.zero.httplibrary.httpService.BaseResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/24
 * @Details: <功能描述>
 */
public interface RequestApi {

    @FormUrlEncoded
    @POST("/ghc-consumer-api/api/market/order")
    Observable<BaseResult<Order>> getOrder(@Field("productId")Long productId, @Field("fromType")Byte fromType,
                                           @Field("doctorCode")String doctorCode, @Field("price")String price,
                                           @Field("payType") Byte payType, @Field("interId")Long interId);

    @POST("/ghc-consumer-api/api/userPackage/manage/activate")
    Observable<BaseResult<Order>> activeMeal(@Body Order custPackageCode);

    @GET("/ghc-consumer-api/api/userPackage/manage/selectList")
    Observable<BaseResult<List<Order>>> queryAllPacks(@Query("useStatus") int status);

    /**
     * 文件下载
     * @param fileUrl
     * @return
     */
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @PUT("/ghc-consumer-api/api/personExperience.activation")
    Observable<BaseResult<Order>> getExperiencesActive(@Query("experienceStateId") Long experienceStateId);
}
