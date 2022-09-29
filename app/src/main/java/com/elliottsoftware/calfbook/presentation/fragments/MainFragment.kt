package com.elliottsoftware.calfbook.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentMainBinding
import com.elliottsoftware.calfbook.domain.models.firebase.FireBaseCalf
import com.elliottsoftware.calfbook.presentation.recyclerViews.CalfListAdapter
import com.elliottsoftware.calfbook.presentation.recyclerViews.FirestoreAdapter
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.util.SwipeToDelete
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModelFactory
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(),CalfListAdapter.OnCalfListener, MenuProvider {
    private  var _binding:FragmentMainBinding? = null
    //this property is only valid between onCreateView on onDestroy
    private val binding get() = _binding!!
    private lateinit var fabButton: FloatingActionButton
    private var auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef: CollectionReference = db.collection("users").document(auth.currentUser?.email!!)
        .collection("calves")
    private val calfViewModel: CalfViewModel by viewModels {
        CalfViewModelFactory((activity?.application as CalfApplication).repository)
    }
    private lateinit var adapter: FirestoreAdapter
    private lateinit var recyclerView: RecyclerView



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


        setUpRecyclerView();



        fabButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_createCalf)
        }

       ItemTouchHelper(SwipeToDelete(adapter)).attachToRecyclerView(recyclerView)

        val orientation:Int = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            fabButton.hide()
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


    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        return when (menuItem.itemId) {
            R.id.logout -> {
                auth.signOut()
                Navigation.findNavController(view!!).navigate(R.id.action_mainFragment_to_login)
                true
            }
            else -> return false
        }

    }




    private fun setUpRecyclerView():Unit{
         val options: FirestoreRecyclerOptions<FireBaseCalf> = FirestoreRecyclerOptions.Builder<FireBaseCalf>()
            .setQuery(collectionRef, FireBaseCalf::class.java)
            .build()
        adapter = FirestoreAdapter(options)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening();
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}