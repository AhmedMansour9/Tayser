package com.tayser.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayser.Adapter.AddressList_Adapter
import com.tayser.Adapter.ServiceCartDetails_Adapter
import com.tayser.ChangeLanguage
import com.tayser.Model.ListAddress_Response
import com.tayser.R
import com.tayser.ViewModel.Cart_ViewModel
import com.tayser.ViewModel.GetAddress_ViewModel
import kotlinx.android.synthetic.main.fragment_address.view.*
import kotlinx.android.synthetic.main.fragment_maintenence__service.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Address.newInstance] factory method to
 * create an instance of this fragment.
 */
class Address : Fragment() {
    // TODO: Rename and change types of parameters
    var bundle=Bundle()
    private lateinit var DataSaver: SharedPreferences

     lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_address, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
       getAddresses()

        return root
    }
    fun getAddresses(){
        var Sizes: GetAddress_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            GetAddress_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            Sizes.getData( DataSaver.getString("token", null)!!,
                ChangeLanguage.getLanguage(context!!.applicationContext), it).observe(viewLifecycleOwner, Observer<ListAddress_Response> { loginmodel ->
                if(loginmodel!=null) {

                    val listAdapter =
                            AddressList_Adapter(context!!.applicationContext, loginmodel.data as List<ListAddress_Response.Data>
                            )

                    root.recycler_Address.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext, LinearLayoutManager.VERTICAL
                        , false
                    )
                    root.recycler_Address.setAdapter(listAdapter)


                }
            })
        }

    }

}