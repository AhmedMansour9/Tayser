package com.tayser.Fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tayser.Adapter.SliderProduct_Adapter
import com.tayser.Model.Product_Response
import com.tayser.Model.SliderHome_Model
import com.tayser.R
import com.tayser.ViewModel.SliderHome_ViewModel
import kotlinx.android.synthetic.main.fragment_details__product.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import androidx.lifecycle.Observer
import com.tayser.Activities.AddToCart
import com.tayser.Activities.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "details"
private const val ARG_PARAM2 = "cat_name"

/**
 * A simple [Fragment] subclass.
 * Use the [Details_Product.newInstance] factory method to
 * create an instance of this fragment.
 */
class Details_Product : Fragment() {
    // TODO: Rename and change types of parameters
    private var detailsProduct: Product_Response.Data? = null
    private var Cat_Name: String? = null
    lateinit var root:View
    var swipeTimer: Timer?=null

    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPagerProduct!!.setCurrentItem(currentPage++, true)
    }
    private var currentPage = 0
    private var NUM_PAGES = 0
    lateinit var Product_Id:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            detailsProduct = it.getParcelable(ARG_PARAM1)
            Cat_Name=it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_details__product, container, false)
         showInfo()
         getSlider()
         OpenCart()
        return root
    }

    private fun OpenCart() {
        root.Tmg_AddCart.setOnClickListener(){
            val intent = Intent(context!!.applicationContext, AddToCart::class.java)
            intent.putExtra("price",detailsProduct?.price?.toDouble())
            intent.putExtra("id",detailsProduct?.id.toString())

            startActivity(intent)

        }
    }


    private fun showInfo() {
        Product_Id=detailsProduct!!.id.toString()
      root.T_Name.text=detailsProduct!!.title
        root.T_Description.text=detailsProduct!!.shortDescription
        root.T_Price.text=detailsProduct!!.price
        root.T_BigDescription.text=detailsProduct!!.description
        root.T_Title.text=Cat_Name
    }


    fun getSlider(){
        var SliderHome: SliderHome_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            SliderHome_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            SliderHome.getSliderProducts(Product_Id, it).observe(viewLifecycleOwner, Observer<SliderHome_Model> { loginmodel ->
                if(loginmodel!=null) {
                    root.viewPagerProduct!!.adapter = this.context?.let { it1 ->
                        SliderProduct_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                        )
                    }
                    root.view_pager_circle_indica.setViewPager(viewPager)
                    NUM_PAGES = loginmodel.data.size
                    swipeTimer = Timer()
                    swipeTimer!!.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(Update)
                        }
                    }, 3000, 3000)
                }
            })
        }
    }

}