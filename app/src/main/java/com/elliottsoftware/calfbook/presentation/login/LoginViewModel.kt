package com.elliottsoftware.calfbook.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elliottsoftware.calfbook.domain.use_cases.ValidateEmail
import com.elliottsoftware.calfbook.domain.use_cases.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

//TODO: add hilt
class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
): ViewModel() {

    var state by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>() //this is what we are calling send() on
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }
    fun updateEmail(email:String){
        state = state.copy(email = email)
    }
    fun updatePassword(password:String){
        state = state.copy(password = password)
    }
    private fun showProgressBar(show:Boolean){
        state = state.copy(showProgressBar = show)

    }
     fun submitData(){

        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
         showProgressBar(true)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any{!it.successful}

        if(hasError){
            state = state.copy(
                emailError = emailResult.message,
                passwordError = passwordResult.message
            )
            //showProgressBar(false)
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }

    }
    fun passwordIconChecked(checked:Boolean){
        state = state.copy(passwordIconChecked= checked)

    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

}