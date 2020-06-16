package com.tayser.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.tayser.R
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.activity_no_item_internet_image.*

class NoItemInternetImage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_item_internet_image)
        initComponent()

    }
         fun initComponent() {
            progress_bar.setVisibility(View.GONE)
            lyt_no_connection.setVisibility(View.VISIBLE)
            bt_retry.setOnClickListener(View.OnClickListener {
                progress_bar.setVisibility(View.VISIBLE)
                lyt_no_connection.setVisibility(View.GONE)
                Handler().postDelayed({
                    if(NetworkCheck.isConnect(this)) {
                      finish()
                    }
                    progress_bar.setVisibility(View.GONE)
                    lyt_no_connection.setVisibility(View.VISIBLE)
                }, 1000)
            })
        }
    }