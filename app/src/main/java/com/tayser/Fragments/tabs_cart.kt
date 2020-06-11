package com.tayser.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tayser.R
import kotlinx.android.synthetic.main.fragment_tabs_cart.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [tabs_cart.newInstance] factory method to
 * create an instance of this fragment.
 */
class tabs_cart : Fragment() {
    lateinit var root:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_tabs_cart, container, false)
        setupViewPager(root.view_pager)
        root.tab_layout.setupWithViewPager(root.view_pager)






        return root
    }


    private fun setupViewPager(viewPager: ViewPager) {
      var  viewPagerAdapter = SectionsPagerAdapter(activity?.supportFragmentManager)
        viewPagerAdapter.addFragment(Cart(), resources.getString(R.string.products)) // index 0
        viewPagerAdapter.addFragment(Services(),resources.getString(R.string.services) ) // index 1
        viewPager.adapter = viewPagerAdapter
    }


    private class SectionsPagerAdapter(manager: FragmentManager?) :
        FragmentStatePagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> =
            ArrayList()
        private val mFragmentTitleList: MutableList<String> =
            ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(
            fragment: Fragment,
            title: String
        ) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}