package com.tayser

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD

class Loading {

    companion object{
        private lateinit var hud: KProgressHUD
       fun Show(context:Context){
           hud =  KProgressHUD.create(context)
               .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//               .setLabel("loading")
               //.setDetailsLabel("Downloading data")
               .setCancellable(false)
               .setAnimationSpeed(2)
               .setDimAmount(0.5f)
               .show();
       }
       fun Disable(){
           hud.dismiss()
       }
    }



}