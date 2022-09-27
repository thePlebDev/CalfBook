package com.elliottsoftware.calfbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentForgotPasswordBinding
import com.elliottsoftware.calfbook.databinding.FragmentMainBinding


/**
 * A simple [Fragment] subclass.

 */
class ForgotPassword : Fragment(), View.OnClickListener {

    private var _binding:FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var email:EditText
    private lateinit var resetPasswordButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        email = binding.email
        resetPasswordButton = binding.forgotPasswordButton
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


    override fun onClick(p0: View?) {
        val emailString = this.email.text.toString().trim()
        if(emailString.isEmpty()){
            this.email.error = "Email is required"
            this.email.requestFocus()
            return
        }

    }
}