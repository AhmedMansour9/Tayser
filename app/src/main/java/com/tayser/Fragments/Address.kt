package com.tayser.Fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayser.Activities.NoItemInternetImage
import com.tayser.Adapter.AddressList_Adapter
import com.tayser.Loading
import com.tayser.utils.ChangeLanguage
import com.tayser.Model.ListAddress_Response
import com.tayser.Model.MessageEvent
import com.tayser.R
import com.tayser.View.Address_View
import com.tayser.ViewModel.GetAddress_ViewModel
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.fragment_address.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Address.newInstance] factory method to
 * create an instance of this fragment.
 */
class Address : Fragment() , Address_View {
    // TODO: Rename and change types of parameters
    var bundle=Bundle()
    private lateinit var DataSaver: SharedPreferences
    private val LOADING_DURATION = 3500

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_address, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
       getAddresses()
       openAddAdress()
        return root
    }

    private fun openAddAdress() {
        root.T_AddAddress.setOnClickListener(){
               var productsByid=AddAddress()
               val bundle = Bundle()
               productsByid.arguments=bundle
               activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                   .addToBackStack(null).commit()

        }
    }
    fun getAddresses(){
        if(!NetworkCheck.isConnect(context!!.applicationContext)) {
            startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
        }
        Loading.Show(this.context!!)
        var Sizes: GetAddress_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            GetAddress_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            Sizes.getData( DataSaver.getString("token", null)!!,
                ChangeLanguage.getLanguage(context!!.applicationContext), it).observe(viewLifecycleOwner, Observer<ListAddress_Response> { loginmodel ->
               Loading.Disable()
                if(loginmodel!=null) {
                    root.Img_noaddress.visibility=View.GONE
                    root.T_NoAddress.visibility=View.GONE

                    val listAdapter =
                            AddressList_Adapter(context!!.applicationContext, loginmodel.data as List<ListAddress_Response.Data>
                            )
                    listAdapter.OnClick(this)
                    root.recycler_Address.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext, LinearLayoutManager.VERTICAL
                        , false
                    )
                    root.recycler_Address.setAdapter(listAdapter)
                }else {
                 root.Img_noaddress.visibility=View.VISIBLE
                    root.T_NoAddress.visibility=View.VISIBLE

                }
            })
        }

    }



    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
         getAddresses()


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun address(address: ListAddress_Response.Data) {
        var productsByid=Edit_Address()
        val bundle = Bundle()
        bundle.putParcelable("details",address)
        productsByid.arguments=bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()

    }

}