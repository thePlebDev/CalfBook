package com.elliottsoftware.calfbook.domain.use_cases

data class ValidationResult(
    val successful: Boolean,
    val message: String? = null
)
