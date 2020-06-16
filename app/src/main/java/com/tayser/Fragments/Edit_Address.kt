package com.tayser.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.tayser.Activities.NoItemInternetImage
import com.tayser.Adapter.Cities_Adapter
import com.tayser.Loading
import com.tayser.utils.ChangeLanguage
import com.tayser.utils.CustomToast
import com.tayser.Model.AddAdress_Response
import com.tayser.Model.Countries_Response
import com.tayser.Model.ListAddress_Response
import com.tayser.Model.MessageEvent
import com.tayser.R
import com.tayser.ViewModel.AddAddress_ViewModel
import com.tayser.ViewModel.Areas_ViewModel
import com.tayser.ViewModel.Cities_ViewModel
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.fragment_edit__address.view.*
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Edit_Address.newInstance] factory method to
 * create an instance of this fragment.
 */
class Edit_Address : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var dataSaver: SharedPreferences
    lateinit var details: ListAddress_Response.Data
    var City_Id:String=""
    var State_Id:String=""
    var Country_Id:String=""
    var City:String=""
    var State:String=""
    var Country:String=""
    var Price:String=""
    var bundle=Bundle()

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_edit__address, container, false)
        dataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext);
        getData()
        getAllCountries()
        Btn_AddAddress()
        Btn_ActiveAddress()
        return root
    }

    private fun getData() {
        bundle=this.arguments!!
        details=bundle.getParcelable("details")!!
        root.E_Name.setText(details.customerName)
        root.E_Phone.setText(details.phone)
        root.E_Address.setText(details.address)
        root.E_BNumber.setText(details.numberFlat)
        root.E_fNumber.setText(details.numberFloor)
        root.E_Apartment.setText(details.numberHouse)

    }

    fun getAllCountries(){
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allCities.getDataCountries( ChangeLanguage.getLanguage(context!!.applicationContext),it).observe(this, Observer<Countries_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val customAdapter = Cities_Adapter(this.context!!.applicationContext,
                        loginmodel.data as List<Countries_Response.Data>
                    )
                    root.S_Country.setPrompt(resources.getString(R.string.city));
                    root.S_Country.setAdapter(customAdapter)
                    var s = 1
                    while (s < loginmodel.data!!.size) {
                        if (loginmodel.data!!.get(s)!!.title.toString().contains(details.country.toString())) {
                            root.S_Country.setSelection(s)
                        }
                        s++
                    }
                    root.S_Country.setOnItemSelectedListener(object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>,
                            view: View,
                            i: Int,
                            l: Long
                        ) {
                            var City = root.S_Country.getSelectedItem().toString()
                            var s = 0
                            while (s < loginmodel.data!!.size) {
                                if (loginmodel!!.data!!.get(s)!!.title.equals(City)) {
                                    Country_Id = loginmodel!!.data!!.get(s)!!.id.toString()
                                    Country = loginmodel!!.data!!.get(s)!!.title.toString()

                                }
                                s++
                            }
                            getAllCities(Country_Id)
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {

                        }
                    })


                }
            })
        }
    }

    fun getAllCities(Country_Id:String){
        val allCities = ViewModelProvider.NewInstanceFactory().create(Cities_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allCities.getData(Country_Id, ChangeLanguage.getLanguage(context!!.applicationContext),it).observe(this, Observer<Countries_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val customAdapter = Cities_Adapter(this.context!!.applicationContext,
                        loginmodel.data as List<Countries_Response.Data>
                    )
                    root.S_City.setPrompt(resources.getString(R.string.city));
                    root.S_City.setAdapter(customAdapter)
                    root.S_City.setOnItemSelectedListener(object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>,
                            view: View,
                            i: Int,
                            l: Long
                        ) {
                            var Cityy = root.S_City.getSelectedItem().toString()

                            var s = 0
                            while (s < loginmodel.data!!.size) {
                                if (loginmodel!!.data!!.get(s)!!.title.equals(Cityy)) {
                                    City_Id = loginmodel!!.data!!.get(s)!!.id.toString()
                                    City = loginmodel!!.data!!.get(s)!!.title.toString()

                                }
                                s++
                            }
                            getAllStates(City_Id)
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {

                        }
                    })


                }
            })
        }
    }

    fun getAllStates(Id:String){
        val allCities = ViewModelProvider.NewInstanceFactory().create(Areas_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allCities.getData( Id, ChangeLanguage.getLanguage(context!!.applicationContext),it).observe(this, Observer<Countries_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val customAdapter = Cities_Adapter(this.context!!.applicationContext,
                        loginmodel.data as List<Countries_Response.Data>
                    )
                    root.S_Area.setAdapter(customAdapter)
                    root.S_Area.setOnItemSelectedListener(object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>,
                            view: View,
                            s: Int,
                            l: Long
                        ) {

                            var City = root.S_Area.getSelectedItem().toString()
//                            if(!City.equals(resources.getString(R.string.selectarea))) {
                            var i = 0
                            while (i < loginmodel!!.data!!.size) {
                                if (loginmodel!!.data!!.get(i)!!.title.equals(City)) {
                                    State_Id = loginmodel!!.data!!.get(i)!!.id.toString()
                                    State = loginmodel!!.data!!.get(i)!!.title.toString()
                                    Price= loginmodel!!.data!!.get(i)!!.price.toString()
                                }
                                i++
                            }
//                            }
                        }

                        override fun onNothingSelected(adapterView: AdapterView<*>) {

                        }
                    })


                }
            })
        }
    }



    fun Btn_AddAddress() {
        root.Btn_EditAddress.setOnClickListener() {
            checkNetwork()
            if (!ValidateAddress() or !ValidateApartment() or !ValidateBNumber() or !ValidateFNumber()
                or !ValidateName()or !ValidatePhone()) {
                return@setOnClickListener
            }

            var RegisterViewModel =
                ViewModelProvider.NewInstanceFactory().create(AddAddress_ViewModel::class.java)
            Loading.Show(context!!)
            root.Btn_EditAddress.isEnabled = false
            RegisterViewModel.getEditData(
                details.id.toString(),
                root.E_Name.text.toString()
                ,Country,City,State,root.E_Address.text.toString(),
                root.E_BNumber.text.toString(),
                root.E_fNumber.text.toString(),
                root.E_Apartment.text.toString(),
                root.E_Phone.text.toString(),
                Price,dataSaver.getString("token", null)!!,
                context!!.applicationContext
            ).observe(this,
                Observer<AddAdress_Response> { loginmodel ->
                    root.Btn_EditAddress.isEnabled = true
                     Loading.Disable()
                    if (loginmodel != null) {
                        Toast.makeText(
                            context!!.applicationContext,
                            loginmodel.data,
                            Toast.LENGTH_SHORT
                        ).show()
                        EventBus.getDefault().postSticky(MessageEvent("address"))
                        activity!!.supportFragmentManager.popBackStack()

                    }
                }
            )


        }

    }

    fun Btn_ActiveAddress() {
        root.Btn_ConfirmAddress.setOnClickListener() {
            checkNetwork()
            var RegisterViewModel =
                ViewModelProvider.NewInstanceFactory().create(AddAddress_ViewModel::class.java)
            Loading.Show(context!!)
            root.Btn_ConfirmAddress.isEnabled = false
            RegisterViewModel.getActiveData(
                details.id.toString(),
                dataSaver.getString("token", null)!!,
                context!!.applicationContext
            ).observe(this,
                Observer<AddAdress_Response> { loginmodel ->
                    Loading.Disable()
                    if (loginmodel != null) {
                        activity?.let { it1 ->
                            CustomToast.toastIconSuccess(loginmodel.data!!
                                , it1
                            )
                        }
                        EventBus.getDefault().postSticky(MessageEvent("address"))

                        activity!!.supportFragmentManager.popBackStack()


                    }else {
                        root.Btn_ConfirmAddress.isEnabled = true

                    }
                }
            )


        }

    }
    private fun ValidateAddress():Boolean{
        val Fullname=root.E_Address.text.toString()
        if(Fullname.isEmpty()){
            root.E_Address.error=resources.getString(R.string.feildempty)
            return false
        }else {
            root.E_Address.error=null
            return true
        }
    }

    private fun ValidatePhone():Boolean{
        val Fullname=root.E_Phone.text.toString()
        if(Fullname.isEmpty()){
            root.E_Phone.error=resources.getString(R.string.feildempty)
            return false
        }else {
            root.E_Phone.error=null
            return true
        }
    }
    private fun ValidateBNumber():Boolean{
        val Fullname=root.E_BNumber.text.toString()
        if(Fullname.isEmpty()){
            root.E_BNumber.error=resources.getString(R.string.feildempty)
            return false
        }
        else {
            root.E_BNumber.error=null
            return true
        }
    }

    private fun ValidateFNumber():Boolean{
        val Fullname=root.E_fNumber.text.toString()
        if(Fullname.isEmpty()){
            root.E_fNumber.error=resources.getString(R.string.feildempty)
            return false
        }
        else {
            root.E_fNumber.error=null
            return true
        }
    }

    private fun ValidateApartment():Boolean{
        val Fullname=root.E_Apartment.text.toString()
        if(Fullname.isEmpty()){
            root.E_Apartment.error=resources.getString(R.string.feildempty)
            return false
        }
        else {
            root.E_Apartment.error=null
            return true
        }
    }
    private fun ValidateName():Boolean{
        val Fullname=root.E_Name.text.toString()
        if(Fullname.isEmpty()){
            root.E_Name.error=resources.getString(R.string.feildempty)
            return false
        }
        else {
            root.E_Name.error=null
            return true
        }
    }


    fun checkNetwork(){
        if(!NetworkCheck.isConnect(context!!.applicationContext)) {
            startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
        }

    }

}