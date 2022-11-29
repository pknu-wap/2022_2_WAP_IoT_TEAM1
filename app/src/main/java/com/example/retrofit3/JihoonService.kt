package com.example.retrofit3

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class ResponseDTO(var result:String? = null)

interface JihoonService {
    @GET("/jihoon")
    fun getRequest(@Query("name") name:String): Call<ResponseDTO>

    @GET("/jihoon/{id}")
    fun getParamRequest(@Path("id") id: String): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("/jihoon")
    fun postRequest(@Field("id") id: String,
                    @Field("password") password: String): Call<ResponseDTO>

    @FormUrlEncoded
    @PUT("/jihoon/{id}")
    fun putRequest(@Path("id") id: String,
                   @Field("content") content: String): Call<ResponseDTO>

    @DELETE("/jihoon/{id}")
    fun deleteRequest(@Path("id") id: String): Call<ResponseDTO>
}