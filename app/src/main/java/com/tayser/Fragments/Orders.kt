package com.tayser.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayser.Adapter.MyOrders_Adapter
import com.tayser.Model.MyOrders_Response

import com.tayser.R
import com.tayser.View.DetailsMyOrdersView
import com.tayser.ViewModel.MyOrders_ViewModel
import kotlinx.android.synthetic.main.fragment_orders.view.*

/**
 * A simple [Fragment] subclass.
 */
class Orders : Fragment() , DetailsMyOrdersView {

    lateinit var root:View
    lateinit var UserToken: String
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_orders, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!

        getAllOrders()

        return root
    }
    fun getAllOrders(){
        var allproducts: MyOrders_ViewModel = ViewModelProviders.of(this)[MyOrders_ViewModel::class.java]
        this.context!!.applicationContext?.let {
            allproducts.getData(UserToken, it)?.observe(viewLifecycleOwner, Observer<MyOrders_Response> { loginmodel ->
                if(loginmodel!=null) {
                    val listAdapter =
                        MyOrders_Adapter( loginmodel.data)
                    listAdapter.OnClick(this)
                    root.Recycle_Order.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.Recycle_Order.setAdapter(listAdapter)
                }else {
                    var status:Boolean=allproducts.getStatus()
                    if(status){
                        root.Recycle_Order.visibility=View.GONE
                        root.T_NoOrder.visibility=View.VISIBLE
                    }

                }

            })
        }
    }

    override fun showDetailsMyOrders(myOrdersData: MyOrders_Response.Data) {
        val detailsMyOrdersFragment = DetailsOrder()
        val bundle = Bundle()
        bundle.putParcelable("MyOrdersItem", myOrdersData)
        detailsMyOrdersFragment.setArguments(bundle)
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.Rela_Orders, detailsMyOrdersFragment)
            .addToBackStack(null).commit()
    }



}
