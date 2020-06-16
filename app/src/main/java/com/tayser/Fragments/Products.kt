package com.tayser.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayser.Adapter.Products_Adapter
import com.tayser.utils.ChangeLanguage
import com.tayser.R
import com.tayser.ViewModel.getProducts_ViewModel
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import com.tayser.Loading
import com.tayser.Model.*
import com.tayser.View.ProductDetails_View
import com.tayser.ViewModel.Cart_ViewModel
import kotlinx.android.synthetic.main.fragment_products.view.*
import kotlinx.android.synthetic.main.fragment_products.view.T_Title
import kotlinx.android.synthetic.main.fragment_products.view.T_notification_numde
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Products.newInstance] factory method to
 * create an instance of this fragment.
 */
class Products : Fragment(), ProductDetails_View {
    lateinit var root:View
    lateinit var categories: Sections_Response.Data
    var bundle=Bundle()
    lateinit var allproducts: Cart_ViewModel
    private lateinit var DataSaver: SharedPreferences
    private var CartNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_products, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)

        Selected_Tabs()
        getProducts("1")
        CartNumber=0
        getNumberOfCart()
        getNumberCartService()
        openCart()
        return root
    }
    fun getProducts(type:String){
        bundle = this.arguments!!
        categories= bundle.getParcelable("CategoryItem")!!
        root.T_Title.text=categories.title
        Loading.Show(context!!)
        var allproducts: getProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getProducts_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allproducts.getProducts(categories.id.toString(),
                ChangeLanguage.getLanguage(it),type, it)
                .observe(viewLifecycleOwner, Observer<Product_Response> { loginmodel ->
                    Loading.Disable()
                if(loginmodel!=null) {
                    root.recycler_Products.visibility=View.VISIBLE
                    Collections.reverse(loginmodel.data!!)
                    var listAdapter =
                        Products_Adapter(context!!.applicationContext,
                            loginmodel.data as List<Product_Response.Data>
                        )
                    listAdapter.onClick(this)
                    ViewCompat.setNestedScrollingEnabled(root.recycler_Products, false);
                    root.recycler_Products.layoutManager = LinearLayoutManager(
                        this.context!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    root.recycler_Products.setAdapter(listAdapter)
                }else {

                }
            })
        }
    }

    fun Selected_Tabs(){
        root.T_Mainten.setOnClickListener(){
            root.recycler_Products.visibility=View.GONE
            onClickMaintence()
            getProducts("1")

        }
        root.T_Establish.setOnClickListener(){
            root.recycler_Products.visibility=View.GONE
            onClickEstablish()
            getProducts("0")

        }
    }
    fun onClickMaintence(){
        root.T_Mainten.setTextColor(Color.parseColor("#224488"))
        root.T_Mainten.setBackgroundResource(R.drawable.bc_select_left)
        root.T_Establish.setTextColor(Color.parseColor("#ffffff"))
        root.T_Establish.setBackgroundColor(Color.TRANSPARENT)
    }
    fun onClickEstablish(){
        root.T_Mainten.setTextColor(Color.parseColor("#ffffff"))
        root.T_Mainten.setBackgroundColor(Color.TRANSPARENT)
//            T_MyRequests.setBackgroundColor(Color.TRANSPARENT)
        root.T_Establish.setTextColor(Color.parseColor("#224488"))
        root.T_Establish.setBackgroundResource(R.drawable.bc_selected_right)
    }

    override fun details(categories: Product_Response.Data) {
        var productsByid=Details_Product()
        val bundle = Bundle()
        bundle.putParcelable("details", categories)
        bundle.putString("cat_name", this.categories.title)

        productsByid.arguments=bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()

    }

    private fun openCart() {
        root.Img_Cartt.setOnClickListener(){

            var productsByid=tabs_cart()
            val bundle = Bundle()
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        CartNumber=0
        getNumberOfCart()
        getNumberCartService()


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    fun getNumberOfCart(){
        this.context!!.applicationContext?.let {
            allproducts.getData(DataSaver.getString("token", null)!!,"en", it).observe(viewLifecycleOwner, Observer<Cart_Response> { loginmodel ->

                if(loginmodel!=null) {
                    CartNumber=CartNumber+loginmodel.data.list.size
                    root.T_notification_numde.text=CartNumber.toString()
                }else {
                    CartNumber=0
                    root.T_notification_numde.text=CartNumber.toString()

                }

            })
        }

    }
    fun getNumberCartService(){
        allproducts.getDataService(DataSaver.getString("token", null)!!,
            ChangeLanguage.getLanguage(context!!.applicationContext), this.context!!.applicationContext)
            .observe(viewLifecycleOwner, Observer<CartService_Response> { loginmodel ->
                if(loginmodel!=null) {
                    CartNumber=CartNumber+loginmodel.data!!.list!!.size
                    root.T_notification_numde.text=CartNumber.toString()
                }else {
                    CartNumber=0
                    root.T_notification_numde.text=CartNumber.toString()
                }
            })
    }

}