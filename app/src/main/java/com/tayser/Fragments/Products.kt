package com.tayser.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayser.Adapter.Products_Adapter
import com.tayser.ChangeLanguage
import com.tayser.R
import com.tayser.ViewModel.getProducts_ViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import androidx.lifecycle.Observer
import com.tayser.Model.Product_Response
import com.tayser.Model.Sections_Response
import com.tayser.View.ProductDetails_View
import kotlinx.android.synthetic.main.fragment_products.view.*
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_products, container, false)
        Selected_Tabs()
        getProducts("1")
        return root
    }
    fun getProducts(type:String){
        bundle = this.arguments!!
        categories= bundle.getParcelable("CategoryItem")!!
        root.T_Title.text=categories.title
        var allproducts: getProducts_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            getProducts_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            allproducts.getProducts(categories.id.toString(),ChangeLanguage.getLanguage(it),type, it)
                .observe(viewLifecycleOwner, Observer<Product_Response> { loginmodel ->
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


}