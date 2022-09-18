package com.elliottsoftware.calfbook.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentMainBinding
import com.elliottsoftware.calfbook.recyclerViews.CalfListAdapter
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.util.SwipeToDelete
import com.elliottsoftware.calfbook.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.viewModles.CalfViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(),CalfListAdapter.OnCalfListener, MenuProvider,SearchView.OnQueryTextListener {
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
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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

        ItemTouchHelper(SwipeToDelete(calfViewModel,adapter)).attachToRecyclerView(recyclerView)
    }


    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /**
     * method from [CalfListAdapter.OnCalfListener] used to navigate to [UpdateCalfFragment]
     * @param[calfId] the unique identifier of the calf
     *
     * @return
     */
    override fun onCalfClick(calfId: Long) {
        //allCalves.value?.get(position) //index of the current calf
        val action = MainFragmentDirections.actionMainFragmentToEditCalf(calfId)

        Navigation.findNavController(binding.root).navigate(action)

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu,menu)
        val search = menu?.findItem(R.id.menu_search)

        val searchView = search?.actionView as? SearchView
        searchView?.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView?.queryHint = "Search Tag Number"
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true

    }

    /**
     * method from [SearchView.OnQueryTextListener] used to handle search queries
     * @param[query] the query entered by the user
     *
     * @return boolean to deterime if the query was handled properly
     */
    //QUERY RELATED METHODS
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    /**
     * method from [SearchView.OnQueryTextListener] used to handle search queries
     * @param[query] the query entered by the user
     *
     * @return boolean to determine if the query was handled properly
     */
    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }
    /**
     * private utility method to search the database
     * @param[query] the query entered by the user
     *
     * @return
     */
    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        calfViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.submitList(it)
            }
        }
    }

}