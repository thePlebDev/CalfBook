package com.elliottsoftware.calfbook.domain.use_cases

import android.util.Patterns

class ValidatePassword {


    fun execute(password:String):ValidationResult{
        if(password.isBlank()){
            return ValidationResult(false,"Password can not be blank")
        }
        if(password.length < 8){
            return ValidationResult(
                successful = false,
                message = "Password must contain at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } && password.any { it.isLetter() }
        if(!containsLettersAndDigits){
            return ValidationResult(
                successful = false,
                message = "Password must contain at least on digit and letter"
            )
        }
        return ValidationResult(successful = true)
    }
}