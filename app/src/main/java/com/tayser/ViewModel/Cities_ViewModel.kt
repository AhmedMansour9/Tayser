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

class Cities_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Countries_Response>? = null
    private lateinit var context: Context


    fun getData( Country_Id:String,DeviceLang:String,context: Context): LiveData<Countries_Response> {
        if(listProductsMutableLiveData==null)
        listProductsMutableLiveData = MutableLiveData<Countries_Response>()
        this.context = context
        getDataValues(Country_Id,DeviceLang)
        return listProductsMutableLiveData as MutableLiveData<Countries_Response>
    }


    fun getDataCountries( DeviceLang:String,context: Context): LiveData<Countries_Response> {
        if(listProductsMutableLiveData==null)
            listProductsMutableLiveData = MutableLiveData<Countries_Response>()
        this.context = context
        getcountries(DeviceLang)
        return listProductsMutableLiveData as MutableLiveData<Countries_Response>
    }
    private fun getDataValues(Country_Id:String,DeviceLang:String) {
        var map= HashMap<String,String>()
        map.put("lang",DeviceLang)
        map.put("country_id",Country_Id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCities(map)
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

    private fun getcountries(DeviceLang:String) {
        var map= HashMap<String,String>()
        map.put("lang",DeviceLang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getCountries(map)
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

