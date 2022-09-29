package com.elliottsoftware.calfbook.presentation.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.elliottsoftware.calfbook.databinding.FragmentForgotPasswordBinding
import com.elliottsoftware.calfbook.util.SnackBarActions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.

 */
class ForgotPassword : Fragment(), View.OnClickListener {

    private var _binding:FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var email:EditText
    private lateinit var resetPasswordButton:Button
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        email = binding.email
        resetPasswordButton = binding.forgotPasswordButton
        progressBar = binding.progressBar
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPasswordButton.setOnClickListener(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(view: View?) {
        val emailString = this.email.text.toString().trim()
        if(emailString.isEmpty()){
            this.email.error = "Email is required"
            this.email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            this.email.error = "Please provide valid email"
            this.email.requestFocus()
            return
        }
        sendResetEmail(emailString,view!!)

    }
    private fun sendResetEmail(emailString:String,view:View){
        progressBar.visibility = View.VISIBLE
        activity?.let {
            auth.sendPasswordResetEmail(emailString).addOnCompleteListener(it){ task ->
                if (task.isSuccessful){
                    progressBar.visibility = View.INVISIBLE
                    val snackBar = Snackbar.make(view,"Check email to reset password", Snackbar.LENGTH_LONG)
                    snackBar.setAction("DISMISS", SnackBarActions(snackBar))
                    snackBar.show()
                }
                else{
                    progressBar.visibility = View.INVISIBLE
                    val snackBar = Snackbar.make(view,"Error, please try again", Snackbar.LENGTH_LONG)
                    snackBar.setAction("DISMISS", SnackBarActions(snackBar))
                    snackBar.show()

                }

            }
        }

    }
}