package com.tayser.Retrofit

import com.tayser.Model.*
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
    fun Sub_SLider(
        @QueryMap map:Map<String,String>): Call<SliderHome_Model>

    @POST("products_images")
    fun Products_SLider(
        @QueryMap map:Map<String,String>): Call<SliderHome_Model>


    @POST("sections")
    fun Categories(
        @QueryMap map:Map<String,String>): Call<Sections_Response>

    @POST("products")
    fun ProductsByCatId(
        @QueryMap map:Map<String,String>): Call<Product_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("add_cart")
    fun AddToCart(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<AddToCart_Response>


}