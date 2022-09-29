package com.elliottsoftware.calfbook.presentation.viewModles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.elliottsoftware.calfbook.domain.use_cases.ValidateEmail
import com.elliottsoftware.calfbook.domain.use_cases.ValidatePassword
import com.elliottsoftware.calfbook.presentation.LoginFormState
//TODO: add hilt
class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
): ViewModel() {

    var state by mutableStateOf(LoginFormState())
}