package com.tayser.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tayser.Model.AddAdress_Response
import com.tayser.Retrofit.ApiClient
import com.tayser.Retrofit.Service
import kotlinx.android.synthetic.main.fragment_maintenence__service.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import javax.security.auth.callback.Callback

class AddMessage_ViewModel : ViewModel() {
     private  var requestImage:MultipartBody.Part?=null
    private  var requestImage2:MultipartBody.Part?=null
    private  var requestImage3:MultipartBody.Part?=null
    private  var requestImage4:MultipartBody.Part?=null

    var listProductsMutableLiveData: MutableLiveData<AddAdress_Response>? = null
    private lateinit var context: Context
    fun getData(
        filename: File?,filename2: File?,filename3: File?,filename4: File?,
        section_id: String, address_id:String, request_preview:String,
        flat_area:String, floor_number:String, customer_note:String,room_number:String,
        token:String,
        context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataValues(filename,filename2,filename3,filename4,section_id,address_id,request_preview,
            flat_area ,floor_number,customer_note,room_number,token)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }


    fun getDataClean(
        filename: File?,filename2: File?,filename3: File?,filename4: File?,
        section_id: String, address_id:String, request_preview:String,
        flat_area:String, floor_number:String, customer_note:String,room_number:String,
        token:String
        ,date:String,
        context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataCleanServiceValues(filename,filename2,filename3,filename4,section_id,address_id,request_preview,
            flat_area ,floor_number,customer_note,room_number,token,date)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }

    fun getDataEmergency(
        filename: File?,filename2: File?,filename3: File?,filename4: File?,
        section_id: String, address_id:String,
        services_id:String,
        customer_note:String,
        date:String,
        token:String,
        context: Context
    ): LiveData<AddAdress_Response> {
        listProductsMutableLiveData = MutableLiveData<AddAdress_Response>()
        this.context = context
        getDataValuesEmergrency(filename,filename2,filename3,filename4,section_id,address_id,date
            ,customer_note,services_id,token)
        return listProductsMutableLiveData as MutableLiveData<AddAdress_Response>
    }


