package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.Sections_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Sections_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Sections_Response>? = null
    private lateinit var context: Context


    public fun getCategories(Lang:String, context: Context): LiveData<Sections_Response> {
        listProductsMutableLiveData = MutableLiveData<Sections_Response>()
        this.context = context
        getDataValues(Lang)
        return listProductsMutableLiveData as MutableLiveData<Sections_Response>
    }
//    fun getCategoriesById(Lang:String,id:String, context: Context): LiveData<Categories_Response> {
//        listProductsMutableLiveData = MutableLiveData<Categories_Response>()
//        this.context = context
//        getcategoriesbyid(Lang,id)
//        return listProductsMutableLiveData as MutableLiveData<Categories_Response>
//    }


    private fun getDataValues(Lang: String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.Categories(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Sections_Response> {
            override fun onResponse(call: Call<Sections_Response>, response: Response<Sections_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Sections_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

//    private fun getcategoriesbyid(Lang: String,id:String) {
//        var map= HashMap<String,String>()
//        map.put("lang",Lang)
//        map.put("section_id",id)
//
//
//        var service = ApiClient.getClient()?.create(Service::class.java)
//        val call = service?.CategoriesByid(map)
//        call?.enqueue(object : Callback, retrofit2.Callback<Categories_Response> {
//            override fun onResponse(call: Call<Categories_Response>, response: Response<Categories_Response>) {
//
//                if (response.code() == 200) {
//                    listProductsMutableLiveData?.setValue(response.body()!!)
//
//                } else  {
//                    listProductsMutableLiveData?.setValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<Categories_Response>, t: Throwable) {
//                listProductsMutableLiveData?.setValue(null)
//
//            }
//        })
//    }
}