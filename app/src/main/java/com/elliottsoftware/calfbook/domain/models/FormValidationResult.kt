package com.elliottsoftware.calfbook.domain.models

data class FormValidationResult(
    val success:Boolean,
    val errorMessage:String ? = null
)