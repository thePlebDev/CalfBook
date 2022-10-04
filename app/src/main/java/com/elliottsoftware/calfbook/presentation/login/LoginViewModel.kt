package com.elliottsoftware.calfbook.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliottsoftware.calfbook.domain.models.Response
import com.elliottsoftware.calfbook.domain.use_cases.ValidateEmail
import com.elliottsoftware.calfbook.domain.use_cases.ValidatePassword
import com.elliottsoftware.calfbook.domain.use_cases.auth.AuthUseCases
import com.elliottsoftware.calfbook.domain.use_cases.auth.LoginUser
import com.elliottsoftware.calfbook.domain.use_cases.auth.LogoutUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

//TODO: add hilt
class LoginViewModel(
    private val loginUser: LoginUser = LoginUser(),
    private val logoutUser: LogoutUser = LogoutUser(),
    private val authUseCases: AuthUseCases = AuthUseCases(loginUser,logoutUser)
): ViewModel() {

    //todo: THIS NEEDS TO BE CHANGED
    var state by mutableStateOf(LoginFormState())

    var signInWithFirebaseResponse by mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set


    fun updateEmail(email:String){
        state = state.copy(email = email)
    }
    fun updatePassword(password:String){
        state = state.copy(password = password)
    }
    private fun showProgressBar(show:Boolean){
        state = state.copy(showProgressBar = show)

    }
    fun signInWithFireBase(){
        //todo make an actual call to the data layer through the use case
        signInWithFirebaseResponse = Response.Loading
    }
     fun submitData(){


    }
    fun passwordIconChecked(checked:Boolean){
        state = state.copy(passwordIconChecked= checked)

    }


}


