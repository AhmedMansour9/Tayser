package com.tayser.Fragments


import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tayser.Model.ContactUs_Response
import com.tayser.R
import com.tayser.ViewModel.ContactUs_ViewModel
import kotlinx.android.synthetic.main.fragment_contact__us.view.*

/**
 * A simple [Fragment] subclass.
 */
class Contact_Us : Fragment() {
    lateinit var contactUs_viewModel: ContactUs_ViewModel

    lateinit var root:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_contact__us, container, false)
        contactUs_viewModel = ViewModelProviders.of(this).get(ContactUs_ViewModel::class.java)
        Contact()


        return root
    }

    fun Contact() {

        root.SendMesg.setOnClickListener(View.OnClickListener { view ->
            if (!ValidateEmail() or !ValidateMessage()or !ValidateName()or !ValidatePhone()) {
                return@OnClickListener
            }
            if (root.E_Name.getText().toString() != ""
                    && root.E_Phone.getText().toString() != ""
                    && root.E_Message.getText().toString() != "" && root.E_Email.getText().toString() != ""
            ) {
                root.SendMesg.hideKeyboard()
                root.SendMesg.setEnabled(false)
                root.progross.visibility=View.VISIBLE
                contactUs_viewModel.getContactus(
                        root.E_Email.getText().toString(),
                        root.E_Name.getText().toString(),
                        root.E_Phone.getText().toString(),
                        root.E_Message.getText().toString(),
                        context!!.applicationContext
                ).observe(viewLifecycleOwner, Observer<ContactUs_Response> { tripsData ->
                    root.SendMesg.setEnabled(true)
                    root.progross.visibility=View.GONE
                    if (tripsData != null) {
                        root.E_Email.setText(null)
                        root.E_Name.setText(null)
                        root.E_Phone.setText(null)
                        root.E_Message.setText(null)
                        Toast.makeText(
                                context,
                                resources.getString(R.string.sendsuccess),
                                Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        Toast.makeText(
                                context,
                                resources.getString(R.string.failedmsg),
                                Toast.LENGTH_SHORT
                        ).show()

                    }
                })

            }
        })


    }

    private fun ValidateName(): Boolean {
        val EMAIL = root.E_Name.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty()) {
            root.E_Name.error="Please Insert Name"
            return false
        }
        return true
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun ValidatePhone(): Boolean {
        val EMAIL = root.E_Phone.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty() ) {
            root.E_Phone.error="Please Insert Phone"
            return false
        }
        return true
    }
    private fun ValidateMessage(): Boolean {
        val EMAIL = root.E_Message.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty() ) {
            root.E_Message.error="Please Message Phone"
            return false
        }
        return true
    }
    private fun ValidateEmail(): Boolean {
        val EMAIL = root.E_Email.getText().toString().trim({ it <= ' ' })
        if (EMAIL.isEmpty() || !isValidEmail(EMAIL)) {
            root.E_Email.error="Please Insert Email"
            //            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false
        } else if (!root.E_Email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
            root.E_Email.error="Please Insert Email"
            //            Toast.makeText(getContext(), ""+getResources().getString(R.string.insertemail), Toast.LENGTH_SHORT).show();
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
