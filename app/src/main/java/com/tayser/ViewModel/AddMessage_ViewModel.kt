package com.tayser.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.AddAdress_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import javax.security.auth.callback.Callback

class AddMessage_ViewModel : ViewModel() {

//    var listProductsMutableLiveData: MutableLiveData<AddAdress_Response>? = null
//    private lateinit var context: Context
//    fun getData(
//        filename: File?, Descrption: String, CityId:String, token:String,
//        context: Context
//    ): LiveData<AddAdress_Response> {
//        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
//        this.context = context
//        getDataValues(filename,Descrption,CityId,token)
//        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
//    }
//
//    private fun getDataValues(filename: File?, Descrption: String,id:String, token:String) {
//
//        val requestDescrption= RequestBody.create(MediaType.parse("multipart/form-data"),Descrption)
//        val requestId= RequestBody.create(MediaType.parse("multipart/form-data"),id)
//            if(filename!=null){
//                val requestFile= RequestBody.create(MediaType.parse("multipart/form-data"),filename)
//                val requestImage=
//                    MultipartBody.Part.createFormData("image",filename?.name,requestFile)
//                SentRequest(requestImage,requestDescrption,requestId,token)
//            }else {
//                SentRequest(null,requestDescrption,requestId,token)
//            }
//
//
//    }
//
//    fun SentRequest(requestImage: MultipartBody.Part?, requestDescrption: RequestBody,requestId:RequestBody, token:String){
//        var service = ApiClient.getClient()?.create(Service::class.java)
//        val call = service?.SentMessage(requestImage,requestDescrption, requestId,"Bearer "+token)
//        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
//            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {
//
//                if (response.code() == 200) {
//                    listProductsMutableLiveData?.setValue(response.body()!!)
//
//                } else  if(response.code()==401){
//                    listProductsMutableLiveData?.setValue(null)
//
//                }
//            }
//
//            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
//                listProductsMutableLiveData?.setValue(null)
//
//            }
//        })
//
//    }
}