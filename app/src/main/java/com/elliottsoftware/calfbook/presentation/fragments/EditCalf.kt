package com.elliottsoftware.calfbook.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentEditCalfBinding
import com.elliottsoftware.calfbook.domain.models.Calf
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.util.CalfUtil
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 * A simple [Fragment] subclass.

 */
class EditCalf : Fragment() {
    private var _binding: FragmentEditCalfBinding? = null
    private val binding get() = _binding!!
    private val args: EditCalfArgs by navArgs()
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }
    private lateinit var cancelButton: FloatingActionButton
    private lateinit var updateButton: FloatingActionButton
    private lateinit var  updateTagNumber: EditText
    private lateinit var updateDetails: EditText
    private lateinit var updateCCIANumber: EditText
    private lateinit var updateSexBULL: RadioButton
    private lateinit var updateSexHEIFER: RadioButton
    private lateinit var calfDate: Date





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditCalfBinding.inflate(inflater,container,false)
        val view = binding.root
        cancelButton = binding.newCalfFabLeft;
        updateButton = binding.newCalfFabRight;
        updateTagNumber = binding.editTag
        updateDetails = binding.editDescription
        updateCCIANumber = binding.editCciaNumber
        updateSexBULL = binding.radioBull
        updateSexHEIFER = binding.radioHeifer
        return view
    }

    /**
     * handles all the business logic needed for the [R.layout.fragment_new_calf]
     * file
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        CoroutineScope(Dispatchers.Main).launch {

            setCalfOnMainThread(args.calfId)

        }
        cancelButton.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_editCalf_to_mainFragment)
        }
        updateButton.setOnClickListener{
            updateCalf(updateTagNumber.text.toString(),updateDetails.text.toString(),
                updateCCIANumber.text.toString(), updateSexBULL.isChecked,view)


        }
    }

    /**
     * private utility function to retireve the appropriate calf from the database
     * and fill in the UI appropriately
     * @param[calfId] unique identifier of the Calf
     *
     * @return
     */
    private suspend fun setCalfOnMainThread(calfId:Long){
        val foundCalf = calfViewModel.findCalf(calfId)
        updateTagNumber.setText(foundCalf.tagNumber)
        updateDetails.setText(foundCalf.details)
        updateCCIANumber.setText(foundCalf.CCIANumber)
        calfDate = foundCalf.date
        updateSex(foundCalf.sex)

    }

    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * private utility function used to determine the sex of the calf
     * @param[sex] the sex of the calf
     *
     * @return
     */
    private fun updateSex(sex:String){
        if(sex == "Bull"){
            updateSexBULL.isChecked = true
        }else{
            updateSexHEIFER.isChecked = true
        }
    }
    /**
     * private utility function used to validate the tag is not empty and update the calf
     * @param[tagNumber] tag number entered by the user
     * @param[details] details entered by the user
     * @param[cciaNumber] ccia number entered by theyser
     * @param[isBull] used to determine the sex of the calf
     *
     * @return
     */
    private fun updateCalf(tagNumber: String,details:String,cciaNumber: String,isBull:Boolean,view: View){
        if(!CalfUtil.validateTagNumber(tagNumber,this.updateTagNumber)){
            val sex = CalfUtil.buttonIsChecked(updateSexBULL)
            calfViewModel.updateCalf(Calf(tagNumber,cciaNumber,sex,details,calfDate,args.calfId))
            Navigation.findNavController(view).navigate(R.id.action_editCalf_to_mainFragment)

        }


    }

}