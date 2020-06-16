package com.tayser.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.preference.PreferenceManager
import com.tayser.R
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {
    private lateinit var DataSaver: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        DataSaver= PreferenceManager.getDefaultSharedPreferences(this)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        Img_logo.startAnimation(animation)


        Handler().postDelayed({
            val UserToken: String? =DataSaver.getString("token",null);
            if(UserToken!=null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)


    }
}
