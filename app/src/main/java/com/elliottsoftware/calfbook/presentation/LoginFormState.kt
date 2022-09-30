package com.elliottsoftware.calfbook.presentation

data class LoginFormState (
    val email:String = "",
    val emailError:String? = null,
    val password:String = "",
    val passwordError:String? = null,
    val passwordIconChecked:Boolean = true,
    val showProgressBar:Boolean = true
        ){
}