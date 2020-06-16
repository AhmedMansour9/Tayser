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
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tayser.Loading
import com.tayser.utils.ChangeLanguage
import com.tayser.utils.CustomToast
import com.tayser.Model.Register_Model
import com.tayser.R
import com.tayser.ViewModel.Register_ViewModel
import com.tayser.utils.NetworkCheck
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

    lateinit var mAuth: FirebaseAuth
    lateinit var googleApiClient: GoogleApiClient
    var RequestSignInCode:Int=7
    lateinit var googleSignInOptions: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeLanguage()
        if(!NetworkCheck.isConnect(this)) {
            startActivity(Intent(this, NoItemInternetImage::class.java))
        }
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance();
        GoogleSignOpition();
        getLanguage()
        Login_Google()
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

    private fun GoogleSignOpition() {

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        googleApiClient =  GoogleApiClient.Builder(applicationContext)
//                .enableAutoManage(applicationContext)
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build();
    }


    private  fun Login_Google(){
        Btn_loginGoogle.setOnClickListener(){
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(signInIntent, RequestSignInCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RequestSignInCode){
            var googleSignInResult: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                var googleSignInAccount: GoogleSignInAccount = googleSignInResult.signInAccount!!;
                FirebaseUserAuth(googleSignInAccount);
            }

        }

    }

    private fun FirebaseUserAuth(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    LoginFaceBooks(
                        mAuth.currentUser!!.uid,
                        mAuth.currentUser!!.email,
                        mAuth.currentUser!!.displayName
                    )

                } else {
                    // If sign in fails, display a message to the user.
                }
            }
    }

    fun LoginFaceBooks(id:String?,email:String? ,name:String?){
           Loading.Show(this)
            var RegisterViewModel =  ViewModelProvider.NewInstanceFactory().create(
                Register_ViewModel::class.java)
            RegisterViewModel.getLoginFacebook(id, email,name,applicationContext).observe(this,
                Observer<Register_Model> { loginmodel ->
                    Loading.Disable()
                    if (loginmodel != null) {
                        val customer_id = loginmodel.data.userToken
                        dataSaver.edit().putString("token", customer_id).apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {

                    }
                }
            )



    }



    fun openHome(){
        Btn_login.setOnClickListener(){
            if (!ValidateEmailLogin() or !ValidatePasswordLogin()) {
                return@setOnClickListener
            }

                var RegisterViewModel =  ViewModelProvider.NewInstanceFactory().create(
                    Register_ViewModel::class.java)
                Loading.Show(this)
                Btn_login.isEnabled=false
                Btn_login.hideKeyboard()
                RegisterViewModel.getLogin(E_EmailLogin.text.toString(), E_PasswordLogin.text.toString(),applicationContext).observe(this,
                    Observer<Register_Model> { loginmodel ->
                        Btn_login.isEnabled=true
                        Loading.Disable()
                        if (loginmodel != null) {
                            CustomToast.toastIconSuccess(applicationContext.getString(R.string.success)+" "+loginmodel.data.name
                                ,this)

                            val customer_id = loginmodel.data.userToken
                            dataSaver.edit().putString("token", customer_id).apply()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val status: Boolean = RegisterViewModel.getStatus()
                            if (status == true) {
                                CustomToast.toastIconError(applicationContext.getString(R.string.wrongemailorpass)
                                    ,this)

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
