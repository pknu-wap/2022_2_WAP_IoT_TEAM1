package com.example.retrofit3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // http://119.198.44.55:7000
        // http://192.168.0.29:7000
        // http://172.20.10.5:7000

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.20.10.5:7000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(JihoonService::class.java)

        button_get.setOnClickListener {
            server.getRequest("wlgns").enqueue(object :Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

            })
        }

        button_get_param.setOnClickListener {
            server.getParamRequest("board01").enqueue(object :Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

            })
        }

        button_post.setOnClickListener {
            server.postRequest("aa67007258@gmail.com","1234").enqueue(object :Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

            })
        }

        button_update.setOnClickListener {
            server.putRequest("board01","수정할 내용").enqueue(object :Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

            })
        }

        button_delete.setOnClickListener {
            server.deleteRequest("board01").enqueue(object :Callback<ResponseDTO> {
                override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                    println(response?.body().toString())
                }

                override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {

                }

            })
        }
    }
}