package com.elliottsoftware.calfbook.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentRegisterBinding
import com.elliottsoftware.calfbook.models.firebase.User
import com.elliottsoftware.calfbook.util.SnackBarActions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.

 */
class Register : Fragment() ,View.OnClickListener{
    private var _binding:FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var  username:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var registerUser:Button
    private lateinit var progressBar:ProgressBar
    private lateinit var database: DatabaseReference
   private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val view = binding.root
        username = binding.username
        email = binding.email
        password = binding.password
        registerUser = binding.registerButton
        progressBar = binding.progressBar
        database = Firebase.database.reference
        registerUser.setOnClickListener(this)

        return view
    }



    override fun onClick(view: View?) {
        checkClicked(view)
    }

    private fun checkClicked(view:View?):Unit{
        when(view?.id){
            R.id.registerButton -> registerUser(view!!)
        }
    }

    private fun registerUser(view:View):Unit{
        val email = this.email.text.toString().trim()
        val username = this.username.text.toString().trim()
        val password = this.password.text.toString().trim()

        if(username.isEmpty()){
            this.username.error = "Username is required"
            this.username.requestFocus()
            return
        }
        if(email.isEmpty()){
            this.email.error = "Email is required"
            this.email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.error = "Please provide valid email"
            this.email.requestFocus()
            return
        }
        if(password.isEmpty()){
            this.password.error = "Password is required"
            this.password.requestFocus()
            return
        }
        if(password.length < 8 ){
            this.password.error = "Password length minimum is 8 characters"
            this.password.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        createAccount(email,password,username,view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun createAccount(email: String, password: String,username:String,view: View) {
        // [START create_user_with_email]
        activity?.let {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI and register the user

                        addUserToCloudDatabase(username,email,view)


                        //NAVIGATE TO THE HOME PAGE
                        Navigation.findNavController(view).navigate(R.id.action_register3_to_mainFragment)
                       // val user = auth.currentUser
                       // user?.uid
                    }


                    else {
                        // If sign in fails, display a message to the user.
                        progressBar.visibility = View.INVISIBLE

                        val snackBar = Snackbar.make(view,task.exception?.message.toString(), Snackbar.LENGTH_LONG)
                        snackBar.setAction("DISMISS", SnackBarActions(snackBar))
                        snackBar.show()


                    }
                }
        }
        // [END create_user_with_email]
    }

    private fun addUserToCloudDatabase(username:String, email: String,view:View){
        progressBar.visibility = View.INVISIBLE
        val snackBar = Snackbar.make(view,"Account created", Snackbar.LENGTH_LONG)
        snackBar.setAction("DISMISS", SnackBarActions(snackBar))
        snackBar.show()
        //CREATE THE USER AND SAVE IT TO THE DATABASE
        val user = User(username,email)
        val documentReference = db.collection("users").document(email)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("MEATBALL", "DocumentSnapshot added with ID: $documentReference")
            }
    }
}