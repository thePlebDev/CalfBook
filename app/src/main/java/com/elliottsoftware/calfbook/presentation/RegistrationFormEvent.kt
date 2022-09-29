package com.elliottsoftware.calfbook.presentation

import com.elliottsoftware.calfbook.domain.use_cases.ValidatePassword

sealed class RegistrationFormEvent {
    //These are all subclasses
    data class EmailChanged(val email:String):RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}