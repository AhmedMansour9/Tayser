package com.tayser.Fragments


import android.content.Context
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
import com.tayser.Model.*

import com.tayser.R
import com.tayser.ViewModel.Cart_ViewModel
import com.tayser.ViewModel.SliderHome_ViewModel
import kotlinx.android.synthetic.main.fragment_sub_categories.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.*
import kotlinx.android.synthetic.main.fragment_sub_categories.view.T_Title
import kotlinx.android.synthetic.main.fragment_sub_categories.view.T_notification_numde
import kotlinx.android.synthetic.main.fragment_sub_categories.view.viewPagerSub
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
    private var CartNumber = 0
    private var currentPage = 0
    private var NUM_PAGES = 0
    lateinit var categories: Sections_Response.Data
    var bundle=Bundle()
    lateinit var allproducts: Cart_ViewModel
    private lateinit var DataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_sub_categories, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)

        getData()
        openProducts()
        openMaintenence()
        openCart()
        openEmergency()
        return root
    }

    private fun openProducts() {
      root.Card_Products.setOnClickListener(){
          var productsByid=Products()
          val bundle = Bundle()
          bundle.putParcelable("CategoryItem", categories)
          productsByid.arguments=bundle
          activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
              .addToBackStack(null).commit()

      }


    }

    private fun openMaintenence() {
        root.Card_Mainten.setOnClickListener(){
            var productsByid=Maintenence_Service()
            val bundle = Bundle()
            bundle.putParcelable("CategoryItem", categories)
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }


    }

    private fun openEmergency() {
        root.Card_Emergency.setOnClickListener(){
            var productsByid=Emergency()
            val bundle = Bundle()
            bundle.putParcelable("CategoryItem", categories)
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }


    }

    private fun openCart() {
        root.Img_SubCart.setOnClickListener(){

            var productsByid=tabs_cart()
            val bundle = Bundle()
            productsByid.arguments=bundle
            activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }
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
            CartNumber=0
            getNumberOfCart()
            getNumberCartService()

    }


    fun getSlider(id:String){
        var SliderHome: SliderHome_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            SliderHome_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            SliderHome.getSliderSub(ChangeLanguage.getLanguage(context!!.applicationContext),id, it).observe(viewLifecycleOwner, Observer<SliderHome_Model> { loginmodel ->
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
