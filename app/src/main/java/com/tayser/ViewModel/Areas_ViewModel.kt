package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.Countries_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Areas_ViewModel  : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Countries_Response>? = null
    private lateinit var context: Context


    fun getData( City_Id:String,lang:String,context: Context): LiveData<Countries_Response> {
        listProductsMutableLiveData = MutableLiveData<Countries_Response>()
        this.context = context
        getDataValues(City_Id,lang)
        return listProductsMutableLiveData as MutableLiveData<Countries_Response>
    }

    private fun getDataValues(City_Id:String,lang:String) {
        var map= HashMap<String,String>()
        map.put("city_id", City_Id)
        map.put("lang", lang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getStates(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Countries_Response> {
            override fun onResponse(
                call: Call<Countries_Response>,
                response: Response<Countries_Response>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Countries_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }




}

