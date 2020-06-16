package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.Profile_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Profile_ViewModel: ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Profile_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        auth:String,
        context: Context
    ): LiveData<Profile_Response> {
        listProductsMutableLiveData = MutableLiveData<Profile_Response>()
        this.context = context
        getDataValues(auth)
        return listProductsMutableLiveData as MutableLiveData<Profile_Response>
    }


    private fun getDataValues(auth:String) {
        var map= HashMap<String,String>()
            map.put("language", "en")

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Profile(map,"Bearer "+auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Profile_Response> {
            override fun onResponse(call: Call<Profile_Response>, response: Response<Profile_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Profile_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}