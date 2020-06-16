package com.tayser.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.tayser.Loading
import com.tayser.utils.ChangeLanguage
import com.tayser.utils.CustomToast
import com.tayser.Model.Register_Model
import com.tayser.R
import com.tayser.ViewModel.Register_ViewModel
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class Register : AppCompatActivity() {
    var DeviceLang:String?= String()
    var DeviceToken:String?= String()

    private lateinit var dataSaver: SharedPreferences
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=\\S+$)" +           //no white spaces
                ".{7,}"                //at least 4 characters
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!NetworkCheck.isConnect(this)) {
            startActivity(Intent(this, NoItemInternetImage::class.java))
        }
        setContentView(R.layout.activity_register)
        getLanguage()
        getUserToken()



    }

    private fun getUserToken(){
        dataSaver = PreferenceManager.getDefaultSharedPreferences(this);
        DeviceToken=dataSaver.getString("token",null)
    }

    private fun changeLanguage(){
        ChangeLanguage.changeLang(this)
    }
    private fun getLanguage(){
        DeviceLang= ChangeLanguage.getLanguage(this)
    }


    fun Btn_Register(view: View) {
        if (!ValidateEmailRegister() or !ValidatePasswordRegister() or !ValidatePhoneRegister() or !ValidateRegister()) {
            return
        }

        var RegisterViewModel =
            ViewModelProvider.NewInstanceFactory().create(Register_ViewModel::class.java)
        Loading.Show(this)

        view.isEnabled = false
        view.hideKeyboard()
        RegisterViewModel.getData(
            E_EmailRegister.text.toString(),
            E_PasswordRegister.text.toString(),
            E_FullName.text.toString(),
            E_Phone.text.toString(),
            applicationContext
        ).observe(this,
            Observer<Register_Model> { loginmodel ->
                view.isEnabled = true
               Loading.Disable()
                if (loginmodel != null) {
                    val customer_id = loginmodel.data.userToken
                    dataSaver.edit().putString("token", customer_id).apply()
                    val intent = Intent(this, MainActivity::class.java)
                    CustomToast.toastIconSuccess(applicationContext.getString(R.string.success)+" "+loginmodel.data.name
                        ,this)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else {
                    val status: Boolean = RegisterViewModel.getStatus()
                    if (status == true) {
                        val status: Boolean = RegisterViewModel.getStatus()
                        if (status == true) {

                            CustomToast.toastIconError(applicationContext.getString(R.string.email_used)
                            ,this)
                        }

                    }
                }
            }
        )




    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun ValidateRegister():Boolean{
        val Fullname=E_FullName.text.toString()
        if(Fullname.isEmpty()){
            E_FullName.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_FullName.error=null
            return true
        }
    }

    private fun ValidatePhoneRegister():Boolean{
        val Fullname=E_Phone.text.toString()
        if(Fullname.isEmpty()){
            E_Phone.error=resources.getString(R.string.feildempty)
            return false
        }else {
            E_Phone.error=null
            return true
        }
    }
    private fun ValidateEmailRegister():Boolean{
        val Fullname=E_EmailRegister.text.toString()
        if(Fullname.isEmpty()){
            E_EmailRegister.error=resources.getString(R.string.feildempty)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Fullname).matches()) run {
            E_EmailRegister.error =
                resources.getString(R.string.validemail)
            return false
        }
        else {
            E_EmailRegister.error=null
            return true
        }
    }
    private fun ValidatePasswordRegister():Boolean{
        val Fullname=E_PasswordRegister.text.toString()
        if(Fullname.isEmpty()){
            E_PasswordRegister.error=resources.getString(R.string.feildempty)
            return false
        } else if (!PASSWORD_PATTERN.matcher(Fullname).matches()) run {
            E_PasswordRegister.error =
                resources.getString(R.string.pasweak)
            return false
        }
        else {
            E_PasswordRegister.error=null
            return true
        }
    }
}
