package com.elliottsoftware.calfbook.domain.use_cases

import android.util.Patterns

class ValidateEmail {


    fun execute(email:String):ValidationResult{
        if(email.isBlank()){
            return ValidationResult(false,"Email can not be blank")
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                message = "Please enter valid email"
            )
        }
        return ValidationResult(successful = true)
    }
}