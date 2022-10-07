package com.elliottsoftware.calfbook.domain.use_cases


import com.elliottsoftware.calfbook.domain.models.FormValidationResult

class ValidatePassword {


    fun execute(password:String): FormValidationResult {
        if(password.isBlank()){
            return FormValidationResult(success = false,errorMessage ="Password can not be blank" )
        }
        if(password.length < 8){
            return FormValidationResult(
                success = false,
                errorMessage = "Password must consist of at least 8 characters"
            )
        }
        return FormValidationResult(success = true)
    }
}