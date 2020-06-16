package com.tayser.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.kaopiz.kprogresshud.KProgressHUD
import com.tayser.Model.AddToCart_Response
import com.tayser.Model.MessageEvent
import com.tayser.R
import com.tayser.ViewModel.AddToCart_ViewModel
import com.tayser.utils.CustomToast
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.activity_add_to_cart.*
import org.greenrobot.eventbus.EventBus


class AddToCart : AppCompatActivity() {
    var Total:Double=0.0
    var Counter:Int=1
    var price:Double=0.0
    var id:String?= String()
    private lateinit var DataSaver: SharedPreferences
    companion object{
        private lateinit var hud: KProgressHUD

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_add_to_cart)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        price=intent.getDoubleExtra("price",0.0)
        id=intent.getStringExtra("id")
        T_Total.text =resources.getString(R.string.total)+" "+ price.toString()+" "+ resources.getString(R.string.currency)

        Btn_Plus()
        Btn_Minus()
        AddToCart()
    }

    fun AddToCart(){
        Btn_AddToCart.setOnClickListener(){
            if(!NetworkCheck.isConnect(this)) {
                startActivity(Intent(this, NoItemInternetImage::class.java))
            }
                prograss_cart.visibility = View.VISIBLE
                var addtocart: AddToCart_ViewModel = ViewModelProvider.NewInstanceFactory().create(
                    AddToCart_ViewModel::class.java)
                this.applicationContext?.let {
                    addtocart.getData(DataSaver.getString("token", null)!!, id!!, T_Quantity.text.toString()
                        , it)
                        .observe(this, Observer<AddToCart_Response> { loginmodel ->
                            prograss_cart.visibility = View.GONE
                            EventBus.getDefault().postSticky(MessageEvent("cart"))
                            if (loginmodel != null) {
                                CustomToast.toastIconSuccess(loginmodel.data
                                    ,this)

                                finish()
                            }

                        })
                }

        }
    }
    fun Btn_Plus(){
        Img_Plus.setOnClickListener(){
            Counter++
            T_Quantity.text=Counter.toString()
            Total=T_Quantity.text.toString().toDouble()*price
            T_Total.text =resources.getString(R.string.total)+" "+ Total.toString()+" "+ resources.getString(R.string.currency)
        }
    }

    fun Btn_Minus(){
        Img_Minus.setOnClickListener(){
            if(Counter>1)
                Counter--
            T_Quantity.text=Counter.toString()
            Total=T_Quantity.text.toString().toDouble()*price
            T_Total.text = resources.getString(R.string.total)+" "+ Total.toString()+" "+resources.getString(R.string.currency)

        }
    }
}