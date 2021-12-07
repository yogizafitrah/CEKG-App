package com.cloudteam.tesflask.API;

import com.cloudteam.tesflask.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Field("nama") String nama,
            @Field("jk") String jk,
            @Field("alamat") String alamat,
            @Field("tglrekam") String tglrekam,
            @Field("tspt") double tspt,
            @Field("bpm") double bpm,
            @Field("irr") double irr,
            @Field("irrlokal") double irrlokal,
            @Field("hr") String hr
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDeleteData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("get.php")
    Call<ResponseModel> ardGetData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("jk") String jk,
            @Field("alamat") String alamat,
            @Field("tglrekam") String tglrekam,
            @Field("tspt") String tspt,
            @Field("bpm") String bpm,
            @Field("irr") String irr,
            @Field("irrlokal") String irrlokal,
            @Field("hr") String hr
    );
}
