package com.elliottsoftware.calfbook.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentMainBinding
import com.elliottsoftware.calfbook.recyclerViews.CalfListAdapter
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.viewModles.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(),CalfListAdapter.OnCalfListener {
    private  var _binding:FragmentMainBinding? = null
    //this property is only valid between onCreateView on onDestroy
    private val binding get() = _binding!!
    private lateinit var fabButton: FloatingActionButton
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }


    private lateinit var recyclerView: RecyclerView
    private val adapter = CalfListAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerview
        val  view = binding.root
        fabButton = binding.fab
        return view;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        calfViewModel.allCalves.observe(viewLifecycleOwner, Observer { calves ->
            calves?.let{adapter.submitList(it)}
        })
        fabButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_createCalf)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //todo:implement
    override fun onCalfClick(calfId: Long) {

    }

}