package com.elliottsoftware.calfbook.presentation.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(viewModel:LoginViewModel = viewModel()) {
    val state = viewModel.state
    LoginView(viewModel,state)
}