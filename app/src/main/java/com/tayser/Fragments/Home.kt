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
import com.tayser.Model.SliderHome_Model
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tayser.Adapter.Categories_Adapter
import com.tayser.Model.Sections_Response

import com.tayser.R
import com.tayser.View.ProductBytUd_View
import com.tayser.ViewModel.Sections_ViewModel
import com.tayser.ViewModel.SliderHome_ViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.viewPager
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() , SwipeRefreshLayout.OnRefreshListener, ProductBytUd_View {
    override fun onRefresh() {
        getSlider()
        getAllCategories()
    }

    lateinit var root:View
    var swipeTimer: Timer?=null
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPager!!.setCurrentItem(currentPage++, true)
    }
    private var currentPage = 0
    private var NUM_PAGES = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_home, container, false)
        SwipRefresh()

        return root
    }


    fun SwipRefresh(){
        root.SwipHome.setOnRefreshListener(this)
        root.SwipHome.isRefreshing=true
        root.SwipHome.isEnabled = true
        root.SwipHome.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipHome.post(Runnable {

            getSlider()
            getAllCategories()
        })
    }


    fun getSlider(){
        var SliderHome: SliderHome_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            SliderHome_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            SliderHome.getData(ChangeLanguage.getLanguage(it), it).observe(viewLifecycleOwner, Observer<SliderHome_Model> { loginmodel ->
                if(loginmodel!=null) {
                    viewPager!!.adapter = this.context?.let { it1 ->
                        Slider_Adapter(
                            it1,
                            loginmodel.data as ArrayList<SliderHome_Model.Slider_Home>
                        )
                    }

                    root.view_pager_circle_indicator.setViewPager(viewPager)
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


    fun getAllCategories() {
        var categories: Sections_ViewModel =
            ViewModelProvider.NewInstanceFactory().create(Sections_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            categories.getCategories(ChangeLanguage.getLanguage(it), it)
                .observe(viewLifecycleOwner, Observer<Sections_Response> { loginmodel ->
                    root.SwipHome.isRefreshing = false

                    if (loginmodel != null) {

                        val listAdapter =
                            Categories_Adapter(
                                context!!.applicationContext,
                                loginmodel.data!! as List<Sections_Response.Data>
                            )
                    listAdapter.onClick(this)
                        root.recycler_Categories.setLayoutManager(
                            GridLayoutManager(
                                context!!.applicationContext
                                , 3
                            )
                        )
                        root.recycler_Categories.setAdapter(listAdapter)
                    }
                })
        }
    }

    override fun Id(categories: Sections_Response.Data) {
        var productsByid=SubCategories()
        val bundle = Bundle()
        bundle.putParcelable("CategoryItem", categories)
        productsByid.arguments=bundle
        activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
            .addToBackStack(null).commit()
    }



}
