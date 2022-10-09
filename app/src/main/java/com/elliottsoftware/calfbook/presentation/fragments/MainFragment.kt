package com.elliottsoftware.calfbook.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentMainBinding
import com.elliottsoftware.calfbook.domain.models.firebase.FireBaseCalf
import com.elliottsoftware.calfbook.presentation.navigationDrawer.AppBar
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerBody
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerHeader
import com.elliottsoftware.calfbook.presentation.navigationDrawer.MenuItem
import com.elliottsoftware.calfbook.presentation.recyclerViews.CalfListAdapter
import com.elliottsoftware.calfbook.presentation.recyclerViews.FirestoreAdapter
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModel
import com.elliottsoftware.calfbook.presentation.viewModles.CalfViewModelFactory
import com.elliottsoftware.calfbook.util.CalfApplication
import com.elliottsoftware.calfbook.util.SwipeToDelete
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
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
    //private lateinit var recyclerView: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater)
//        recyclerView = binding.recyclerview

        val  view = binding.root
//        fabButton = binding.fab
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding.composeView.apply {
            //A strategy for managing the underlying Composition of Compose UI
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(

                    scaffoldState = scaffoldState,
                    topBar = {
                             AppBar(
                                 onNavigationIconClick = {
                                     scope.launch {
                                         scaffoldState.drawerState.open()
                                     }


                                 }
                             )
                    },
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(items = listOf(
                            MenuItem(
                                id = "home",
                                title="Home",
                                contentDescription = "Go to home screen",
                                icon = Icons.Default.Home
                            ),
                            MenuItem(
                                id = "weather",
                                title="Weather",
                                contentDescription = "Go to weather screen",
                                icon = Icons.Default.Terrain
                            )

                        ), onItemClick = {
                            when(it.id){
                                "weather" ->{
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                        Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_weather)
                                    }

                                }
                            }
                        })
                    }
                ) {

                }



            }
        }

        return view;

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


    /**
     * sets _binding = null to avoid memory leaks with View Binding
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}