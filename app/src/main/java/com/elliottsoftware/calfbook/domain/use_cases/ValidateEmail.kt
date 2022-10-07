package com.elliottsoftware.calfbook.domain.use_cases

import android.util.Patterns
import com.elliottsoftware.calfbook.domain.models.FormValidationResult

class ValidateEmail {


    fun execute(email:String):FormValidationResult{
        if(email.isBlank()){
            return FormValidationResult(success = false,errorMessage ="Email can not be blank" )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return FormValidationResult(
                success = false,
                errorMessage = "Not a valid email"
            )
        }
        return FormValidationResult(success = true)
    }

}