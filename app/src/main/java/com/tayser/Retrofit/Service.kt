package com.tayser.Retrofit

import com.tayser.Model.Register_Model
import com.tayser.Model.Sections_Response
import com.tayser.Model.SliderHome_Model
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST("register")
     fun userRegister(
        @QueryMap map:Map<String,String>): Call<Register_Model>
////
    @POST("login")
    fun userLogin(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("slider")
    fun SLider(
        @QueryMap map:Map<String,String>): Call<SliderHome_Model>
    @POST("sliders_sections")
    fun Products_SLider(
        @QueryMap map:Map<String,String>): Call<SliderHome_Model>

    @POST("sections")
    fun Categories(
        @QueryMap map:Map<String,String>): Call<Sections_Response>


}