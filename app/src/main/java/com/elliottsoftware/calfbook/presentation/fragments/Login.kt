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
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigation
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentLoginBinding
import com.elliottsoftware.calfbook.util.SnackBarActions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 */
class Login : Fragment(), View.OnClickListener{
    private var _binding: FragmentLoginBinding? = null
    //this property is only valid between onCreateView() and onDestroyView()
    private val binding get() = _binding!!
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var loginButton:Button
    private lateinit var register:TextView
    private lateinit var forgotPassword:TextView
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
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        email = binding.email;
        password = binding.password
        loginButton =binding.loginButton
        progressBar = binding.progressBar
        forgotPassword = binding.forgotPassword
        register = binding.register

        binding.composeView.apply {
            //A strategy for managing the underlying Composition of Compose UI
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                BannerCard("Calf Tracker","powered by Elliott Software")

            }
        }

        return view
    }


    @Composable
    fun BannerCard(banner: String,bannerDescription:String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(banner,fontSize = 40.sp,
                fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 0.dp,16.dp,0.dp,0.dp)
            )
            Text(bannerDescription,fontSize = 18.sp, fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,)
        }
    }


    //TODO: REFACTOR SO WE HAVE SEPARATE ONCLICK LISTENERS.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_login_to_register3)
        }
        forgotPassword.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_login_to_forgotPassword2)
        }
        loginButton.setOnClickListener(this)
    }

     override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Navigation.findNavController(view!!).navigate(R.id.action_login_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?):Unit {
        //VALIDATE EMAIL, NOT NULL AND EMAIL
        //VALIDATE PASSWORD IS NOT NULL
        val email = this.email.text.toString().trim()
        val password = this.password.text.toString().trim()
        if(email.isEmpty()){
            this.email.error = "Email required"
            this.email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.email.error = "Please provide valid email"
            this.email.requestFocus()
            return
        }
        if(password.isEmpty()){
            this.password.error = "Password required"
            this.password.requestFocus()
            return
        }
        progressBar.visibility = View.VISIBLE
        signinUser(email,password)

    }

    private fun signinUser(email:String,password:String){
        activity?.let {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        progressBar.visibility = View.INVISIBLE
                        // Sign in success, update UI with the signed-in user's information

                        Navigation.findNavController(view!!).navigate(LoginDirections.actionLoginToMainFragment())
                        //it.supportFragmentManager.popBackStack()

                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        progressBar.visibility = View.INVISIBLE
                        val snackBar = Snackbar.make(view!!,"Authentication failed", Snackbar.LENGTH_LONG)
                        snackBar.setAction("DISMISS", SnackBarActions(snackBar))
                        snackBar.show()

                    }
                }
        }
    }

}