package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.ContactUs_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class ContactUs_ViewModel :ViewModel() {

    lateinit var context: Context
    private var regist: MutableLiveData<ContactUs_Response>? = null
    internal var Error: String? = null

    fun getContactus(
        Email: String,
        Name: String,
        phone: String,
        message: String,
        context: Context
    ): LiveData<ContactUs_Response> {
        regist = MutableLiveData<ContactUs_Response>()
        this.context = context
        Contactus(Email, Name, phone, message)

        return regist as MutableLiveData<ContactUs_Response>
    }

    fun Contactus(Email: String, Name: String, phone: String, message: String) {
        val queryMap = HashMap<String, String>()

        queryMap["email"] = Email
        queryMap["name"] = Name
        queryMap["phone"] = phone
        queryMap["message"] = message
        val apiInterface = ApiClient.getClient()?.create(Service::class.java)
        val call = apiInterface?.ContactUs(queryMap)
        call?.enqueue(object : Callback<ContactUs_Response> {
            override fun onResponse(
                call: Call<ContactUs_Response>,
                response: Response<ContactUs_Response>
            ) {
                if (response.code() == 200) {
                    regist!!.setValue(response.body())
                } else if (response.code() == 404) {
                    regist!!.setValue(null)
                }


            }

            override fun onFailure(call: Call<ContactUs_Response>, t: Throwable) {
                regist!!.setValue(null)

            }
        })
    }

}