package com.elliottsoftware.calfbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 */
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    //this property is only valid between onCreateView() and onDestroyView()
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.register.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_login_to_register3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}