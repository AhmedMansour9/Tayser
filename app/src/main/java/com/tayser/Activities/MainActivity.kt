package com.tayser.Activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tayser.utils.ChangeLanguage
import com.tayser.Fragments.Home
import com.tayser.Fragments.More
import com.tayser.Fragments.Notifications
import com.tayser.Fragments.Orders
import com.tayser.R
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var view1: View
    lateinit var view2:View
    lateinit var view3:View
    lateinit var view4:View
    lateinit var Img_Home: ImageView
    lateinit var T_Home: TextView
    lateinit var Img_Orders: ImageView
    lateinit var T_Orders: TextView
    lateinit var Img_Notifications: ImageView
    lateinit var T_Notifications: TextView
    lateinit var Img_More: ImageView
    lateinit var T_More: TextView

    companion object {
        lateinit var tabLayout:TabLayout
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChangeLanguage.changeLang(this)
        if(!NetworkCheck.isConnect(this)) {
            startActivity(Intent(this, NoItemInternetImage::class.java))
        }
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabs)
        setupViewPager(viewpager)
        tabLayout.setupWithViewPager(viewpager)
        tabLayout.rotationX = 180f
        setupTabIcons()
        selectedTabs()
    }

    private fun setupViewPager(viewpager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(Home(), "")
        adapter.addFrag(Orders(), "")
        adapter.addFrag(Notifications(), "")
        adapter.addFrag(More(), "")
        viewpager?.setAdapter(adapter)

    }
    private fun selectedTabs() {
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Selected(tab!!.position)
            }
            override fun onTabUnselected(tab:TabLayout.Tab?){
            }
            override fun onTabReselected(tab:TabLayout.Tab?) {
            }
        })
    }

    private fun setupTabIcons() {
        view1 = layoutInflater.inflate(R.layout.ic_tabhome, null)
        view2 = layoutInflater.inflate(R.layout.ic_taborders, null)
        view3 = layoutInflater.inflate(R.layout.ic_tabnotifications, null)
        view4 = layoutInflater.inflate(R.layout.ic_tabmore, null)
        tabs.getTabAt(0)!!.setCustomView(view1)
        tabs.getTabAt(1)!!.setCustomView(view2)
        tabs.getTabAt(2)!!.setCustomView(view3)
        tabs.getTabAt(3)!!.setCustomView(view4)

    }


    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentStatePagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

    }

    private fun Selected(current: Int) {
        Img_Home=view1.findViewById(R.id.Img_Home)
        T_Home=view1.findViewById(R.id.T_Home)
        Img_Orders=view2.findViewById(R.id.Img_Orders)
        T_Orders=view2.findViewById(R.id.T_Orders)
        Img_Notifications=view3.findViewById(R.id.Img_Notifications)
        T_Notifications=view3.findViewById(R.id.T_Notifications)
        Img_More=view4.findViewById(R.id.Img_More)
        T_More=view4.findViewById(R.id.T_More)
        when (current) {
            0 -> {
                Img_Home.setColorFilter(ContextCompat.getColor(this, R.color.darkred));
                T_Home.setTextColor(ContextCompat.getColor(this, R.color.darkred))
                Img_Orders.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Orders.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Notifications.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Notifications.setTextColor(Color.parseColor("#bbbbbb"))
                Img_More.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_More.setTextColor(Color.parseColor("#bbbbbb"))
            }
            1 -> {
                Img_Home.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Home.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Orders.setColorFilter(ContextCompat.getColor(this, R.color.darkred));
                T_Orders.setTextColor(ContextCompat.getColor(this, R.color.darkred))
                Img_Notifications.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Notifications.setTextColor(Color.parseColor("#bbbbbb"))
                Img_More.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_More.setTextColor(Color.parseColor("#bbbbbb"))
            }
            2 -> {
                Img_Home.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Home.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Orders.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Orders.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Notifications.setColorFilter(ContextCompat.getColor(this, R.color.darkred));
                T_Notifications.setTextColor(ContextCompat.getColor(this, R.color.darkred))
                Img_More.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_More.setTextColor(Color.parseColor("#bbbbbb"))
            }
            3 -> {
                Img_Home.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Home.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Orders.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Orders.setTextColor(Color.parseColor("#bbbbbb"))
                Img_Notifications.setColorFilter(ContextCompat.getColor(this, R.color.darkwhite));
                T_Notifications.setTextColor(Color.parseColor("#bbbbbb"))
                Img_More.setColorFilter(ContextCompat.getColor(this, R.color.darkred));
                T_More.setTextColor(ContextCompat.getColor(this, R.color.darkred))

            }
        }

    }
}
