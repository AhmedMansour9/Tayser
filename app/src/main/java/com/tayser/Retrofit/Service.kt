package com.tayser.Retrofit

import com.tayser.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @POST("services")
    fun ServicesByCatId(
        @QueryMap map:Map<String,String>): Call<Services_Response>

    @POST("list_user_address")
    fun ListAddress(
        @Header("Authorization")auth:String): Call<ListAddress_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("add_cart")
    fun AddToCart(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<AddToCart_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("add_cart_services")
    fun AddToCartService(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<AddToCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("delete_cart")
    fun DeleteCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("delete_cart_services")
    fun DeleteServiceCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("min_quentity_Cart")
    fun MinusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("list_data_cart")
    fun getCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Cart_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("list_cart_services")
    fun getServiceCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<CartService_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("list_cart_services_details")
    fun getServiceDetailsCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<CartServiceDetails_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("plus_quentity_Cart")
    fun PlusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>



    @Multipart
    @POST("request_establishment")
    fun SentMessage(@Part img: MultipartBody.Part?,@Part img2: MultipartBody.Part?
                    ,@Part img3: MultipartBody.Part?,@Part img4: MultipartBody.Part?, @Part("message") description: RequestBody, @Part("shop_id") id: RequestBody,
                    @Header("Authorization")auth:String): Call<AddAdress_Response>

}