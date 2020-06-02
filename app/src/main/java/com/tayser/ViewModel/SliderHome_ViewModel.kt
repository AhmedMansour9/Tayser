package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.SliderHome_Model
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SliderHome_ViewModel : ViewModel()
{
     var listProductsMutableLiveData: MutableLiveData<SliderHome_Model>? = null
    private lateinit var context: Context


     fun getData(Lang:String, context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }

    fun getSliderProducts(Lang:String, context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getProductsSliders(Lang)
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }



    fun getSliderSub(Lang:String,id:String, context: Context): LiveData<SliderHome_Model> {
        listProductsMutableLiveData = MutableLiveData<SliderHome_Model>()
        this.context = context
        getSectionsSliders(Lang,id)
        return listProductsMutableLiveData as MutableLiveData<SliderHome_Model>
    }
    private fun getProductsSliders(product_id: String) {
        var map= HashMap<String,String>()
        map.put("product_id",product_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Products_SLider(map)
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SLider(map)
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }


    private fun getSectionsSliders(lang:String,product_id: String) {
        var map= HashMap<String,String>()
        map.put("section_id",product_id)
        map.put("lang",lang)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Sub_SLider(map)
        call?.enqueue(object : Callback, retrofit2.Callback<SliderHome_Model> {
            override fun onResponse(call: Call<SliderHome_Model>, response: Response<SliderHome_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<SliderHome_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

}