package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.AddToCart_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddToCart_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AddToCart_Response>? = null
    private lateinit var context: Context


     fun getData(
        auth:String,
        product_id: String,
        product_quantity:String,
        context: Context
    ): LiveData<AddToCart_Response> {
        listProductsMutableLiveData = MutableLiveData<AddToCart_Response>()
        this.context = context
        getDataValues(auth,product_id,product_quantity)
        return listProductsMutableLiveData as MutableLiveData<AddToCart_Response>
    }


    private fun getDataValues(auth:String,product_id: String,product_quantity:String) {
        var map = HashMap<String, String>()
            map.put("product_id", product_id)
            map.put("product_quantity", product_quantity)

         var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.AddToCart(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<AddToCart_Response> {
            override fun onResponse(
                call: Call<AddToCart_Response>,
                response: Response<AddToCart_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddToCart_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }




}