package com.example.test1026

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class ResponseDTO(var name:String? = null,
                       var phone:String? = null,
                       var address:String? = null,
                       var symptom:String? = null)

interface RetrofitService {

    // get(환자)
    @GET("/jihoon")
    fun getRequest(@Query("name") name:String,
                       @Query("number") number:String,
                       @Query("address") address:String,
                       @Query("symptom") symptom:String) : Call<ResponseDTO>

    // put(환자)
    @FormUrlEncoded
    @PUT("/jihoon2")
    fun putRequest( @Field("name") name:String,
                    @Field("number") number: String,
                    @Field("address") address: String,
                    @Field("symptom") symptom: String) : Call<ResponseDTO>

    // get(보호자)

    @GET("/jihoon3")
    fun GgetRequest(@Query("name") name: String,
                    @Query("number") number: String):Call<ResponseDTO>

    // put(보호자)

    @FormUrlEncoded
    @PUT("/jihoon4")
    fun GputRequest(@Field("name") name: String,
                    @Field("number") number: String):Call<ResponseDTO>














}