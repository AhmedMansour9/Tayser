package com.tayser.Fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tayser.Adapter.Slider_Adapter
import com.tayser.utils.ChangeLanguage
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kaopiz.kprogresshud.KProgressHUD
import com.tayser.Activities.AddToCart
import com.tayser.Activities.NoItemInternetImage
import com.tayser.Adapter.Categories_Adapter
import com.tayser.Loading
import com.tayser.utils.ItemAnimation
import com.tayser.Model.*

import com.tayser.R
import com.tayser.View.ProductBytUd_View
import com.tayser.ViewModel.Cart_ViewModel
import com.tayser.ViewModel.Sections_ViewModel
import com.tayser.ViewModel.SliderHome_ViewModel
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.viewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() , SwipeRefreshLayout.OnRefreshListener, ProductBytUd_View {
    override fun onRefresh() {
        Loading.Show(context!!)
        checkNetwork()
        getSlider()
        getAllCategories()
        CartNumber=0
        getNumberOfCart()
        getNumberCartService()
    }

    lateinit var root:View
    var swipeTimer: Timer?=null
    private lateinit var DataSaver: SharedPreferences
    val handler = Handler()
    val Update = Runnable {
        if (currentPage == NUM_PAGES) {
            currentPage = 0
        }
        root.viewPager!!.setCurrentItem(currentPage++, true)
    }

    private var CartNumber = 0
    private var currentPage = 0
    private var NUM_PAGES = 0
    lateinit var allproducts: Cart_ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_home, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)

        SwipRefresh()
        openCart()


        return root
    }
    private fun openCart() {
        root.Img_Cart.setOnClickListener(){

            var productsByid=tabs_cart()
            val bundle = Bundle()
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }
    }

    fun getNumberOfCart(){
        this.context!!.applicationContext?.let {
            allproducts.getData(DataSaver.getString("token", null)!!,"en", it).observe(viewLifecycleOwner, Observer<Cart_Response> { loginmodel ->

                if(loginmodel!=null) {
                    CartNumber=CartNumber+loginmodel.data.list.size
                    root.T_notification_numdetai.text=CartNumber.toString()
                }else {
                    CartNumber=0
                    root.T_notification_numdetai.text=CartNumber.toString()

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
                    root.T_notification_numdetai.text=CartNumber.toString()
                }else {
                    CartNumber=0
                    root.T_notification_numdetai.text=CartNumber.toString()
                }
    })
}
    fun SwipRefresh(){
        root.SwipHome.setOnRefreshListener(this)
        root.SwipHome.isEnabled = true
        root.SwipHome.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        root.SwipHome.post(Runnable {
            Loading.Show(context!!)
            checkNetwork()
            CartNumber=0
            getNumberOfCart()
            getNumberCartService()
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
                    Loading.Disable()

                    if (loginmodel != null) {

                        val listAdapter =
                            Categories_Adapter(
                                context!!.applicationContext,
                                loginmodel.data!! as List<Sections_Response.Data>
                            , ItemAnimation.FADE_IN
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
        if(categories.typeSection.equals("1")){
            var productsByid=CleanService()
            val bundle = Bundle()
            bundle.putParcelable("CategoryItem", categories)
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }else {
            var productsByid=SubCategories()
            val bundle = Bundle()
            bundle.putParcelable("CategoryItem", categories)
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

    fun checkNetwork(){
        if(!NetworkCheck.isConnect(context!!.applicationContext)) {
            startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
        }

    }

}
