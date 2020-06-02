package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Fragments.Products
import com.tayser.Model.Product_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class getProducts_ViewModel: ViewModel() {
    private var No_Product:Boolean=false

     var listProductsMutableLiveData: MutableLiveData<Product_Response>? = null

    private lateinit var context: Context



     fun getProducts(category_id:String,Lang:String,type:String, context: Context): LiveData<Product_Response> {
        listProductsMutableLiveData = MutableLiveData<Product_Response>()
        this.context = context
        getProductsId(category_id,Lang,type)
        return listProductsMutableLiveData as MutableLiveData<Product_Response>
    }





    private fun getProductsId(section_id:String,Lang: String,type:String) {
        var map= HashMap<String,String>()
        map.put("lang",Lang)
        map.put("section_id",section_id)
        map.put("type",type)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ProductsByCatId(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Product_Response> {
                override fun onResponse(call: Call<Product_Response>, response: Response<Product_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Product_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }







}