package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.ListAddress_Response
import com.tayser.Model.Services_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class GetAddress_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<ListAddress_Response>? = null
    private lateinit var context: Context

    public var listAllsizesMutableLiveData: MutableLiveData<ListAddress_Response>? = null

    fun getData(token:String,lang:String ,context: Context): LiveData<ListAddress_Response> {
        listProductsMutableLiveData = MutableLiveData<ListAddress_Response>()
        this.context = context
        getDataValues(token,lang)
        return listProductsMutableLiveData as MutableLiveData<ListAddress_Response>
    }

    private fun getDataValues(token:String,lang:String) {
        var map= HashMap<String,String>()

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ListAddress("Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<ListAddress_Response> {
            override fun onResponse(call: Call<ListAddress_Response>, response: Response<ListAddress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<ListAddress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}