package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.AddAdress_Response
import com.tayser.Model.Register_Model
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AddAddress_ViewModel :ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<AddAdress_Response>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getEditData(address_id:String,
        customer_name: String,country: String,city: String,
        area: String,address: String,
        number_house: String,number_floor:String,number_flat:String,phone:String
        ,area_price:String,token:String,
        context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataEditValues(address_id,customer_name,country,city,area,address,number_house,number_floor
            ,number_flat,phone,area_price,token)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }

    fun getActiveData(address_id:String,token:String,
                    context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataActiveeValues(address_id,token)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }
    fun getData(
        customer_name: String,country: String,city: String,
        area: String,address: String,
        number_house: String,number_floor:String,number_flat:String,phone:String
        ,area_price:String,token:String,
        context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataValues(customer_name,country,city,area,address,number_house,number_floor
        ,number_flat,phone,area_price,token)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }

    private fun getDataValues(customer_name: String,country: String,city: String,
                              area: String,address: String,
                              number_house: String,number_floor:String,number_flat:String,phone:String
                              ,area_price:String,token:String) {
        var map= HashMap<String,String>()
        map.put("customer_name",customer_name)
        map.put("country",country)
        map.put("city",city)
        map.put("area",area)
        map.put("address",address)
        map.put("number_house",number_house)
        map.put("number_floor",number_floor)
        map.put("number_flat",number_flat)
        map.put("phone",phone)
        map.put("area_price",area_price)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.creat_user_address(map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }



    private fun getDataEditValues(address_id:String,customer_name: String,country: String,city: String,
                              area: String,address: String,
                              number_house: String,number_floor:String,number_flat:String,phone:String
                              ,area_price:String,token:String) {
        var map= HashMap<String,String>()
        map.put("customer_name",customer_name)
        map.put("country",country)
        map.put("city",city)
        map.put("area",area)
        map.put("address",address)
        map.put("number_house",number_house)
        map.put("number_floor",number_floor)
        map.put("number_flat",number_flat)
        map.put("phone",phone)
        map.put("area_price",area_price)
        map.put("address_id",address_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.edit_user_address(map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getDataActiveeValues(address_id:String,token:String) {
        var map= HashMap<String,String>()

        map.put("address_id",address_id)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.active_user_address(map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}