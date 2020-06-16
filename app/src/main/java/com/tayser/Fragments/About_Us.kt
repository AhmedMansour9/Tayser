package com.tayser.Fragments


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tayser.Loading
import com.tayser.Model.About_Response
import com.tayser.R
import com.tayser.ViewModel.AboutUs_ViewModel
import com.tayser.utils.ChangeLanguage
import kotlinx.android.synthetic.main.fragment_about__us.view.*

/**
 * A simple [Fragment] subclass.
 */
class About_Us : Fragment() {
   lateinit var root:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_about__us, container, false)
        Get_Aboutus()




        return root
    }

    fun Get_Aboutus() {
        val aboutUs_viewModel = ViewModelProvider.NewInstanceFactory().create(
                AboutUs_ViewModel::class.java)
        Loading.Show(context!!)
        aboutUs_viewModel.getAboutus(
            ChangeLanguage.getLanguage( context!!.applicationContext),
                context!!.applicationContext).observe(viewLifecycleOwner,
                Observer<List<About_Response.Data>> { tripsData ->
                    Loading.Disable()
                    if (tripsData != null) {
                        //                    Title.setText(tripsData.get(0).getTitle());
                        root.Descrption.setText(tripsData[0].description)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            root.Descrption.setText(
                                    Html.fromHtml(
                                            tripsData[0].description,
                                            Html.FROM_HTML_MODE_LEGACY  )
                            )
                        } else {
                            root.Descrption.setText(Html.fromHtml(tripsData[0].description))
                        }
                        Glide.with(context!!.applicationContext)
                                .load("https://atcs-egy.com" + tripsData[0].image)
                                .into(root.img_about)
                    }
                })
    }
}
