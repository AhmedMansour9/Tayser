package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.*
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Cart_ViewModel : ViewModel()
{
     var listProductsMutableLiveData: MutableLiveData<Cart_Response>? = null
    var PlusProductsMutableLiveData: MutableLiveData<PlusCart_Response>? = null
    var CartServiceMutableLiveData: MutableLiveData<CartService_Response>? = null
    var CartServiceDetailsMutableLiveData: MutableLiveData<CartServiceDetails_Response>? = null
    var OrderListMutableLiveData: MutableLiveData<OrderProduct_Response>? = null
    var OrderListServiceMutableLiveData: MutableLiveData<OrderListService_Response>? = null

    private lateinit var context: Context
     lateinit var call:Any
     fun getData(
        auth:String,
        lang:String,
        context: Context
    ): LiveData<Cart_Response> {
        listProductsMutableLiveData = MutableLiveData<Cart_Response>()
        this.context = context
        getDataValues(auth,lang)
        return listProductsMutableLiveData as MutableLiveData<Cart_Response>
    }
    fun getDataService(
        auth:String,
        lang:String,
        context: Context
    ): LiveData<CartService_Response> {
        CartServiceMutableLiveData = MutableLiveData<CartService_Response>()
        this.context = context
        getDataServiceValues(auth,lang)
        return CartServiceMutableLiveData as MutableLiveData<CartService_Response>
    }

    fun getOrderProducts(
        auth:String,
        order_id:String,
        context: Context
    ): LiveData<OrderProduct_Response> {
        OrderListMutableLiveData = MutableLiveData<OrderProduct_Response>()
        this.context = context
        getDataOrderProducts(auth,order_id)
        return OrderListMutableLiveData as MutableLiveData<OrderProduct_Response>
    }

    fun getOrderService(
        auth:String,
        order_id:String,
        context: Context
    ): LiveData<OrderListService_Response> {
        OrderListServiceMutableLiveData = MutableLiveData<OrderListService_Response>()
        this.context = context
        getDataOrderServices(auth,order_id)
        return OrderListServiceMutableLiveData as MutableLiveData<OrderListService_Response>
    }

    fun getDataDetailsService(
        cart_service_id:String,
        auth:String,
        lang:String,
        context: Context
    ): LiveData<CartServiceDetails_Response> {
        CartServiceDetailsMutableLiveData = MutableLiveData<CartServiceDetails_Response>()
        this.context = context
        getDataServiceDetailsValues(cart_service_id,auth,lang)
        return CartServiceDetailsMutableLiveData as MutableLiveData<CartServiceDetails_Response>
    }
    public fun DeleteData(
        auth:String,
        lang:String,
        cart_id:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getDeleteCart(    auth,lang,cart_id)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }

    public fun DeleteService(
        auth:String,
        lang:String,
        cart_id:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getDeleteCartService(    auth,lang,cart_id)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }
     fun AddPlusData(
        auth:String,
        lang:String,
        cart_id:String,
        Type:String,
        context: Context
    ): LiveData<PlusCart_Response> {
        PlusProductsMutableLiveData = MutableLiveData<PlusCart_Response>()
        this.context = context
        getPlusCart(    auth,lang,cart_id,Type)
        return PlusProductsMutableLiveData as MutableLiveData<PlusCart_Response>
    }

    private fun getDataValues(auth:String,lang:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCart( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Cart_Response> {
            override fun onResponse(
                call: Call<Cart_Response>,
                response: Response<Cart_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Cart_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDataServiceValues(auth:String,lang:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getServiceCart( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<CartService_Response> {
            override fun onResponse(
                call: Call<CartService_Response>,
                response: Response<CartService_Response>
            ) {

                if (response.code() == 200) {
                    CartServiceMutableLiveData?.setValue(response.body()!!)

                } else {
                    CartServiceMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<CartService_Response>, t: Throwable) {
                CartServiceMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDataOrderServices(auth:String,order_id:String ) {
        var map = HashMap<String, String>()
        map.put("order_id",order_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getOrderServices( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<OrderListService_Response> {
            override fun onResponse(
                call: Call<OrderListService_Response>,
                response: Response<OrderListService_Response>
            ) {

                if (response.code() == 200) {
                    OrderListServiceMutableLiveData?.setValue(response.body()!!)

                } else {
                    OrderListServiceMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<OrderListService_Response>, t: Throwable) {
                OrderListServiceMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDataOrderProducts(auth:String,order_id:String ) {
        var map = HashMap<String, String>()
        map.put("order_id",order_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getOrderProduct( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<OrderProduct_Response> {
            override fun onResponse(
                call: Call<OrderProduct_Response>,
                response: Response<OrderProduct_Response>
            ) {

                if (response.code() == 200) {
                    OrderListMutableLiveData?.setValue(response.body()!!)

                } else {
                    OrderListMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<OrderProduct_Response>, t: Throwable) {
                OrderListMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDataServiceDetailsValues(cart_service_id:String,auth:String,lang:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_service_id",cart_service_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getServiceDetailsCart( map,"Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<CartServiceDetails_Response> {
            override fun onResponse(
                call: Call<CartServiceDetails_Response>,
                response: Response<CartServiceDetails_Response>
            ) {

                if (response.code() == 200) {
                    CartServiceDetailsMutableLiveData?.setValue(response.body()!!)

                } else {
                    CartServiceDetailsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<CartServiceDetails_Response>, t: Throwable) {
                CartServiceDetailsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getPlusCart(auth:String,lang:String,cart_id:String,Type:String ) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        if(Type.equals("minus")){
             call = service?.MinusCart( map,"Bearer " + auth)!!
        }else {
            call = service?.PlusCart( map,"Bearer " + auth)!!
        }

        (call as Call<PlusCart_Response>).enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }

    private fun getDeleteCart(auth:String,lang:String,cart_id:String) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
       val  call = service?.DeleteCart( map,"Bearer " + auth)!!

        call.enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getDeleteCartService(auth:String,lang:String,cart_id:String) {
        var map = HashMap<String, String>()
        map.put("lang",lang)
        map.put("cart_id",cart_id)
        var service = ApiClient.getClient()?.create(Service::class.java)
        val  call = service?.DeleteServiceCart( map,"Bearer " + auth)!!

        call.enqueue(object : Callback, retrofit2.Callback<PlusCart_Response> {
            override fun onResponse(
                call: Call<PlusCart_Response>,
                response: Response<PlusCart_Response>
            ) {

                if (response.code() == 200) {
                    PlusProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    PlusProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<PlusCart_Response>, t: Throwable) {
                PlusProductsMutableLiveData?.setValue(null)

            }
        })
    }


}