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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tayser.Adapter.OrderListProduct_Adapter
import com.tayser.Adapter.OrderListService_Adapter
import com.tayser.Model.OrderListService_Response
import com.tayser.Model.OrderProduct_Response
import com.tayser.R
import com.tayser.ViewModel.Cart_ViewModel
import kotlinx.android.synthetic.main.fragment_details_order_sservice.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsOrderSservice.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsOrderSservice : Fragment() , SwipeRefreshLayout.OnRefreshListener{
    lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
    lateinit var UserToken: String
    var bundle=Bundle()
    lateinit var allproducts: Cart_ViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details_order_sservice, container, false)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        SwipRefresh()

        return view
    }


    fun getAllCart(){
        root.SwipCart.isRefreshing= true
        this.context!!.applicationContext?.let {
            allproducts.getOrderService(UserToken,
                DetailsOrder.OrderId, it)
                .observe(viewLifecycleOwner, Observer<OrderListService_Response> { loginmodel ->
                    root.SwipCart.isRefreshing=false
                    if(loginmodel!=null) {
                        root.recycler_Cart.visibility=View.VISIBLE
                        val listAdapter =
                            loginmodel.data.let { it1 ->
                                OrderListService_Adapter(context!!.applicationContext,
                                    it1 as List<OrderListService_Response.Data  >
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
    override fun onRefresh() {
        getAllCart()
    }
    fun SwipRefresh(){
        root.SwipCart.setOnRefreshListener(this)
        root.SwipCart.isRefreshing=true
        root.SwipCart.isEnabled = true
        root.SwipCart.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipCart.post(Runnable {
            getAllCart()

        })
    }




}