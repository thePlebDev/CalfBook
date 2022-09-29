package com.elliottsoftware.calfbook.presentation.viewModles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elliottsoftware.calfbook.presentation.LoginFormState

class LoginViewModel: ViewModel() {

    var state by mutableStateOf(LoginFormState())
}