package com.tayser.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.tayser.ChangeLanguage
import com.tayser.Model.Register_Model
import com.tayser.R
import com.tayser.ViewModel.Register_ViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    var DeviceLang:String?= String()
    var DeviceToken:String?= String()
    private lateinit var dataSaver: SharedPreferences
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=\\S+$)" +           //no white spaces
                ".{5,}"                //at least 4 characters
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getLanguage()
        getUserToken()
        openRegister()
        openHome()

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

    private fun openRegister() {
        Btn_Signup.setOnClickListener(){
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun openHome(){
        Btn_login.setOnClickListener(){
            if (!ValidateEmailLogin() or !ValidatePasswordLogin()) {
                return@setOnClickListener
            }

                var RegisterViewModel =  ViewModelProvider.NewInstanceFactory().create(
                    Register_ViewModel::class.java)
                progressBarLogin.visibility= View.VISIBLE
                Btn_login.isEnabled=false
                Btn_login.hideKeyboard()
                RegisterViewModel.getLogin(E_EmailLogin.text.toString(), E_PasswordLogin.text.toString(),applicationContext).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        Btn_login.isEnabled=true
                        progressBarLogin.visibility = View.GONE
                        if (loginmodel != null) {
                            val customer_id = loginmodel.data.userToken
                            dataSaver.edit().putString("token", customer_id).apply()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                Toast.makeText(
                                    applicationContext,
                                    applicationContext.getString(R.string.wrongemailorpass),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                )


        }

    }



    private fun ValidateEmailLogin():Boolean{
        val Fullname=E_EmailLogin.text.toString()
        if(Fullname.isEmpty()){
            E_EmailLogin.error=resources.getString(R.string.feildempty)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Fullname).matches()) run {
            E_EmailLogin.error =
                resources.getString(R.string.validemail)
            return false
        }
        else {
            E_EmailLogin.error=null

            return true
        }
    }

    private fun ValidatePasswordLogin():Boolean{
        val Fullname=E_PasswordLogin.text.toString()
        if(Fullname.isEmpty()){
            E_PasswordLogin.error=resources.getString(R.string.feildempty)
            return false
        } else if (!PASSWORD_PATTERN.matcher(Fullname).matches()) run {
            E_PasswordLogin.error =
                resources.getString(R.string.pasweak)
            return false
        }
        else {
            E_PasswordLogin.error=null
            return true
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}
