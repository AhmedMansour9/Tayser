package com.tayser.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.tayser.Activities.Language
import com.tayser.Activities.Login

import com.tayser.R
import kotlinx.android.synthetic.main.fragment_more.view.*

/**
 * A simple [Fragment] subclass.
 */
class More : Fragment(),View.OnClickListener {
    lateinit var root:View
    private lateinit var dataSaver: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_more, container, false)

        dataSaver = PreferenceManager.getDefaultSharedPreferences(context);
        init()


        return root
    }
    fun init(){
        root.Img_profile.setOnClickListener(this)
        root.T_MyProfile.setOnClickListener(this)
        root.Img_about.setOnClickListener(this)
        root.T_Abouts.setOnClickListener(this)
        root.Img_Language.setOnClickListener(this)
        root.T_Language.setOnClickListener(this)
        root.Img_Contact.setOnClickListener(this)
        root.T_Contactus.setOnClickListener(this)
        root.Rela_Logout.setOnClickListener(this)

    }

    override fun onClick(dd: View?) {
        val item_id = dd!!.id
        when(item_id) {
            R.id.Img_profile ->  activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                MyProfile()).addToBackStack(null).commit()
            R.id.T_MyProfile ->  activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                MyProfile()).addToBackStack(null).commit()
            R.id.Img_about ->  activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                About_Us()).addToBackStack(null).commit()
            R.id.T_Abouts -> {
                activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                    About_Us()).addToBackStack(null).commit()
            }
            R.id.Img_Language -> {
                var intent= Intent(context!!.applicationContext, Language::class.java)
                startActivity(intent)
            }
            R.id.T_Language ->  {
                var intent= Intent(context!!.applicationContext,Language::class.java)
                startActivity(intent)
            }
            R.id.Img_Contact ->  activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                Contact_Us()).addToBackStack(null).commit()
            R.id.T_Contactus ->  activity!!.supportFragmentManager.beginTransaction().add(R.id.Rela_More,
                Contact_Us()).addToBackStack(null).commit()
            R.id.Rela_Logout -> {
                dataSaver.edit().putString("token", null).apply()
                val intent = Intent(context, Login::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity!!.finish()
            }
        }
    }


}
