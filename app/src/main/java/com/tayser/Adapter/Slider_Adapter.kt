package com.tayser.Adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.tayser.Model.SliderHome_Model
import com.tayser.R

class Slider_Adapter (private val context: Context, private val imageModelArrayList: ArrayList<SliderHome_Model.Slider_Home>) : PagerAdapter() {
    private val inflater: LayoutInflater


    init {
        inflater = LayoutInflater.from(context)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.row_slider, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView

        Picasso.get()
            .load("http://atcs-egy.com" + imageModelArrayList.get(position).image)
                .resize(500,500)
            .into(imageView)

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}
