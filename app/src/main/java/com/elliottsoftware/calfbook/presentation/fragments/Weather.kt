package com.elliottsoftware.calfbook.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.elliottsoftware.calfbook.R
import com.elliottsoftware.calfbook.data.remote.Post
import com.elliottsoftware.calfbook.data.remote.PostRetrofitInstance
import com.elliottsoftware.calfbook.data.remote.PostViewModel
import com.elliottsoftware.calfbook.data.remote.RetrofitInstance
import com.elliottsoftware.calfbook.databinding.FragmentWeatherBinding
import com.elliottsoftware.calfbook.domain.models.PostResponse
import com.elliottsoftware.calfbook.domain.models.Response2
import com.elliottsoftware.calfbook.presentation.components.PostCard
import com.elliottsoftware.calfbook.presentation.login.Fail
import com.elliottsoftware.calfbook.presentation.login.LinearLoadingBar
import com.elliottsoftware.calfbook.presentation.login.Success
import com.elliottsoftware.calfbook.presentation.navigationDrawer.AppBar
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerBody
import com.elliottsoftware.calfbook.presentation.navigationDrawer.DrawerHeader
import com.elliottsoftware.calfbook.presentation.navigationDrawer.MenuItem
import com.elliottsoftware.calfbook.presentation.recyclerViews.PostAdapter
import com.elliottsoftware.calfbook.presentation.recyclerViews.TodoAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


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
    //private val viewModel:PostViewModel =  viewModel()

    //private lateinit var todoAdapter: PostAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }
//    private fun setupRecyclerView(){
//        binding.rvTodos.apply {
//            todoAdapter = PostAdapter()
//            adapter = todoAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
//    }

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
                                            Navigation.findNavController(binding.root).navigate(R.id.action_weather_to_mainFragment)
                                        }

                                    }
                                }
                            })
                        }

                    ) {
//
//                        val postList = listOf(
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3),
//                            Post(title = "MEATBALL", id = 1, body = "", userId = 1),
//                            Post(title = "TINY", id = 2, body = "", userId = 2),
//                            Post(title = "ANOTHER", id = 3, body = "", userId = 3)
//                        )
//
//                        LazyColumn{
//                            items(postList){post ->
//                                PostCard(post = post)
//                            }
//                        }
                        LoadingIndicator()

                    }



                } //end of setContent{}
            }
            }


        return view
    }



    @Composable
    fun LoadingIndicator(viewModel: PostViewModel = viewModel()){
        when(val response = viewModel.loadingResponse){
            is PostResponse.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            is PostResponse.Success -> {
                if(response.data){
                    //THIS IS WHERE WE WOULD DO THE NAVIGATION
                    //Success()
                }
            }
            is PostResponse.Failure -> {
                //should probably show a snackbar
                //Fail()
                Log.d("Login Error",response.e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
