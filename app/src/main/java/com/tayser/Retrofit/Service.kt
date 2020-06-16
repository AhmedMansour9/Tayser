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
    @POST("creat_user_address")
    fun creat_user_address(
        @QueryMap map:Map<String,String>, @Header("Authorization")auth:String): Call<AddAdress_Response>

    @POST("edit_user_address")
    fun edit_user_address(
        @QueryMap map:Map<String,String>, @Header("Authorization")auth:String): Call<AddAdress_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("order_list")
    fun Myorders(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<MyOrders_Response>

    @POST("social_login")
    fun userLoginFacebook(
        @QueryMap map:Map<String,String>): Call<Register_Model>

    @POST("active_user_address")
    fun active_user_address(
        @QueryMap map:Map<String,String>, @Header("Authorization")auth:String): Call<AddAdress_Response>
    @POST("about")
    fun GetAboutus(@QueryMap queryMab: Map<String, String>): Call<About_Response>
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("contact_message")
    fun ContactUs(@QueryMap queryMab: Map<String, String>): Call<ContactUs_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("update_profile")
    fun EditProf(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("change_password")
    fun ChangePassword(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Edit_ProfileResponse>
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("user_profile")
    fun Profile(
        @QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<Profile_Response>

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
    @POST("create_order")
    fun create_order(
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
    @POST("order_list_product_details")
    fun getOrderProduct(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<OrderProduct_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("order_list_product_details")
    fun getOrderServices(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<OrderListService_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("list_cart_services_details")
    fun getServiceDetailsCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<CartServiceDetails_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("plus_quentity_Cart")
    fun PlusCart(@QueryMap map:Map<String,String>,@Header("Authorization")auth:String): Call<PlusCart_Response>



    @Multipart
    @POST("request_establishment")
    fun SentMessage(@Part img: MultipartBody.Part?,@Part img2: MultipartBody.Part?
                    ,@Part img3: MultipartBody.Part?,@Part img4: MultipartBody.Part?,
                    @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>
                    ,@Header("Authorization")auth:String): Call<AddAdress_Response>

    @Multipart
    @POST("request_cleannesses")
    fun request_cleannesses(@Part img: MultipartBody.Part?,@Part img2: MultipartBody.Part?
                    ,@Part img3: MultipartBody.Part?,@Part img4: MultipartBody.Part?,
                    @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>
                    ,@Header("Authorization")auth:String): Call<AddAdress_Response>

    @Multipart
    @POST("request_emergencies")
    fun request_emergencies(@Part img: MultipartBody.Part?,@Part img2: MultipartBody.Part?
                    ,@Part img3: MultipartBody.Part?,@Part img4: MultipartBody.Part?,
                    @PartMap map:HashMap<String,@JvmSuppressWildcards RequestBody>
                    ,@Header("Authorization")auth:String): Call<AddAdress_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("countries")
    fun getCountries(@QueryMap map:Map<String,String>): Call<Countries_Response>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("cities")
    fun getCities(@QueryMap map:Map<String,String>): Call<Countries_Response>
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("areas")
    fun getStates(@QueryMap map:Map<String,String>): Call<Countries_Response>


}