    private fun getDataValues(filename: File?,filename2: File?,filename3: File?,filename4: File?,
                              section_id: String?, address_id:String, request_preview:String,
                              flat_area:String, floor_number:String, customer_note:String,room_number:String,token:String) {
        val section_id= RequestBody.create(MediaType.parse("multipart/form-data"),section_id)
        val address_id= RequestBody.create(MediaType.parse("multipart/form-data"),address_id)
        val request_preview= RequestBody.create(MediaType.parse("multipart/form-data"),request_preview)
        val flat_area= RequestBody.create(MediaType.parse("multipart/form-data"),flat_area)
        val floor_number= RequestBody.create(MediaType.parse("multipart/form-data"),floor_number)
        val customer_note= RequestBody.create(MediaType.parse("multipart/form-data"),customer_note)
        val room_number= RequestBody.create(MediaType.parse("multipart/form-data"),room_number)

        if(filename!=null) {
             val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filename)
             requestImage = MultipartBody.Part.createFormData("image", filename?.name, requestFile)
         }
        if(filename2!=null) {
            val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), filename2)
            requestImage2 =
                MultipartBody.Part.createFormData("image2", filename2?.name, requestFile2)
        }
        if(filename3!=null) {
            val requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), filename3)
            requestImage3 =
                MultipartBody.Part.createFormData("image3", filename3?.name, requestFile3)
        }
        if(filename4!=null) {
            val requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), filename4)
            requestImage4 =
                MultipartBody.Part.createFormData("image4", filename4?.name, requestFile4)
        }
        SentRequest(requestImage,requestImage2,requestImage3,requestImage4,
            section_id!!, address_id,request_preview,flat_area
            ,floor_number ,customer_note,room_number,token)



    }

    fun SentRequest(requestImage: MultipartBody.Part?,
                    requestImage2: MultipartBody.Part?,
                    requestImage3: MultipartBody.Part?,requestImage4: MultipartBody.Part?,
                    section_id: RequestBody,address_id:RequestBody
                    ,request_preview: RequestBody,flat_area:RequestBody
                    ,floor_number: RequestBody,customer_note:RequestBody
                    ,room_number:RequestBody
        , token:String){
        var map = HashMap<String, RequestBody>()
        map.put("section_id", section_id)
        map.put("address_id", address_id)
        map.put("request_preview", request_preview)
        map.put("flat_area", flat_area)
        map.put("room_number", room_number)
        map.put("floor_number", floor_number)
        map.put("customer_note", customer_note)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.SentMessage(requestImage,
            requestImage2,requestImage3,requestImage4,  map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  if(response.code()==401){
                    listProductsMutableLiveData?.setValue(null)

                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })

    }

    private fun getDataValuesEmergrency(filename: File?,filename2: File?,filename3: File?,filename4: File?,
                              section_id: String?, address_id:String,date:String,
                                        customer_note:String,services_id:String,token:String) {
        val section_id= RequestBody.create(MediaType.parse("multipart/form-data"),section_id)
        val address_id= RequestBody.create(MediaType.parse("multipart/form-data"),address_id)
        val date= RequestBody.create(MediaType.parse("multipart/form-data"),date)
        val customer_note= RequestBody.create(MediaType.parse("multipart/form-data"),customer_note)
        val services_id= RequestBody.create(MediaType.parse("multipart/form-data"),services_id)

        if(filename!=null) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filename)
            requestImage = MultipartBody.Part.createFormData("image", filename?.name, requestFile)
        }
        if(filename2!=null) {
            val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), filename2)
            requestImage2 =
                MultipartBody.Part.createFormData("image2", filename2?.name, requestFile2)
        }
        if(filename3!=null) {
            val requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), filename3)
            requestImage3 =
                MultipartBody.Part.createFormData("image3", filename3?.name, requestFile3)
        }
        if(filename4!=null) {
            val requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), filename4)
            requestImage4 =
                MultipartBody.Part.createFormData("image4", filename4?.name, requestFile4)
        }
        SentRequestEmergency(requestImage,requestImage2,requestImage3,requestImage4,
            section_id!!, address_id,date,customer_note,services_id,token)



    }

    fun SentRequestEmergency(requestImage: MultipartBody.Part?,
                    requestImage2: MultipartBody.Part?,
                    requestImage3: MultipartBody.Part?,requestImage4: MultipartBody.Part?,
                    section_id: RequestBody,address_id:RequestBody,
                     date:RequestBody,customer_note:RequestBody
                             ,services_id:RequestBody
                    , token:String){
        var map = HashMap<String, RequestBody>()
        map.put("section_id", section_id)
        map.put("address_id", address_id)
        map.put("date_time", date)
        map.put("services_id", services_id)
        map.put("customer_note", customer_note)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.request_emergencies(requestImage,
            requestImage2,requestImage3,requestImage4,  map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  if(response.code()==401){
                    listProductsMutableLiveData?.setValue(null)

                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })

    }





    private fun getDataCleanServiceValues(filename: File?,filename2: File?,filename3: File?,filename4: File?,
                              section_id: String?, address_id:String, request_preview:String,
                              flat_area:String, floor_number:String, customer_note:String
                                          ,room_number:String,token:String,date:String) {
        val section_id= RequestBody.create(MediaType.parse("multipart/form-data"),section_id)
        val address_id= RequestBody.create(MediaType.parse("multipart/form-data"),address_id)
        val request_preview= RequestBody.create(MediaType.parse("multipart/form-data"),request_preview)
        val flat_area= RequestBody.create(MediaType.parse("multipart/form-data"),flat_area)
        val floor_number= RequestBody.create(MediaType.parse("multipart/form-data"),floor_number)
        val customer_note= RequestBody.create(MediaType.parse("multipart/form-data"),customer_note)
        val room_number= RequestBody.create(MediaType.parse("multipart/form-data"),room_number)
        val date= RequestBody.create(MediaType.parse("multipart/form-data"),date)

        if(filename!=null) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filename)
            requestImage = MultipartBody.Part.createFormData("image", filename?.name, requestFile)
        }
        if(filename2!=null) {
            val requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), filename2)
            requestImage2 =
                MultipartBody.Part.createFormData("image2", filename2?.name, requestFile2)
        }
        if(filename3!=null) {
            val requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), filename3)
            requestImage3 =
                MultipartBody.Part.createFormData("image3", filename3?.name, requestFile3)
        }
        if(filename4!=null) {
            val requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), filename4)
            requestImage4 =
                MultipartBody.Part.createFormData("image4", filename4?.name, requestFile4)
        }
        SentRequestClean(requestImage,requestImage2,requestImage3,requestImage4,
            section_id!!, address_id,request_preview,flat_area
            ,floor_number ,customer_note,room_number,token,date)



    }

    fun SentRequestClean(requestImage: MultipartBody.Part?,
                    requestImage2: MultipartBody.Part?,
                    requestImage3: MultipartBody.Part?,requestImage4: MultipartBody.Part?,
                    section_id: RequestBody,address_id:RequestBody
                    ,request_preview: RequestBody,flat_area:RequestBody
                    ,floor_number: RequestBody,customer_note:RequestBody
                    ,room_number:RequestBody
                    , token:String,date:RequestBody){
        var map = HashMap<String, RequestBody>()
        map.put("section_id", section_id)
        map.put("address_id", address_id)
        map.put("period", request_preview)
        map.put("flat_area", flat_area)
        map.put("room_number", room_number)
        map.put("floor_number", floor_number)
        map.put("customer_note", customer_note)
        map.put("date_time", date)

        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.request_cleannesses(requestImage,
            requestImage2,requestImage3,requestImage4,  map,"Bearer "+token)
        call?.enqueue(object : Callback, retrofit2.Callback<AddAdress_Response> {
            override fun onResponse(call: Call<AddAdress_Response>, response: Response<AddAdress_Response>) {

                if (response.code() == 200) {
                    listProductsMutableLiveData?.setValue(response.body()!!)

                } else  if(response.code()==401){
                    listProductsMutableLiveData?.setValue(null)

                }
            }

            override fun onFailure(call: Call<AddAdress_Response>, t: Throwable) {
                listProductsMutableLiveData?.setValue(null)

            }
        })

    }


}