package com.tayser.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ChangeLanguage {

    companion object {
        fun changeLang(context: Context) {
           var shared:  SharedPreferences = context.getSharedPreferences("Language", AppCompatActivity.MODE_PRIVATE)
            val Lan = shared.getString("Lann", null)
            if (Lan != null) {
                val locale = Locale(Lan!!)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                context.resources.updateConfiguration(
                        config,
                        context.resources.displayMetrics
                )
            }

        }
        fun getLanguage(context: Context):String{
            var DeviceLang=""
            var shared:  SharedPreferences = context.getSharedPreferences("Language", AppCompatActivity.MODE_PRIVATE)
            val Lan = shared.getString("Lann", null)
            if(Lan!=null){
                DeviceLang = Lan
            }else {
                DeviceLang = Locale.getDefault().language

            }
            return DeviceLang
        }
    }
}