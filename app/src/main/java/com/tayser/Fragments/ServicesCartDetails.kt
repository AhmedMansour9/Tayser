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
import com.tayser.Adapter.ServiceCartDetails_Adapter
import com.tayser.utils.ChangeLanguage
import com.tayser.Model.CartServiceDetails_Response
import com.tayser.Model.CartService_Response
import com.tayser.R
import com.tayser.ViewModel.Cart_ViewModel
import kotlinx.android.synthetic.main.fragment_services_cart_details.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "details"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ServicesCartDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServicesCartDetails : Fragment() {
    // TODO: Rename and change types of parameters
    private var details: CartService_Response.Data.Details? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            details = it.getParcelable(ARG_PARAM1)
        }
    }
    private lateinit var DataSaver: SharedPreferences
    lateinit var UserToken: String
    var bundle=Bundle()
    lateinit var allproducts: Cart_ViewModel

    lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_services_cart_details, container, false)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        root.T_Title.text=details?.name
        getAllCart()



        return root
    }

    fun getAllCart(){
        this.context!!.applicationContext?.let {
            allproducts.getDataDetailsService(details?.cartServiceId.toString(),UserToken,
                ChangeLanguage.getLanguage(context!!.applicationContext), it)
                .observe(viewLifecycleOwner, Observer<CartServiceDetails_Response> { loginmodel ->
                    if(loginmodel!=null) {
                        val listAdapter =
                            loginmodel.data?.list?.let { it1 ->
                                ServiceCartDetails_Adapter(context!!.applicationContext,
                                    it1
                                )
                            }
                        root.recycler_Cart.layoutManager = LinearLayoutManager(
                            this.context!!.applicationContext, LinearLayoutManager.VERTICAL
                            , false
                        )
                        root.recycler_Cart.setAdapter(listAdapter)
                    }else {

                    }

                })
        }
    }
}