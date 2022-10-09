package com.elliottsoftware.calfbook.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.Navigation
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.databinding.FragmentWeatherBinding
import com.elliottsoftware.calfbook.presentation.navigationDrawer.AppBar
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerBody
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerHeader
import com.elliottsoftware.calfbook.presentation.navigationDrawer.MenuItem
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Weather.newInstance] factory method to
 * create an instance of this fragment.
 */
class Weather : Fragment() {
    private var _binding:FragmentWeatherBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.composeView.apply {
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

                                    }
                                    "home" ->{
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_weather)
                                        }

                                    }
                                }
                            })
                        }

                    ) {
                        Column {
                            Text("WEATHER")
                            Text("APP")
                        }
                        

                    }



                }
            }



            }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}