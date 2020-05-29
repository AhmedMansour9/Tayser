package com.tayser.Fragments


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tayser.Adapter.Slider_Adapter
import com.tayser.ChangeLanguage
import com.tayser.Model.Sections_Response
import com.tayser.Model.SliderHome_Model
import androidx.lifecycle.Observer

import com.tayser.R
import com.tayser.ViewModel.SliderHome_ViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.viewPager
import kotlinx.android.synthetic.main.fragment_sub_categories.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.viewPagerSub
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SubCategories : Fragment() {
    lateinit var root:View
    var swipeTimer: Timer?=null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPagerSub!!.setCurrentItem(currentPage++, true)
    }
    private var currentPage = 0
    private var NUM_PAGES = 0
    lateinit var categories: Sections_Response.Data
    var bundle=Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_sub_categories, container, false)
        getData();


        return root
    }

    private fun getData() {
        bundle = this.arguments!!
        categories= bundle.getParcelable("CategoryItem")!!
        root.T_Title.text=categories.title
        if(categories.haveProduct.equals("0")){
            root.Card_Products.visibility=View.GONE
        }
        if(categories.haveMaintenance.equals("0")){
            root.Card_Mainten.visibility=View.GONE
        }
        if(categories.haveEmergency.equals("0")){
            root.Card_Emergency.visibility=View.GONE
        }
        getSlider(categories.id.toString())
    }


    fun getSlider(id:String){
        var SliderHome: SliderHome_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            SliderHome_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            SliderHome.getSliderProducts(ChangeLanguage.getLanguage(context!!.applicationContext),id, it).observe(viewLifecycleOwner, Observer<SliderHome_Model> { loginmodel ->
                if(loginmodel!=null) {
                    viewPagerSub!!.adapter = this.context?.let { it1 ->
                        Slider_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                        )
                    }

                    root.view_pager_circle_indicators.setViewPager(viewPagerSub)
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
