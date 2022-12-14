package com.elliottsoftware.calfbook.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentCreateCalfBinding
import com.elliottsoftware.calfbook.domain.models.firebase.FireBaseCalf
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.util.CalfUtil
import com.elliottsoftware.calfbook.util.SnackBarActions
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


/**
 * A simple [Fragment] subclass.

 */
class CreateCalf : Fragment() {
    private var _binding: FragmentCreateCalfBinding? = null
    private val binding get() = _binding!!

    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }
    private lateinit var cancelButton: FloatingActionButton
    private lateinit var createButton: FloatingActionButton
    private lateinit var  calfTagNumber: EditText
    private lateinit var  cowTagNumber: EditText
    private lateinit var details: EditText
    private lateinit var cCIANumber: EditText
    private lateinit var birthWeight:EditText
    private lateinit var bull: RadioButton
    private lateinit var heifer: RadioButton
    private lateinit var calfDate: Date
    private lateinit var fabLeft: FloatingActionButton
    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateCalfBinding.inflate(inflater, container, false)
        val view = binding.root
        cancelButton = binding.newCalfFabLeft
        createButton = binding.newCalfFabRight
        calfTagNumber = binding.editCalfTag
        cowTagNumber = binding.editCalfTag
        details = binding.editDescription
        cCIANumber = binding.editCciaNumber
        birthWeight = binding.editBirthWeight
        bull = binding.radioBull
        heifer = binding.radioHeifer
        fabLeft = binding.newCalfFabLeft

        fabLeft.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_createCalf_to_mainFragment)
        }
        binding.newCalfFabRight.setOnClickListener{
            val calfTag:String = calfTagNumber.text.toString()
            val cowTag:String = cowTagNumber.text.toString()
            val weightString = birthWeight.text.trim().toString()
            val weight = weightString.toInt()
            val details:String = details.text.toString()
            val cciaNumber:String = cCIANumber.text.toString()
            val sex:String = CalfUtil.buttonIsChecked(bull)

            saveCalf(calfTag,cowTag,details,cciaNumber,weight,sex,it)

        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(isOrientationLandScape()){
            fabLeft.hide()
        }
    }

    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * private utility function to save the calf to the Room database and navigate
     * back to home fragment.
     * @param[tagNumber] tag number entered by the user
     * @param[details] details entered by the user
     * @param[cciaNumber] ccia number entered by the user
     * @param[sex] sex of the calf entered by the user
     * @param[view] the view being clicked. Used to navigate back to home
     *
     * @return no value
     */

    private fun saveCalf(
        calfTag: String,
        cowTag:String,
        details: String,
        cciaNumber: String,
        birthWeight:Int,
        sex: String,
        view: View,
    ){
        if(!CalfUtil.validateTagNumber(calfTag,this.calfTagNumber)){
            // this should run if the tagNumber is not empty
//            calfViewModel.insert(Calf(tagNumber,cciaNumber,sex,details, Date(),))
            val snackBar = Snackbar.make(view,"Calf $calfTag created", Snackbar.LENGTH_LONG)
            snackBar.setAction("DISMISS", SnackBarActions(snackBar))
            snackBar.show()
            val fireBaseCalf = FireBaseCalf(calfTag,cowTag,cciaNumber,sex,details, Date(),birthWeight)
            db.collection("users").document(auth.currentUser?.email!!)
                .collection("calves").document().set(fireBaseCalf)

            if(!isOrientationLandScape()){
                Navigation.findNavController(view).navigate(R.id.action_createCalf_to_mainFragment)
            }

        }
    }

    private fun isOrientationLandScape():Boolean{
        val orientation:Int = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true
        }
        return false
    }
}