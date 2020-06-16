package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.About_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class AboutUs_ViewModel :ViewModel() {

    internal lateinit var context: Context
    private var tripList: MutableLiveData<List<About_Response.Data>>? = null

    fun getAboutus(Lang: String, context: Context): LiveData<List<About_Response.Data>> {
        tripList = MutableLiveData<List<About_Response.Data>>()
        this.context = context
        getBanner(Lang)

        return tripList as MutableLiveData<List<About_Response.Data>>
    }



    fun getBanner(lang: String) {
        val hashMap = HashMap<String, String>()
        hashMap["lang"] = lang
        val service = ApiClient.getClient()!!.create(Service::class.java)
        val call = service.GetAboutus(hashMap)
        call.enqueue(object : Callback<About_Response> {
            override fun onResponse(
                call: Call<About_Response>,
                response: Response<About_Response>
            ) {

                if (response.code() == 200) {
                    tripList!!.setValue(response.body()!!.data)
                } else {
                    tripList!!.setValue(null)
                }
            }

            override fun onFailure(call: Call<About_Response>, t: Throwable) {
                tripList!!.setValue(null)

            }
        })
    }


}