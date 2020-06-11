package com.tayser.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tayser.Adapter.Cart_Adapter
import com.tayser.Adapter.ServicesCart_Adapter
import com.tayser.ChangeLanguage
import com.tayser.Model.CartService_Response
import com.tayser.Model.Cart_Response
import com.tayser.Model.MessageEvent
import com.tayser.Model.PlusCart_Response
import com.tayser.R
import com.tayser.View.DeleteService_View
import com.tayser.ViewModel.Cart_ViewModel
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_services.view.*
import kotlinx.android.synthetic.main.fragment_services.view.Btn_Checkout
import kotlinx.android.synthetic.main.fragment_services.view.SwipCart
import kotlinx.android.synthetic.main.fragment_services.view.T_NoCart
import kotlinx.android.synthetic.main.fragment_services.view.T_Total
import kotlinx.android.synthetic.main.fragment_services.view.recycler_Cart
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [Services.newInstance] factory method to
 * create an instance of this fragment.
 */
class Services : Fragment() , SwipeRefreshLayout.OnRefreshListener, DeleteService_View {

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
        root= inflater.inflate(R.layout.fragment_services, container, false)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        UserToken = DataSaver.getString("token", null)!!
        SwipRefresh()
        getAllCart()
        checkOrder()
        return root
    }
    private fun checkOrder() {
//        root.Btn_Checkout.setOnClickListener() {
//
//                if (this.arguments != null) {
//                    bundle = this.arguments!!
//                    Postion = bundle.getInt("view")
//                    var productsByid = OrderLocation_Fragmet()
//                    val bundle = Bundle()
//                    bundle.putString("totalprice", T_TotalPrice!!)
//                    productsByid.arguments = bundle
//                    activity!!.supportFragmentManager?.beginTransaction()
//                        ?.replace(Postion, productsByid)
//                        ?.addToBackStack(null)?.commit()
//                } else {
//                    Postion = root.Rela_Cart.id
//                    var productsByid = OrderLocation_Fragmet()
//                    val bundle = Bundle()
//                    bundle.putString("totalprice", T_TotalPrice!!)
//                    productsByid.arguments = bundle
//                    childFragmentManager?.beginTransaction()?.replace(Postion, productsByid)
//                        ?.addToBackStack(null)?.commit()
//
//                }
//
//            }
//        }


    }

    fun getAllCart(){
        root.SwipCart.isRefreshing= true
        this.context!!.applicationContext?.let {
            allproducts.getDataService(UserToken,
                ChangeLanguage.getLanguage(context!!.applicationContext), it)
                .observe(viewLifecycleOwner, Observer<CartService_Response> { loginmodel ->
                    root.SwipCart.isRefreshing=false
                    if(loginmodel!=null) {
                        root.recycler_Cart.visibility=View.VISIBLE
                        root.T_Total.visibility=View.VISIBLE
                        root.Btn_Checkout.visibility=View.VISIBLE
                        val pi = loginmodel.data?.totalServices?.toDouble()
                        val s = "%.2f".format(pi)
                        Cart.T_TotalPrice =s

                        root.T_Total.text=resources.getString(R.string.total)+" "+ Cart.T_TotalPrice.toString()+" "+resources.getString(R.string.currency)
                        val listAdapter =
                            loginmodel.data?.list?.let { it1 ->
                                ServicesCart_Adapter(context!!.applicationContext,
                                    it1
                                )
                            }
                        listAdapter?.OnClickDelete(this)
                        root.recycler_Cart.layoutManager = LinearLayoutManager(
                            this.context!!.applicationContext, LinearLayoutManager.VERTICAL
                            , false
                        )
                        root.recycler_Cart.setAdapter(listAdapter)
                    }else {
                        root.T_Total.visibility=View.GONE
                        root.T_NoCart.visibility=View.VISIBLE
                        root.recycler_Cart.visibility=View.GONE
                        root.Btn_Checkout.visibility=View.GONE

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

    override fun delete(Id: String) {
        val builder =
            AlertDialog.Builder(context!!)
        builder.setMessage(resources.getString(R.string.delete_post))
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
            this.context!!.applicationContext?.let {
                allproducts.DeleteService(UserToken, "en", Id, it)
                    .observe(this, Observer<PlusCart_Response> { loginmodel ->
                        if (loginmodel != null) {
                            EventBus.getDefault().postSticky(MessageEvent("cart"))

                            getAllCart()
                        }

                    })

            }
        }

        builder.setNegativeButton(
            resources.getString(R.string.no)
        ) { dialog, which -> dialog.cancel() }
        builder.show()






    }

    override fun details(details: CartService_Response.Data.Details) {
        var productsByid=ServicesCartDetails()
        val bundle = Bundle()
        bundle.putParcelable("details", details)
        productsByid.arguments=bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()

    }


}