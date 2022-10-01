package com.elliottsoftware.calfbook.presentation.login

data class LoginFormState (
    val email:String = "",
    val emailError:String? = null,
    val password:String = "",
    val passwordError:String? = null,
    val passwordIconChecked:Boolean = false,
    val showProgressBar:Boolean = false
        ){
}