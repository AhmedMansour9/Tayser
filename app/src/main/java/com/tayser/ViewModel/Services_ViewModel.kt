package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.Services_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Services_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Services_Response>? = null
    private lateinit var context: Context

    public var listAllsizesMutableLiveData: MutableLiveData<Services_Response>? = null

     fun getData(product_id:String,lang:String ,context: Context): LiveData<Services_Response> {
        listProductsMutableLiveData = MutableLiveData<Services_Response>()
        this.context = context
        getDataValues(product_id,lang)
        return listProductsMutableLiveData as MutableLiveData<Services_Response>
    }

    private fun getDataValues(product_id: String,lang:String) {
        var map= HashMap<String,String>()
        map.put("section_id",product_id)
        map.put("lang",lang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ServicesByCatId(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Services_Response> {
            override fun onResponse(call: Call<Services_Response>, response: Response<Services_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Services_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }



}