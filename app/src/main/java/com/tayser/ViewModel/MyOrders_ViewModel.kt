package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.MyOrders_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MyOrders_ViewModel : ViewModel() {
    private var No_Product:Boolean=false

     var listProductsMutableLiveData: MutableLiveData<MyOrders_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false
//    var listOrderDetailssMutableLiveData: MutableLiveData<OrderDetails_Response>? = null

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        auth:String,
        context: Context
    ): LiveData<MyOrders_Response> {
        listProductsMutableLiveData = MutableLiveData<MyOrders_Response>()
        this.context = context
        getDataValues(auth)
        return listProductsMutableLiveData as MutableLiveData<MyOrders_Response>
    }
//    public fun getorderDetails(
//        auth:String,
//        orderid:String,
//        context: Context
//    ): LiveData<OrderDetails_Response> {
//        listOrderDetailssMutableLiveData = MutableLiveData<OrderDetails_Response>()
//        this.context = context
//        getOrderdetails(orderid,auth)
//        return listOrderDetailssMutableLiveData as MutableLiveData<OrderDetails_Response>
//    }


    private fun getDataValues(auth:String) {
        var map= HashMap<String,String>()
        map.put("lang", "en")

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Myorders(map,"Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<MyOrders_Response> {
            override fun onResponse(call: Call<MyOrders_Response>, response: Response<MyOrders_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    No_Product=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<MyOrders_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
//    private fun getOrderdetails(orderid:String,auth:String) {
//        var map= HashMap<String,String>()
////        map.put("lang", "en")
//        map.put("order_id", orderid)
//
//        var service = ApiClient.getClient()?.create(Service::class.java)
//        val call = service?.MyordersDetails(map,"Bearer "+auth)
//        call?.enqueue(object : Callback, retrofit2.Callback<OrderDetails_Response> {
//            override fun onResponse(call: Call<OrderDetails_Response>, response: Response<OrderDetails_Response>) {
//
//                if (response.code() == 200) {
//                    listOrderDetailssMutableLiveData?.setValue(response.body()!!)
//
//                } else  {
//                    listOrderDetailssMutableLiveData?.setValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<OrderDetails_Response>, t: Throwable) {
//                listOrderDetailssMutableLiveData?.setValue(null)
//
//            }
//        })
//    }
}