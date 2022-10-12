package com.elliottsoftware.calfbook.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliottsoftware.calfbook.domain.models.Response2
import com.elliottsoftware.calfbook.domain.use_cases.SignUserInUseCase
import com.elliottsoftware.calfbook.domain.use_cases.ValidateEmail
import com.elliottsoftware.calfbook.domain.use_cases.ValidatePassword
import kotlinx.coroutines.launch

//TODO: add hilt
class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val signUserInUseCase: SignUserInUseCase = SignUserInUseCase()
): ViewModel() {

    //todo: THIS NEEDS TO BE CHANGED
    var state by mutableStateOf(LoginFormState())

    var signInWithFirebaseResponse by mutableStateOf<Response2<Boolean>>(Response2.Success(false))
        private set



    fun updateEmail(email:String){
        state = state.copy(email = email)
    }
    fun updatePassword(password:String){
        state = state.copy(password = password)
    }

    fun signInWithFireBase(email:String,password: String) = viewModelScope.launch{
        //todo make an actual call to the data layer through the use case
        //signInWithFirebaseResponse = signUserInUseCase(email,password)
        signUserInUseCase(email,password).collect{response ->
            signInWithFirebaseResponse = response

        }
    }
     fun submitData(){
         val emailResult = validateEmail.execute(state.email)
         val passwordResult = validatePassword.execute(state.password)
         val hasError = listOf(
             emailResult,
             passwordResult
         ).any{!it.success}
         if(hasError){
             state = state.copy(
                 emailError = emailResult.errorMessage,
                 passwordError = passwordResult.errorMessage
             )

             return
         }else{
             //THIS IS WHAT FIXES THE UI BUG
             state = state.copy(
                 emailError = null,
                 passwordError = null
             )
         }
         signInWithFireBase(state.email,state.password)

    }


    fun passwordIconChecked(checked:Boolean){
        state = state.copy(passwordIconChecked= checked)

    }



}


