package com.tayser.Fragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.tayser.Activities.NoItemInternetImage
import com.tayser.Loading
import com.tayser.Model.EditProfile_ViiewModel
import com.tayser.Model.Edit_ProfileResponse
import com.tayser.Model.Profile_Response
import com.tayser.R
import com.tayser.ViewModel.Profile_ViewModel
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.fragment_my_profile.view.*
import kotlinx.android.synthetic.main.fragment_my_profile.view.E_Email
import kotlinx.android.synthetic.main.fragment_my_profile.view.E_Phone

/**
 * A simple [Fragment] subclass.
 */
class MyProfile : Fragment() {
   lateinit var root:View
    private lateinit var DataSaver: SharedPreferences
    lateinit var UserToken: String
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_my_profile, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)

        Get_Profle()
        Rela_ChangeData()
        Rela_Password()
        Btn_ConfirmEmail()
        Btn_ConFirmpassword()

        return root
    }
    fun Rela_ChangeData() {
        root.Constrain_ChangeData.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundResource(R.drawable.bc_selected);
            root.Constrain_ChangeData.setTextColor(
                    Color.BLACK)
            root.Constrain_ChangePass.setBackgroundColor(
                    Color.TRANSPARENT
            )
            root.Constrain_ChangePass.setTextColor(Color.WHITE)
            root.constrain_Password.visibility = View.GONE
            root.constrain_Login.visibility = View.VISIBLE
        }


    }

    fun Rela_Password() {
        root.Constrain_ChangePass.setOnClickListener() {
            root.Constrain_ChangeData.setBackgroundColor(Color.TRANSPARENT);
            root.Constrain_ChangeData.setTextColor(
                    Color.WHITE)
            root.Constrain_ChangePass.setBackgroundResource(
                    R.drawable.bc_selected
            )
            root.Constrain_ChangePass.setTextColor(Color.BLACK)

            root.constrain_Password.visibility = View.VISIBLE
            root.constrain_Login.visibility = View.GONE

        }


    }


    fun Get_Profle() {
        var Prof_ViewModel: Profile_ViewModel =
                ViewModelProviders.of(this)[Profile_ViewModel::class.java]
//        root.progressBarLogin.visibility = View.VISIBLE
        Loading.Show(context!!)
        Prof_ViewModel.getData(
                DataSaver.getString("token",null)!!,
                context!!.applicationContext
        ).observe(viewLifecycleOwner,
                Observer<Profile_Response> { loginmodel ->
                   Loading.Disable()
                    if (loginmodel != null) {
                        root.E_NameProfile.setText(loginmodel.data.get(0).name)
                        root.E_Phone.setText(loginmodel.data.get(0).phone)
                        root.E_Email.setText(loginmodel.data.get(0).email)
                    } else {
                        Toast.makeText(
                                context!!.applicationContext,
                                context!!.applicationContext.getString(R.string.failedmsg),
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
        )
    }

    fun Btn_ConfirmEmail() {
        root.Btn_ConfirmEmail.setOnClickListener() {

            var Name = root.E_NameProfile.text.toString()
            var Phone = root.E_Phone.text.toString()
            var email = root.E_Email.text.toString()
            if (Name.isEmpty()) {
                root.E_NameProfile.error = "Name required"
                root.E_NameProfile.requestFocus()
            }
            if (Phone.isEmpty()) {
                root.E_Phone.error = "Phone required"
                root.E_Phone.requestFocus()
            }
            if (email.isEmpty()) {
                root.E_Email.error = "Email required"
                root.E_Email.requestFocus()
            }
            if (!isEmailValid(email)) {
                root.E_Email.error = "Valid Email required"
                root.E_Email.requestFocus()
            } else if (!Name.isEmpty() && !Phone.isEmpty() && !email.isEmpty()) {
                if(!NetworkCheck.isConnect(context!!.applicationContext)) {
                    startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
                }
                    var editProf_ViewModel: EditProfile_ViiewModel =
                            ViewModelProviders.of(this)[EditProfile_ViiewModel::class.java]
                Loading.Show(context!!)

                    editProf_ViewModel.getData(
                            DataSaver.getString("token",null)!!,
                            email,
                            Phone,
                            Name,
                            context!!.applicationContext
                    ).observe(viewLifecycleOwner,
                            Observer<Edit_ProfileResponse> { loginmodel ->
                                Loading.Disable()
                                if (loginmodel != null) {
                                    Toast.makeText(
                                            context!!.applicationContext,
                                            context!!.applicationContext.getString(R.string.updated),
                                            Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                            context!!.applicationContext,
                                            context!!.applicationContext.getString(R.string.failedmsg),
                                            Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    )

            }
        }

    }

    fun isEmailValid(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email);
    }

    fun Btn_ConFirmpassword() {
        root.Btn_ConfirmPass.setOnClickListener(){
            var Password = root.e_Password.text.toString().trim()
            var ConfirmPassword = root.e_PasswordConFirm.text.toString().trim()
            if (Password.isEmpty()) {
                root.e_Password.error = "Password required"
                root.e_Password.requestFocus()
            }
            if (ConfirmPassword.isEmpty()) {
                root.e_PasswordConFirm.error = "ConFirm Password required"
                root.e_PasswordConFirm.requestFocus()
            }
            if (Password.length < 6 || Password.length > 16) {
                root.e_Password.error =
                        "Min password must be at least 6 Chrachter and  max 16 Chrachter "
                root.e_Password.requestFocus()
            }
            if (ConfirmPassword.length < 6 || ConfirmPassword.length > 16) {
                root.e_PasswordConFirm.error =
                        "Min password must be at least 6 Chrachter and  max 16 Chrachter "
                root.e_PasswordConFirm.requestFocus()
            }


            else if (!Password.isEmpty() && Password.length >= 6 || Password.length <= 16
                    && ConfirmPassword.length >= 6 || ConfirmPassword.length <= 16
            ) {
                if(!NetworkCheck.isConnect(context!!.applicationContext)) {
                    startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
                }
                    var editProf_ViewModel: EditProfile_ViiewModel =
                            ViewModelProviders.of(this)[EditProfile_ViiewModel::class.java]
                Loading.Show(context!!)
                    editProf_ViewModel.getConfirmPassowrd(
                            UserToken,
                            Password,
                            ConfirmPassword,
                            context!!.applicationContext
                    ).observe(viewLifecycleOwner,
                            Observer<Edit_ProfileResponse> { loginmodel ->
                               Loading.Disable()
                                if (loginmodel != null) {
                                    if(loginmodel.data.equals("Password does not match ")){
                                        Toast.makeText(
                                                context!!.applicationContext,
                                                getString(R.string.oldpasswrong),
                                                Toast.LENGTH_LONG
                                        ).show()
                                    }else if (loginmodel.data.equals("Password changed successfully")) {
                                        Toast.makeText(
                                                context!!.applicationContext,
                                                getString(R.string.updatedpassword),
                                                Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                            context!!.applicationContext,
                                            getString(R.string.failedmsg),
                                            Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    )
                }
                }


        }



}
