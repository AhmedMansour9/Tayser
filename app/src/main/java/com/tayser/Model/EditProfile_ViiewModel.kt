package com.tayser.Model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class EditProfile_ViiewModel : ViewModel() {

    public var listProductsMutableLiveData: MutableLiveData<Edit_ProfileResponse>? = null
    private lateinit var context: Context
    private var Wron_Email:Boolean=false

    fun getStatus():Boolean{
        return Wron_Email
    }

    public fun getData(
        auth:String,
        Email: String,
        Phone:String,
        Name:String,
        context: Context
    ): LiveData<Edit_ProfileResponse> {
        listProductsMutableLiveData = MutableLiveData<Edit_ProfileResponse>()
        this.context = context
        getDataValues(auth,Email,Phone,Name)
        return listProductsMutableLiveData as MutableLiveData<Edit_ProfileResponse>
    }

    public fun getConfirmPassowrd(
        auth:String,
        password: String,
        confirmpassword:String,
        context: Context
    ): LiveData<Edit_ProfileResponse> {
        listProductsMutableLiveData = MutableLiveData<Edit_ProfileResponse>()
        this.context = context
        getDataPassword(auth,password,confirmpassword)
        return listProductsMutableLiveData as MutableLiveData<Edit_ProfileResponse>
    }
    private fun getDataValues(auth:String,Email: String,Phone:String,Name:String) {
        var map = HashMap<String, String>()
        if (Email != null) {
            map.put("email", Email)
        }
        if (Phone != null) {
            map.put("phone", Phone)
        }
        if (Name != null) {
            map.put("name", Name)
        }
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.EditProf(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Edit_ProfileResponse> {
            override fun onResponse(
                call: Call<Edit_ProfileResponse>,
                response: Response<Edit_ProfileResponse>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Edit_ProfileResponse>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })
    }

    fun getDataPassword(auth:String,Password: String,ConFirmPass:String) {

        var map = HashMap<String, String>()
        map.put("password", Password)
        map.put("new_password", ConFirmPass)


        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.ChangePassword(map, "Bearer " + auth)
        call?.enqueue(object : Callback, retrofit2.Callback<Edit_ProfileResponse> {
            override fun onResponse(
                call: Call<Edit_ProfileResponse>,
                response: Response<Edit_ProfileResponse>
            ) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else {
                    listProductsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Edit_ProfileResponse>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)
            }
        })
    }


    }