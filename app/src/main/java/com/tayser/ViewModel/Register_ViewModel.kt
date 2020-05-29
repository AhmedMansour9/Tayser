package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.Register_Model
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Register_ViewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Register_Model>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        Email: String,
        Password:String,
        Name:String,
        phone:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getDataValues(Email,Password,Name,phone)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>
    }
    public fun getLogin(
        Email: String,
        Password:String,
        context: Context
    ): LiveData<Register_Model> {
        listProductsMutableLiveData = MutableLiveData<Register_Model>()
        this.context = context
        getLoginValues(Email,Password)
        return listProductsMutableLiveData as MutableLiveData<Register_Model>

    }

    private fun getDataValues(Email: String,Password:String,Name:String,phone:String) {
      var map= HashMap<String,String>()
        map.put("email",Email)
        map.put("password",Password)
        map.put("phone",phone)
        map.put("name",Name)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userRegister(map)
        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
    private fun getLoginValues(Email: String,Password:String) {
        var map= HashMap<String,String>()
        map.put("email",Email)
        map.put("password",Password)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.userLogin(map)

        call?.enqueue(object : Callback, retrofit2.Callback<Register_Model> {
            override fun onResponse(call: Call<Register_Model>, response: Response<Register_Model>) {
                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    Wron_Email=true
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Register_Model>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }
}